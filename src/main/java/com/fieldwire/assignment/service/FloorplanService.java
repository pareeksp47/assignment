package com.fieldwire.assignment.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fieldwire.assignment.exception.CreationException;
import com.fieldwire.assignment.exception.FileStorageException;
import com.fieldwire.assignment.exception.NotFoundException;
import com.fieldwire.assignment.model.Floorplan;

public interface FloorplanService {

        List<Floorplan> getFloorplans(UUID projectId) throws NotFoundException;

        Optional<Floorplan> getFloorplanById(UUID projectId, UUID floorplanId) throws NotFoundException;

        void createFloorplans(List<MultipartFile> files, UUID projectId, Boolean isUpdate, Floorplan floorplan)
                        throws NotFoundException, CreationException, FileStorageException;

        void updateFloorplan(MultipartFile file, UUID floorplanId, UUID projectId)
                        throws NotFoundException, CreationException, FileStorageException;

        void deleteFloorplan(UUID projectId, UUID floorplanId) throws NotFoundException;
}
