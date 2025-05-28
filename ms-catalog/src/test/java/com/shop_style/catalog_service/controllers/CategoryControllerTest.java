package com.shop_style.catalog_service.controllers;

import com.shop_style.catalog_service.dtos.category.CategoryDTO;
import com.shop_style.catalog_service.facade.GeneralFacade;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private final String API_ENDPOINT = "/v1/categories";

    private final Long STUB_ID = 1L;

    private CategoryStubsBuilder categoryStubsBuilder;

    private CategoryDTO stubCategoryDto1;

    private CategoryDTO stubCategoryDto2;

    private CategoryDTO stubCategoryDto3;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeneralFacade generalFacade;

    @BeforeEach
    void setup(){
        this.categoryStubsBuilder = new CategoryStubsBuilder();

        this.stubCategoryDto1 = categoryStubsBuilder.withId(1L).withName("Masculine").withParentId(null).getDto();
        this.stubCategoryDto2 = categoryStubsBuilder.withId(2L).withName("Feminine").getDto();
        this.stubCategoryDto3 = categoryStubsBuilder.withId(3L).withName("Shirts").getDto();
    }

    //    GET - /v1/categories
    @Test
    @DisplayName("Category Controller - GET requisition for Category by Id")
    public void getRequestForCategoryShouldReturnOkAndCategoryBody() throws Exception {

        when(generalFacade.retrieveCategoryById(STUB_ID)).thenReturn(stubCategoryDto1);

        mockMvc.perform(get(API_ENDPOINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(stubCategoryDto1.getName()))
                .andExpect(jsonPath("$.active").value(stubCategoryDto1.isActive()))
                .andExpect(jsonPath("$.parentId").doesNotExist())
                .andExpect(jsonPath("$.childrenCategories").doesNotExist());

    }

    //    GET - /v1/categories
    @Test
    @DisplayName("Category Controller - GET requisition for all Categories")
    public void getRequestForCategoriesShouldReturnOkAndListOfCategories() throws Exception {

        stubCategoryDto1.setChildrenCategories(List.of(stubCategoryDto3));
        when(generalFacade.retrieveAllCategories()).thenReturn(List.of(stubCategoryDto1, stubCategoryDto2));

        mockMvc.perform(get(API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que retornou 2 itens na lista

                // Verificação do primeiro item (índice 0)
                .andExpect(jsonPath("$[0].id").value(stubCategoryDto1.getId()))
                .andExpect(jsonPath("$[0].name").value(stubCategoryDto1.getName()))
                .andExpect(jsonPath("$[0].active").value(stubCategoryDto1.isActive()))
                .andExpect(jsonPath("$[0].parentId").doesNotExist())

                // Nova verificação da lista childrenCategories com 1 elemento
                .andExpect(jsonPath("$[0].childrenCategories", hasSize(1))) // Verifica tamanho da lista
                .andExpect(jsonPath("$[0].childrenCategories[0].id").value(stubCategoryDto3.getId())) // Verifica que o elemento existe
                .andExpect(jsonPath("$[0].childrenCategories[0].name").value(stubCategoryDto3.getName()))
                .andExpect(jsonPath("$[0].childrenCategories[0].active").value(stubCategoryDto3.isActive()))

                // Verificação do segundo item (índice 1)
                .andExpect(jsonPath("$[1].id").value(stubCategoryDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(stubCategoryDto2.getName()))
                .andExpect(jsonPath("$[1].active").value(stubCategoryDto2.isActive()))
                .andExpect(jsonPath("$[1].parentId").doesNotExist())
                .andExpect(jsonPath("$[1].childrenCategories").doesNotExist());

    }

    //    POST - /v1/categories
    @Test
    @DisplayName("Category Controller - POST requisition of new Category")
    public void postRequestForCategoryShouldReturnCreatedAndCategoryBody() throws Exception {

        when(generalFacade.saveNewCategory(any(CategoryDTO.class))).thenReturn(stubCategoryDto1);

        mockMvc.perform(post(API_ENDPOINT)
                        .content("{ \"name\": \"Masculine\", \"active\": true }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(stubCategoryDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubCategoryDto1.getName()))
                .andExpect(jsonPath("$.active").value(stubCategoryDto1.isActive()))
                .andExpect(jsonPath("$.parentId").doesNotExist())
                .andExpect(jsonPath("$.childrenCategories").doesNotExist());
    }

    //    PUT - /v1/categories/:id
    @Test
    @DisplayName("Category Controller - PUT requisition for Category by Id")
    public void putRequestForCategoryShouldReturnOkAndCategoryBody() throws Exception {

        when(generalFacade.updateCategoryById(anyLong(), any(CategoryDTO.class))).thenReturn(stubCategoryDto1);

        mockMvc.perform(put(API_ENDPOINT + "/1")
                        .content("{ \"name\": \"Masculine\", \"active\": true }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(stubCategoryDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubCategoryDto1.getName()))
                .andExpect(jsonPath("$.active").value(stubCategoryDto1.isActive()))
                .andExpect(jsonPath("$.parentId").doesNotExist())
                .andExpect(jsonPath("$.childrenCategories").doesNotExist());

    }

    //    DELETE - /v1/categories/:id
    @Test
    @DisplayName("Category Controller - DELETE request for Category by Id")
    public void deleteRequestForCategoryShouldReturnOkAndCategoryBody() throws Exception {

        when(generalFacade.removeCategoryById(STUB_ID)).thenReturn(stubCategoryDto1);

        mockMvc.perform(delete(API_ENDPOINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stubCategoryDto1.getId()))
                .andExpect(jsonPath("$.name").value(stubCategoryDto1.getName()))
                .andExpect(jsonPath("$.active").value(stubCategoryDto1.isActive()))
                .andExpect(jsonPath("$.parentId").doesNotExist())
                .andExpect(jsonPath("$.childrenCategories").doesNotExist());

    }
//    GET - /v1/categories/:id/products

}