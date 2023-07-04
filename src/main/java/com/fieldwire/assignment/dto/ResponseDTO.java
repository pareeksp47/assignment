package com.fieldwire.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseDTO {

    private Integer statusCode;
    private String description;

    public ResponseDTO() {

    }

    public ResponseDTO(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Status 200
     */
    public static ResponseDTO STATUS_200_OK = new ResponseDTO(200, "Request successful");

    /**
     * Status 201 creation success
     */
    public static ResponseDTO STATUS_201_OK = new ResponseDTO(201, "Created Success");

    /**
     * Status 1000
     */
    public static ResponseDTO STATUS_1000_UNDEFINED_EXCEPTION = new ResponseDTO(1000, "undefined exception");
}
