package com.shop_style.catalog_service.controllers;

import com.shop_style.catalog_service.dtos.product.ProductDto;
import com.shop_style.catalog_service.dtos.sku.SkuDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SkuController.class)
class SkuControllerTest {

    private final String API_ENDPOINT = "/v1/skus";

    private final Long STUB_ID = 1L;

    private ProductStubsBuilder productStubsBuilder;

    private SkuStubsBuilder skuStubsBuilder;

    private SkuDto stubSkuDto1;

    private ProductDto stubProductDto1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeneralFacade generalFacade;

    @BeforeEach
    void setup(){
        this.productStubsBuilder = new ProductStubsBuilder();
        this.skuStubsBuilder = new SkuStubsBuilder();

        this.stubProductDto1 = productStubsBuilder.getDto();
        this.stubSkuDto1 = skuStubsBuilder.getDto();
        stubSkuDto1.setProduct(stubProductDto1);
    }

    @Test
    @DisplayName("Sku Controller - Post requisition for new Sku")
    public void requestShouldReturnCreatedSku() throws Exception {

        when(generalFacade.saveNewSku(any(SkuDto.class))).thenReturn(stubSkuDto1);

        mockMvc.perform(post(API_ENDPOINT )
                        .content("{\"price\":12.49,\"quantity\":40,\"color\":\"Red\",\"size\":\"M\",\"height\":55,\"width\":39,\"productId\":1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(stubSkuDto1.getId()))
                .andExpect(jsonPath("$.price").value(stubSkuDto1.getPrice()))
                .andExpect(jsonPath("$.quantity").value(stubSkuDto1.getQuantity()))
                .andExpect(jsonPath("$.color").value(stubSkuDto1.getColor()))
                .andExpect(jsonPath("$.size").value(stubSkuDto1.getSize()))
                .andExpect(jsonPath("$.height").value(stubSkuDto1.getHeight()))
                .andExpect(jsonPath("$.width").value(stubSkuDto1.getWidth()))
                .andExpect(jsonPath("$.product.id").value(stubProductDto1.getId()));
    }

    @Test
    @DisplayName("Sku Controller - Put requisition for existing Sku")
    public void requestShouldReturnUpdatedSku() throws Exception {

        when(generalFacade.updateSkuById(anyLong(), any(SkuDto.class))).thenReturn(stubSkuDto1);

        mockMvc.perform(put(API_ENDPOINT + "/1")
                        .content("{\"price\":12.49,\"quantity\":40,\"color\":\"Red\",\"size\":\"M\",\"height\":55,\"width\":39,\"productId\":1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(stubSkuDto1.getId()))
                .andExpect(jsonPath("$.price").value(stubSkuDto1.getPrice()))
                .andExpect(jsonPath("$.quantity").value(stubSkuDto1.getQuantity()))
                .andExpect(jsonPath("$.color").value(stubSkuDto1.getColor()))
                .andExpect(jsonPath("$.size").value(stubSkuDto1.getSize()))
                .andExpect(jsonPath("$.height").value(stubSkuDto1.getHeight()))
                .andExpect(jsonPath("$.width").value(stubSkuDto1.getWidth()))
                .andExpect(jsonPath("$.product.id").value(stubProductDto1.getId()));

    }

    @Test
    @DisplayName("Sku Controller - Delete requisition for existing Sku")
    public void requestShouldReturnDeletedSku() throws Exception {

        when(generalFacade.removeSkuById(anyLong())).thenReturn(stubSkuDto1);

        mockMvc.perform(delete(API_ENDPOINT + "/1"))
                .andExpect(jsonPath("$.id").value(stubSkuDto1.getId()))
                .andExpect(jsonPath("$.price").value(stubSkuDto1.getPrice()))
                .andExpect(jsonPath("$.quantity").value(stubSkuDto1.getQuantity()))
                .andExpect(jsonPath("$.color").value(stubSkuDto1.getColor()))
                .andExpect(jsonPath("$.size").value(stubSkuDto1.getSize()))
                .andExpect(jsonPath("$.height").value(stubSkuDto1.getHeight()))
                .andExpect(jsonPath("$.width").value(stubSkuDto1.getWidth()))
                .andExpect(jsonPath("$.product.id").value(stubProductDto1.getId()));
    }



}