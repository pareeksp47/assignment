package com.fieldwire.assignment.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.fieldwire.assignment.dto.ProjectDTO;
import com.fieldwire.assignment.dto.ResponseDTO;
import com.fieldwire.assignment.exception.CreationException;
import com.fieldwire.assignment.exception.NotFoundException;
import com.fieldwire.assignment.model.Project;
import com.fieldwire.assignment.service.ProjectService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/projects", produces = "application/json")
public class ProjectController {

    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProjectDTO> getProjects() {
        logger.debug("get projects");
        List<Project> projects = projectService.getProjects();
        List<ProjectDTO> projectDTOs = projects.stream().map(project -> {
            return convertToDTO(project);
        }).collect(Collectors.toList());

        logger.debug("get projects success");
        return projectDTOs;
    }

    @PostMapping(consumes = "application/json")
    public ResponseDTO createProject(@RequestBody ProjectDTO projectDTO) throws CreationException {

        logger.debug("create project");
        Project project = convertToEntity(projectDTO);
        projectService.createProject(project);
        logger.debug("create project success");
        return ResponseDTO.STATUS_201_OK;
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseDTO updateProject(@PathVariable("id") UUID projectId, @RequestBody ProjectDTO projectDTO)
            throws NotFoundException, CreationException {

        logger.debug("update project");
        Project project = convertToEntity(projectDTO);
        projectService.updateProject(project, projectId);
        logger.debug("update project success");
        return new ResponseDTO(204, "Record updated successfully");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseDTO deleteProject(@PathVariable("id") UUID projectId) throws InternalError, NotFoundException {
        logger.debug("delete project");
        projectService.deleteProject(projectId);
        logger.debug("delete project success");
        return new ResponseDTO(204, "Record deleted successfully");
    }

    private Project convertToEntity(ProjectDTO projectDTO) {

        Project project = modelMapper.map(projectDTO, Project.class);
        return project;

    }

    private ProjectDTO convertToDTO(Project project) {

        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        return projectDTO;

    }

}
