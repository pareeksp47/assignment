package com.fieldwire.assignment.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;

import com.fieldwire.assignment.dao.ProjectRepository;
import com.fieldwire.assignment.dto.ProjectDTO;
import com.fieldwire.assignment.dto.ResponseDTO;
import com.fieldwire.assignment.model.Project;
import com.fieldwire.assignment.service.ProjectService;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    private static final String PROJECT_ENDPOINT = "api/projects";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    public ProjectService projectService;

    @Mock
    public ProjectRepository projectRepository;

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
    }

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
        when(projectService.getProjects()).thenReturn(projects);

        List projectDTOs = restTemplate.getForObject(getProjectsEndpoint(), ArrayList.class);
        assertThat(projectDTOs.isEmpty()).isEqualTo(new ArrayList<>().isEmpty());
    }

    @Test
    public void testCreateProduct() {

        Project project = projects.get(0);
        when(projectRepository.save(project)).thenReturn(project);

        ResponseDTO responseDTO = restTemplate.postForObject(getProjectsEndpoint(), any(), ResponseDTO.class,
                eq(String.class),
                null);
        assertNotNull(responseDTO);
        assertThat(responseDTO.getStatusCode()).isEqualTo(201);
    }

    @Test
    public void testUpdateProduct() {

        Project project = projects.get(0);
        when(projectRepository.save(project)).thenReturn(project);

        ResponseDTO responseDTO = restTemplate.patchForObject(getProjectsEndpoint(), any(), ResponseDTO.class,
                eq(String.class),
                null);
        assertNotNull(responseDTO);
        assertThat(responseDTO.getStatusCode()).isEqualTo(204);
    }

    @Test
    public void testDeleteProduct() {

        Optional<Project> project = Optional.of(projects.get(0));

        when(projectRepository.findById(projects.get(0).getId())).thenReturn(project);
        Mockito.doThrow(new RuntimeException()).doNothing().when(projectRepository).delete(projects.get(0));

        try {
            restTemplate.delete(getProjectsEndpoint(),
                    projects.get(0).getId());
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Exception occured so test failed", true);
        }

    }

    private String getProjectsEndpoint() {
        return getLocalServerUrl() + PROJECT_ENDPOINT;
    }

    private String getLocalServerUrl() {
        return String.format("http://localhost:%d/", port);
    }
}
