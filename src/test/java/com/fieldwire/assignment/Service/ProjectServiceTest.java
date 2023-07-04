package com.fieldwire.assignment.Service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fieldwire.assignment.dao.ProjectRepository;
import com.fieldwire.assignment.dto.ProjectDTO;
import com.fieldwire.assignment.model.Project;
import com.fieldwire.assignment.service.ProjectService;
import com.fieldwire.assignment.service.impl.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    public ProjectServiceImpl projectService;

    @Mock
    public ProjectRepository projectRepository;

    private static List<Project> projects;

    private static ProjectDTO projectDTO;

    @BeforeAll
    public static void setupBeforeAll() throws Exception {
        projects = new ArrayList<Project>();
        Project project = new Project();
        project.setName("Proj 1");
        UUID id = UUID.randomUUID();
        project.setId(id);
        projects.add(project);

        projectDTO = new ProjectDTO();
        projectDTO.setId(id);
        projectDTO.setName("Proj 1");
    }

    @Test
    public void testGetProducts() {

        when(projectRepository.findAll()).thenReturn(projects);
        
        List<Project> actualProjects = projectService.getProjects();
        assertNotNull(actualProjects);
        assertSame("ActualProjects and projects",actualProjects.isEmpty(), projects.isEmpty());
        assertSame("ActualProjects name and mocked project name should match ",actualProjects.get(0).getName(), projects.get(0).getName());
        assertSame("ActualProjects id and mocked project id should match ",actualProjects.get(0).getId(), projects.get(0).getId());

    }

    @Test
    public void testCreateProduct() {

        Project project = projects.get(0);
        when(projectRepository.save(project)).thenReturn(project);

        try {
            projectService.createProject(project);
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Exception occured so test failed", true);
        }

    }

    @Test
    public void testUpdateProduct() {

        Project project = projects.get(0);
        Optional<Project> result = Optional.of(projects.get(0));
        when(projectRepository.findById(project.getId())).thenReturn(result);
        when(projectRepository.save(project)).thenReturn(project);

        try {
            projectService.updateProject(project, project.getId());
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Exception occured so test failed", true);

        }

    }

    @Test
    public void testDeleteProduct() {

        Project project = projects.get(0);
        Optional<Project> result = Optional.of(projects.get(0));
        when(projectRepository.findById(project.getId())).thenReturn(result);
        Mockito.doNothing().when(projectRepository).deleteById(projects.get(0).getId());

        try {
            projectService.deleteProject(project.getId());
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Exception occured so test failed", true);
        }

    }

}
