package com.fieldwire.assignment.service.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.fieldwire.assignment.exception.FileStorageException;
import com.fieldwire.assignment.security.FileStorageProperties;
import com.fieldwire.assignment.service.ImageProcessorService;
import com.fieldwire.assignment.util.CommonUtil;

import org.marvinproject.image.transform.scale.Scale;
import marvin.image.MarvinImage;

@Service
public class ImageProcessorServiceImpl implements ImageProcessorService {

    Logger logger = LoggerFactory.getLogger(ImageProcessorService.class);

    private Path fileStorageLocation;
    private final Thumb thumb;
    private final Large large;

    private class Image {
        final Integer width;
        final Integer height;

        Image(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }
    }

    private final class Thumb extends Image {
        Thumb(Integer width, Integer height) {
            super(width, height);
        }
    }

    private final class Large extends Image {
        Large(Integer width, Integer height) {
            super(width, height);
        }
    }

    @Autowired
    public ImageProcessorServiceImpl(FileStorageProperties fileStorageProperties) {

        logger.debug("image processing set properties");

        fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.thumb = new Thumb(fileStorageProperties.getThumbWidth(), fileStorageProperties.getThumbHeight());
        this.large = new Large(fileStorageProperties.getLargeWidth(), fileStorageProperties.getLargeHeight());

        try {
            logger.debug("create upload directory");
            Files.createDirectories(this.fileStorageLocation);
            // Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            logger.error("Error creating upload directory", ex);
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @Override
    public void createSubDirectory(UUID projectId) {
        logger.debug("create sub directory for each project");
        try {
            if (!this.fileStorageLocation.toString().contains("\\" + projectId))
                fileStorageLocation = CommonUtil.attachProjectIdtoPath(fileStorageLocation, projectId);
            Files.createDirectories(fileStorageLocation);
            logger.debug("sub directory for project created");
            // Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            logger.error("Error while creating sub directory", ex);
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @Override
    public void deleteSubDirectory() {
        try {
            Files.delete(fileStorageLocation);
            logger.debug("delete sub directory successful");
        } catch (Exception e) {
            logger.error("Error deleting sub directory", e);
            throw new InternalError("Deleting sub directory error occured", e);
        }
    }

    @Override
    public CompletableFuture<String> createAndStoreThumbImage(final MultipartFile file, final BufferedImage image) {
        BufferedImage thumbImage = resizeImage(image, this.thumb.width, this.thumb.height);
        return CompletableFuture.completedFuture(storeFile(thumbImage, file, ImageType.THUMB));
    }

    @Override
    public CompletableFuture<String> createAndStoreLargeImage(final MultipartFile file, final BufferedImage image) {
        BufferedImage largeImage = resizeImage(image, this.large.width, this.large.height);
        return CompletableFuture.completedFuture(storeFile(largeImage, file, ImageType.LARGE));
    }

    @Override
    public CompletableFuture<String> storeFile(final MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            fileName = ImageType.ORIGINAL + fileName;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return CompletableFuture.completedFuture(targetLocation.toUri().toURL().toString());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFile(final BufferedImage image, final MultipartFile file, ImageType imageType) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            fileName = imageType + fileName;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            File outputfile = new File(targetLocation.toUri());
            ImageIO.write(image, getImageExtension(fileName), outputfile);

            logger.debug("upload floor plan image success");
            return targetLocation.toUri().toURL().toString();

        } catch (IOException ex) {
            logger.error("Could not store floor plan error occured", ex);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        MarvinImage image = new MarvinImage(originalImage);
        Scale scale = new Scale();
        scale.load();
        scale.setAttribute("newWidth", targetWidth);
        scale.setAttribute("newHeight", targetHeight);
        scale.process(image.clone(), image, null, null, false);
        return image.getBufferedImageNoAlpha();
    }

    private String getImageExtension(String fileName) {
        String extension = fileName;
        if (extension == null) {
            extension = "jpeg";
        }
        int delimiterIndex = extension.lastIndexOf('.');
        return extension.substring(delimiterIndex + 1);
    }

    @Override
    public void deleteFiles(String name, UUID projectId) {

        if (!this.fileStorageLocation.toString().contains("\\" + projectId))
            this.fileStorageLocation = CommonUtil.attachProjectIdtoPath(fileStorageLocation, projectId);
        for (ImageType imageType : ImageType.values()) {
            Path path = this.fileStorageLocation.resolve(imageType + name);
            try {

                Files.delete(path);

            } catch (Exception ex) {
                logger.error("Could not delete floor plan error occured", ex);
                throw new FileStorageException("Could not delete floor plan image. Please try again!", ex);
            }

        }
    }

}
