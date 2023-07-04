package com.fieldwire.assignment.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fieldwire.assignment.dao.FloorplanRepository;
import com.fieldwire.assignment.exception.CreationException;
import com.fieldwire.assignment.exception.FileStorageException;
import com.fieldwire.assignment.exception.NotFoundException;
import com.fieldwire.assignment.model.Floorplan;
import com.fieldwire.assignment.model.Project;
import com.fieldwire.assignment.service.FloorplanService;
import com.fieldwire.assignment.service.ImageProcessorService;
import com.fieldwire.assignment.service.ProjectService;
import com.fieldwire.assignment.util.CommonUtil;
import com.fieldwire.assignment.util.Constants;

import jakarta.transaction.Transactional;

@Service
public class FloorplanServiceImpl implements FloorplanService {

    Logger logger = LoggerFactory.getLogger(FloorplanService.class);

    private static final String PROJECT_NOT_FOUND = Constants.PROJECT_NOT_FOUND;
    private static final String FLOORPLAN_NOT_CREATED = Constants.FLOORPLAN_NOT_CREATED;
    private static final String FLOORPLAN_NOT_FOUND = Constants.FLOORPLAN_NOT_FOUND;

    @Autowired
    FloorplanRepository floorplanRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    ImageProcessorService imageProcessorService;

    private enum ImageUrl {
        ThumbUrl,
        OriginalUrl,
        LargeUrl
    }

    @Override
    public List<Floorplan> getFloorplans(UUID projectId) throws NotFoundException {
        Optional<Project> result = projectService.findProjectById(projectId);

        if (result.isPresent()) {
            logger.debug("project found");
            return floorplanRepository.findAllByProjectId(result.get().getId());
        } else {
            logger.error(PROJECT_NOT_FOUND);
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }

    }

    @Override
    public Optional<Floorplan> getFloorplanById(UUID projectId, UUID floorplanId) throws NotFoundException {
        Optional<Project> result = projectService.findProjectById(projectId);
        if (result.isPresent()) {
            logger.debug("project found");
            return floorplanRepository.findByIdAndProjectId(floorplanId, result.get().getId());
        } else {
            logger.error(PROJECT_NOT_FOUND);
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteFloorplan(UUID projectId, UUID floorplanId) throws NotFoundException {
        Optional<Project> projectResult = projectService.findProjectById(projectId);

        if (!projectResult.isPresent()) {
            logger.error(PROJECT_NOT_FOUND);
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }

        Optional<Floorplan> floorplanResult = getFloorplanById(projectResult.get().getId(), floorplanId);

        if (!floorplanResult.isPresent()) {
            logger.error(FLOORPLAN_NOT_FOUND);
            throw new NotFoundException(FLOORPLAN_NOT_FOUND);
        }

        Floorplan floorplan = floorplanResult.get();
        floorplanRepository.deleteById(floorplan.getId());
        imageProcessorService.deleteFiles(
                floorplan.getName(), projectId);

    }

    @Override
    public void updateFloorplan(MultipartFile file, UUID floorplanId, UUID projectId)
            throws CreationException, NotFoundException {
        Optional<Floorplan> result = getFloorplanById(projectId, floorplanId);

        if (!result.isPresent()) {
            logger.error(FLOORPLAN_NOT_FOUND);
            throw new NotFoundException(FLOORPLAN_NOT_FOUND);
        }

        Floorplan floorplan = result.get();
        imageProcessorService.deleteFiles(floorplan.getName(), projectId);
        createFloorplans(Arrays.asList(file), projectId, true, floorplan);
    }

    @Override
    public void createFloorplans(List<MultipartFile> files, UUID projectId, Boolean isUpdate,
            Floorplan floorPlanToBeUpdated) throws CreationException {

        Set<String> names = new HashSet<String>();
        try {

            List<BufferedImage> bufferedImages = convertMultipartFileToBufferedImage(files);
            Integer iteration = 0;
            imageProcessorService.createSubDirectory(projectId);
            List<Floorplan> floorplans = new ArrayList<Floorplan>();
            for (BufferedImage bufferedImage : bufferedImages) {
                MultipartFile multipartFile = files.get(iteration);

                Map<ImageUrl, String> imageDetails = handleImageProcessing(multipartFile, bufferedImage);
                Floorplan floorplan = buildEntity(multipartFile, imageDetails);

                if (isUpdate) {
                    floorplan.setId(floorPlanToBeUpdated.getId());

                }
                floorplans.add(floorplan);
                names.add(floorplan.getName());
                iteration++;
            }

            Optional<Project> result = projectService.findProjectById(projectId);

            if (!result.isPresent()) {
                logger.error(PROJECT_NOT_FOUND);
                throw new NotFoundException(PROJECT_NOT_FOUND);
            }

            for (Floorplan floorplan : floorplans) {
                floorplan.setProject(result.get());
                try {

                    floorplanRepository.save(floorplan);
                } catch (DataIntegrityViolationException e) {
                    logger.debug("Same file uploaded again");
                }
            }
        } catch (FileStorageException e) {
            throw new CreationException(FLOORPLAN_NOT_CREATED);
        } catch (Exception e) {
            logger.error("Error occured while creating plans", e);
            if (names.size() != 0) {
                imageProcessorService.deleteSubDirectory();
            }
            throw new CreationException(FLOORPLAN_NOT_CREATED);
        }

    }

    private Floorplan buildEntity(MultipartFile file, Map<ImageUrl, String> imageDetails) {

        Floorplan floorplan = new Floorplan();
        floorplan.setName(CommonUtil.getFileName(file));
        floorplan.setThumbUrl(imageDetails.get(ImageUrl.ThumbUrl));
        floorplan.setOriginalUrl(imageDetails.get(ImageUrl.OriginalUrl));
        floorplan.setLargeUrl(imageDetails.get(ImageUrl.LargeUrl));
        return floorplan;
    }

    private List<BufferedImage> convertMultipartFileToBufferedImage(List<MultipartFile> multipartFiles)
            throws IOException {

        List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        for (MultipartFile multipartFile : multipartFiles) {
            bufferedImages.add(ImageIO.read(multipartFile.getInputStream()));
        }

        return bufferedImages;
    }

    private Map<ImageUrl, String> handleImageProcessing(MultipartFile multipartFile, BufferedImage bufferedImage)
            throws InterruptedException, ExecutionException {

        CompletableFuture<String> thumbUrl = imageProcessorService.createAndStoreThumbImage(multipartFile,
                bufferedImage);
        CompletableFuture<String> largeUrl = imageProcessorService.createAndStoreLargeImage(multipartFile,
                bufferedImage);
        CompletableFuture<String> originalUrl = imageProcessorService.storeFile(multipartFile);

        CompletableFuture.allOf(thumbUrl, largeUrl, originalUrl).join();

        return Map.of(
                ImageUrl.ThumbUrl, thumbUrl.get(),
                ImageUrl.OriginalUrl, originalUrl.get(),
                ImageUrl.LargeUrl, largeUrl.get());
    }

}
