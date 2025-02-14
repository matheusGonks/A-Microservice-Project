package com.shop_style.customers_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFound extends RuntimeException{

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFound(Class<?> c, int id){
        super(String.format("%s with provided ID(%d) does not exist.", c.getSimpleName(), id));
    }
}
