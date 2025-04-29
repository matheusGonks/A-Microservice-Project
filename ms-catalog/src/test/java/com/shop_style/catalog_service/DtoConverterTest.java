package com.shop_style.catalog_service;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.facade.DtoConverter;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DtoConverterTest {

    private static DtoConverter dtoConverter;

    private CategoryStubsBuilder categoryStubsBuilder;

    private ProductStubsBuilder productStubsBuilder;

    @BeforeAll
    public static void setupAll(){
        dtoConverter = new DtoConverter();
    }

    @BeforeEach
    public void setup(){
        categoryStubsBuilder = new CategoryStubsBuilder();
        productStubsBuilder = new ProductStubsBuilder();
    }

    @Test
    @DisplayName("Dto Converter - creation of Dto from Category")
    public void createDtoFromCategory(){

        Category stub1 = categoryStubsBuilder.withName("Masculine").getInstance();
        CategoryDTO stub1Dto = categoryStubsBuilder.getDto();

        Category stub2 = categoryStubsBuilder.withName("Shirt").getInstance();
        CategoryDTO stub2Dto = categoryStubsBuilder.getDto();
        stub1.setChildrenCategories(List.of(stub2));

        Category stub3 = categoryStubsBuilder.withName("Oversized").getInstance();
        CategoryDTO stub3Dto = categoryStubsBuilder.getDto();
        stub2.setChildrenCategories(List.of(stub3));

        CategoryDTO madeDTO = dtoConverter.makeDtoFromCategory(stub1);
        assertEquals( stub1Dto, madeDTO, "Did not properly created Dto for root Category");

        CategoryDTO childDto = madeDTO.getChildrenCategories().get(0);
        assertEquals( stub2Dto, childDto, "1st generation child category was not properly converted to Dto.");

        CategoryDTO grandchildDto = childDto.getChildrenCategories().get(0);
        assertEquals( stub3Dto, grandchildDto, "2nd generation child category was not properly converted to Dto.");
    }

    @Test
    @DisplayName("Dto Converter - creation of Category from DTO")
    public void createCategoryFromDto(){
       CategoryDTO stubDto = categoryStubsBuilder.getDto();
       Category createdCategory = dtoConverter.makeCategoryFromDto(stubDto);

       assertAll(
               () -> assertEquals(stubDto.getName(), createdCategory.getName(), "Name of created Category doesn't match with Dto's."),
               () -> assertEquals(stubDto.isActive(), createdCategory.isActive(), "Active state of created Category doesn't match with Dto's.")
       );
    }
}