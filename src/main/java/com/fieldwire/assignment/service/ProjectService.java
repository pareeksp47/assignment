package com.fieldwire.assignment.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fieldwire.assignment.exception.CreationException;
import com.fieldwire.assignment.exception.NotFoundException;
import com.fieldwire.assignment.model.Project;

public interface ProjectService {

    List<Project> getProjects();

    Optional<Project> findProjectById(UUID projectId) throws NotFoundException;

    void createProject(Project project) throws CreationException;

    void updateProject(Project project, UUID projectId) throws NotFoundException, CreationException;

    void deleteProject(UUID projectId) throws NotFoundException, InternalError;

}
