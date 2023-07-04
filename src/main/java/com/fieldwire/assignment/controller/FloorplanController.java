package com.fieldwire.assignment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import com.fieldwire.assignment.dto.FloorplanDTO;
import com.fieldwire.assignment.dto.ResponseDTO;
import com.fieldwire.assignment.exception.CreationException;
import com.fieldwire.assignment.exception.FileStorageException;
import com.fieldwire.assignment.model.Floorplan;
import com.fieldwire.assignment.service.FloorplanService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = "/projects/{project_id}/floorplans", produces = "application/json")
public class FloorplanController {

    Logger logger = LoggerFactory.getLogger(FloorplanController.class);

    @Autowired
    private FloorplanService floorplanService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<FloorplanDTO> getFloorplans(@PathVariable("project_id") UUID projectId)
            throws com.fieldwire.assignment.exception.NotFoundException {

        logger.debug("Get floor plans for project id {} ", projectId);

        List<Floorplan> floorplans = floorplanService.getFloorplans(projectId);
        List<FloorplanDTO> floorplanDTOs = new ArrayList<FloorplanDTO>();
        for (Floorplan floorplan : floorplans) {
            floorplanDTOs.add(convertToDTO(floorplan));
        }
        logger.debug("Total floor plans returned {}", floorplanDTOs.size());
        return floorplanDTOs;
    }

    @GetMapping("/{id}")
    public FloorplanDTO getFloorplanById(@PathVariable("project_id") UUID projectId,
            @PathVariable("id") UUID floorplanId) throws com.fieldwire.assignment.exception.NotFoundException {

        logger.debug("Get floor plan detail for Id {} for project id {} ", floorplanId, projectId);
        Optional<Floorplan> result = floorplanService.getFloorplanById(projectId, floorplanId);
        logger.debug("Returned floor plan is present {} ", result.isPresent());
        if (result.isPresent()) {
            return convertToDTO(result.get());
        } else {
            String message = "Floor plan not found";
            logger.error(message);
            throw new NotFoundException(message);
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseDTO createFloorplans(@PathVariable("project_id") UUID projectId,
            @RequestBody FloorplanDTO floorplanDTO)
            throws FileStorageException, com.fieldwire.assignment.exception.NotFoundException, CreationException {

        logger.debug("Create multiple floor plans for project Id {} ", projectId);
        floorplanService.createFloorplans(floorplanDTO.getFiles(), projectId, false, null);
        logger.debug("Create multiple floor plans for project Id {} ", projectId);
        return ResponseDTO.STATUS_201_OK;
    }

    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseDTO updateFloorplan(@PathVariable("project_id") UUID projectId,
            @PathVariable("id") UUID floorplanId, @RequestBody FloorplanDTO floorplanDTO)
            throws FileStorageException, com.fieldwire.assignment.exception.NotFoundException, CreationException {

        logger.debug("Update existing floor plan id {} for project id {}", floorplanId, projectId);
        floorplanService.updateFloorplan(floorplanDTO.getFiles().get(0), floorplanId, projectId);
        logger.debug("floor plan updated");
        return new ResponseDTO(204, "Record updated successfully");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseDTO deleteFloorplan(@PathVariable("project_id") UUID projectId,
            @PathVariable("id") UUID floorplanId) throws com.fieldwire.assignment.exception.NotFoundException {

        logger.debug("delete floor plan id {} for project id {} ", floorplanId, projectId);
        floorplanService.deleteFloorplan(projectId, floorplanId);
        logger.debug("delete successful");
        return ResponseDTO.STATUS_200_OK;
    }

    private FloorplanDTO convertToDTO(Floorplan floorplan) {

        FloorplanDTO floorplanDTO = modelMapper.map(floorplan, FloorplanDTO.class);
        return floorplanDTO;

    }

}
