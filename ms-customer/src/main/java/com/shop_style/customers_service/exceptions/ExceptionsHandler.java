package com.shop_style.customers_service.exceptions;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception e){
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFound e){
        ApiErrorResponse apiResponse = new ApiErrorResponse(e.getStatus(), e.getMessage());
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e){

        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Cpf or email are already associated with a customer.");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

}
