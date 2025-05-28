package com.shop_style.catalog_service.controllers;

import com.shop_style.catalog_service.dtos.product.ProductDto;
import com.shop_style.catalog_service.facade.GeneralFacade;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
import com.shop_style.catalog_service.stub_builders.SkuStubsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private final String API_ENDPOINT = "/v1/products";

    private final Long STUB_ID = 1L;

    private ProductStubsBuilder productStubsBuilder;

    private SkuStubsBuilder skuStubsBuilder;

    private ProductDto stubProductDto1;

    private ProductDto stubProductDto2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeneralFacade generalFacade;

    @BeforeEach
    void setup(){
        this.productStubsBuilder = new ProductStubsBuilder();
        this.skuStubsBuilder = new SkuStubsBuilder();

        this.stubProductDto1 = productStubsBuilder.getDto();
        stubProductDto1.setSkus(List.of(skuStubsBuilder.getDto()));

        this.stubProductDto2 = productStubsBuilder.getDto();
    }

    @Test
    @DisplayName("Category Controller - GET requisition for Category by Id")
    public void getRequestForCategoryShouldReturnOkAndCategoryBody() throws Exception {

        when(generalFacade.retrieveProductById(STUB_ID)).thenReturn(stubProductDto1);

        mockMvc.perform(get(API_ENDPOINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stubProductDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubProductDto1.getName()))
                .andExpect(jsonPath("$.description").value(stubProductDto1.getDescription()))
                .andExpect(jsonPath("$.brand").value(stubProductDto1.getBrand()))
                .andExpect(jsonPath("$.material").value(stubProductDto1.getMaterial()))
                .andExpect(jsonPath("$.active").value(stubProductDto1.getActive()))
                .andExpect(jsonPath("$.categoryId").value(stubProductDto1.getCategoryId()))
                .andExpect(jsonPath("$.skus").isArray());

    }

    //    GET - /v1/products
    @Test
    @DisplayName("Product Controller - GET requisition for all products")
    public void getRequestForProductsShouldReturnOkAndListOfCategories() throws Exception {

        when(generalFacade.retrieveAllProducts()).thenReturn(List.of(stubProductDto1, stubProductDto2));

        mockMvc.perform(get(API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que retornou 2 itens na lista

                .andExpect(jsonPath("$[0].id").value(stubProductDto1.getId()))
                .andExpect(jsonPath("$[0].name").value(stubProductDto1.getName()))
                .andExpect(jsonPath("$[0].description").value(stubProductDto1.getDescription()))
                .andExpect(jsonPath("$[0].brand").value(stubProductDto1.getBrand()))
                .andExpect(jsonPath("$[0].material").value(stubProductDto1.getMaterial()))
                .andExpect(jsonPath("$[0].active").value(stubProductDto1.getActive()))
                .andExpect(jsonPath("$[0].categoryId").value(stubProductDto1.getCategoryId()))
                .andExpect(jsonPath("$[0].skus").isArray())

                .andExpect(jsonPath("$[1].id").value(stubProductDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(stubProductDto2.getName()))
                .andExpect(jsonPath("$[1].description").value(stubProductDto2.getDescription()))
                .andExpect(jsonPath("$[1].brand").value(stubProductDto2.getBrand()))
                .andExpect(jsonPath("$[1].material").value(stubProductDto2.getMaterial()))
                .andExpect(jsonPath("$[1].active").value(stubProductDto2.getActive()))
                .andExpect(jsonPath("$[1].categoryId").value(stubProductDto2.getCategoryId()))
                .andExpect(jsonPath("$[1].skus").doesNotExist());


    }

    //    POST - /v1/products
    @Test
    @DisplayName("Product Controller - POST requisition of new Product")
    public void postRequestForCategoryShouldReturnCreatedAndCategoryBody() throws Exception {
        stubProductDto1.setSkus(null);
        when(generalFacade.saveNewProduct(any(ProductDto.class))).thenReturn(stubProductDto1);

        mockMvc.perform(post(API_ENDPOINT)
                        .content("{\"name\":\"Red Shirt\",\"description\":\"An over sized red shirt thought for your comfort and drip.\",\"brand\":\"The North Face\",\"material\":\"Cotton\",\"active\":true,\"categoryId\":1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(stubProductDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubProductDto1.getName()))
                .andExpect(jsonPath("$.description").value(stubProductDto1.getDescription()))
                .andExpect(jsonPath("$.brand").value(stubProductDto1.getBrand()))
                .andExpect(jsonPath("$.material").value(stubProductDto1.getMaterial()))
                .andExpect(jsonPath("$.active").value(stubProductDto1.getActive()))
                .andExpect(jsonPath("$.categoryId").value(stubProductDto1.getCategoryId()))
                .andExpect(jsonPath("$.skus").doesNotExist());

    }

    //    PUT - /v1/products/:id
    @Test
    @DisplayName("Product Controller - PUT requisition for Product by Id")
    public void putRequestForCategoryShouldReturnOkAndCategoryBody() throws Exception {

        when(generalFacade.updateProductById(anyLong(), any(ProductDto.class))).thenReturn(stubProductDto1);

        mockMvc.perform(put(API_ENDPOINT + "/1")
                        .content("{\"id\":1,\"name\":\"Red Shirt\",\"description\":\"An over sized red shirt thought for your comfort and drip.\",\"brand\":\"The North Face\",\"material\":\"Cotton\",\"active\":true,\"categoryId\":1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stubProductDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubProductDto1.getName()))
                .andExpect(jsonPath("$.description").value(stubProductDto1.getDescription()))
                .andExpect(jsonPath("$.brand").value(stubProductDto1.getBrand()))
                .andExpect(jsonPath("$.material").value(stubProductDto1.getMaterial()))
                .andExpect(jsonPath("$.active").value(stubProductDto1.getActive()))
                .andExpect(jsonPath("$.categoryId").value(stubProductDto1.getCategoryId()))
                .andExpect(jsonPath("$.skus").isArray());

    }

    //    DELETE - /v1/categories/:id
    @Test
    @DisplayName("Product Controller - DELETE request for Product by Id")
    public void deleteRequestForCategoryShouldReturnOkAndCategoryBody() throws Exception {

        when(generalFacade.removeProductById(STUB_ID)).thenReturn(stubProductDto1);

        mockMvc.perform(delete(API_ENDPOINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stubProductDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubProductDto1.getName()))
                .andExpect(jsonPath("$.description").value(stubProductDto1.getDescription()))
                .andExpect(jsonPath("$.brand").value(stubProductDto1.getBrand()))
                .andExpect(jsonPath("$.material").value(stubProductDto1.getMaterial()))
                .andExpect(jsonPath("$.active").value(stubProductDto1.getActive()))
                .andExpect(jsonPath("$.categoryId").value(stubProductDto1.getCategoryId()))
                .andExpect(jsonPath("$.skus").isArray());
    }

}