package com.shop_style.catalog_service.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorResponseTest {

    @Test
    @DisplayName("ApiErrorResponse - create ApiErrorResponse with one error String properly.")
    public void testApiErrorResponseWithOneError(){
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Could not find item.");
        assertEquals(HttpStatus.BAD_REQUEST,apiResponse.getStatus());
        assertEquals("Could not find item.", apiResponse.getErrors().get(0));
    }

    @Test
    @DisplayName("ApiErrorResponse - create ApiErrorResponse with multiple errors.")
    public void testApiErrorResponseWithMultipleErrors(){
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, List.of("There is a problem in name.", "There is a problem in date."));

        assertEquals(HttpStatus.BAD_REQUEST,apiResponse.getStatus());
        assertEquals("There is a problem in name.", apiResponse.getErrors().get(0));
        assertEquals("There is a problem in date.", apiResponse.getErrors().get(1));
    }

}