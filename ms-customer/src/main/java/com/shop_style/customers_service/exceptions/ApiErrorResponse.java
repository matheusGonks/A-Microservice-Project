package com.shop_style.customers_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrorResponse {

    HttpStatus status;
    List<String> errors;

    ApiErrorResponse(HttpStatus status, List<String> errors){
        this.status = status;
        this.errors = errors;
    }

    ApiErrorResponse(HttpStatus status, String errors){
        this.status = status;
        this.errors = Collections.singletonList(errors);
    }

}
