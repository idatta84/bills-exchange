package com.sample.billsexchange.controller;

import com.sample.billsexchange.exception.NoChangeLeftException;
import com.sample.billsexchange.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoChangeLeftException.class})
    public ResponseEntity<ErrorResponse> handleNoChangeLeft(NoChangeLeftException ncl){
        log.error(ncl.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ncl.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleNoChangeLeft(ConstraintViolationException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage().split(":")[1].trim()), HttpStatus.BAD_REQUEST);
    }
}
