package com.shop_style.catalog_service.exceptions;

import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.List;

public class ApiErrorResponse {

    private final HttpStatus status;
    private final List<String> errors;

    public ApiErrorResponse(HttpStatus status, String error){
        this.status = status;
        this.errors = Collections.singletonList(error);
    }

    public ApiErrorResponse(HttpStatus status, List<String> errors){
        this.status = status;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
