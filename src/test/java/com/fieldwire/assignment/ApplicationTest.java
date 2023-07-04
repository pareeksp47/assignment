package com.fieldwire.assignment;

import com.fieldwire.assignment.dto.ResponseDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.apache.catalina.connector.Response;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTest {

    private static final String PROJECT_ENDPOINT = "api/projects";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldRespondWithStatusAndDescription() {
        ResponseDTO responseDTO = restTemplate.getForObject(getHelloEndpointUrl(), ResponseDTO.class);

        // assertThat(ResponseDTO.STATUS_200_OK).isNotEqualTo("");
    }

    private String getHelloEndpointUrl() {
        return getLocalServerUrl() + PROJECT_ENDPOINT;
    }

    private String getLocalServerUrl() {
        return String.format("http://localhost:%d/", port);
    }
}
