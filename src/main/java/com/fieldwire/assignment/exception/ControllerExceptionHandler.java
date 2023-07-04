package com.fieldwire.assignment.exception;

import org.hibernate.sql.exec.ExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.fieldwire.assignment.dto.ResponseDTO;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { FileNotFoundException.class, NotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseDTO fileNotFoundException(FileNotFoundException ex, WebRequest request) {
        ResponseDTO response = new ResponseDTO(404, ex.getMessage());
        return response;
    }

    @ExceptionHandler(value = { BadRequestException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO badRequestException(BadRequestException ex, WebRequest request) {
        ResponseDTO response = new ResponseDTO(400, ex.getMessage());
        return response;
    }

    @ExceptionHandler(value = { CreationException.class, InterruptedException.class, ExecutionException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO badRequestException(Exception ex, WebRequest request) {
        ResponseDTO response = new ResponseDTO(500, ex.getMessage());
        return response;
    }
}
