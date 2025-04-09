package com.shop_style.catalog_service.exceptions;

public class EntityNotFoundException extends RuntimeException{

    EntityNotFoundException(Class<?> entityClass, long id){
        super(String.format("%s with provided id = %d not found.", entityClass.getSimpleName(), id));
    }

}
