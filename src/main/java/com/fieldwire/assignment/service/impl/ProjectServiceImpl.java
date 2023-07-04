package com.fieldwire.assignment.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fieldwire.assignment.dao.ProjectRepository;
import com.fieldwire.assignment.exception.NotFoundException;
import com.fieldwire.assignment.model.Project;
import com.fieldwire.assignment.service.ProjectService;
import com.fieldwire.assignment.util.Constants;

@Service
public class ProjectServiceImpl implements ProjectService {

    Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private static final String PROJECT_NOT_FOUND = Constants.PROJECT_NOT_FOUND;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void createProject(Project project) {
        projectRepository.save(project);
        logger.debug("Project saved successfully");
    }

    @Override
    public void updateProject(Project project, UUID projectId) throws NotFoundException {

        Optional<Project> result = findProjectById(projectId);
        if (!result.isPresent()) {
            logger.error(PROJECT_NOT_FOUND);
            throw new NotFoundException(PROJECT_NOT_FOUND);

        }
        Project projectDB = result.get();
        projectDB.setName(project.getName());
        projectRepository.save(projectDB);

        logger.debug("Project updated successfully");
    }

    @Override
    public void deleteProject(UUID projectId) throws NotFoundException {
        Optional<Project> result = findProjectById(projectId);
        if (!result.isPresent()) {
            logger.error(PROJECT_NOT_FOUND);
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    public Optional<Project> findProjectById(UUID projectId) {
        return projectRepository.findById(projectId);
    }

}
