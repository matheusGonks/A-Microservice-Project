package com.shop_style.catalog_service;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.facade.GeneralFacade;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.services.CategoryService;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GeneralFacadeTest {

    CategoryStubsBuilder categoryStubsBuilder;

    private final Long STUB_ID = 1L;

    @InjectMocks
    GeneralFacade generalFacade;

    @Mock
    CategoryService categoryService;

    @BeforeEach
    public void setup(){
        categoryStubsBuilder = new CategoryStubsBuilder();
    }

    @Test
    @DisplayName("GeneralFacade - retrieves all Categories")
    public void retrievalOfAllCategories(){
        Category stub1 = categoryStubsBuilder.withName("Feminine").getInstance();
        CategoryDTO stub1DTO = categoryStubsBuilder.getDto();

        Category stub2 = categoryStubsBuilder.withName("Shirt").getInstance();
        stub1.setChildrenCategories(List.of(stub2));
        CategoryDTO stub2DTO = categoryStubsBuilder.getDto();

        Category stub3 = categoryStubsBuilder.withName("Masculine").getInstance();
        CategoryDTO stub3DTO = categoryStubsBuilder.getDto();
        when(categoryService.retrieveAllCategories()).thenReturn(List.of(stub1, stub3));

        List<CategoryDTO> allCategories = generalFacade.retrieveAllCategories();
        assertAll(
                () -> assertEquals(allCategories.get(0), stub1DTO, "Did not convert 1st category properly."),

                () -> assertEquals(allCategories.get(1), stub3DTO, "Did not convert 2nd category properly."),

                () -> assertTrue(allCategories.get(0).getChildrenCategories().contains(stub2DTO),
                        "Did not convert child category properly." )
        );
        verify(categoryService).retrieveAllCategories();
    }

    @Test
    @DisplayName("General Facade - retrieve one category by Id")
    public void retrievalOfCategoryById(){
        Category stubCategory = categoryStubsBuilder.getInstance();
        CategoryDTO stubDto = categoryStubsBuilder.getDto();
        when(categoryService.retrieveCategoryById(any(Long.class))).thenReturn(stubCategory);

        CategoryDTO retrievedCategory = generalFacade.retrieveCategoryById(STUB_ID);
        assertEquals(stubDto, retrievedCategory,"Did not convert Category for Dto");

        verify(categoryService).retrieveCategoryById(STUB_ID);
    }

    @Test
    @DisplayName("General Facade - post of category.")
    public void creationOfCategory(){
        CategoryDTO stubDto = categoryStubsBuilder.withName("Shirt").getDto();
        Category stub = categoryStubsBuilder.getInstance();
        when(categoryService.saveNewCategory(any(Category.class))).thenReturn(stub);

        CategoryDTO createdCategory = generalFacade.saveNewCategory(stubDto);
        assertAll(
                () -> assertEquals(stubDto.getName() , createdCategory.getName(), "Returned DTO name doesn't match."),
                () -> assertEquals(stubDto.isActive(), createdCategory.isActive(), "Returned DTO state doesnt match.")
        );

        verify(categoryService).saveNewCategory(any(Category.class));
    }

    @Test
    @DisplayName("General Facade - update of existing category.")
    public void updateOfExistingCategory(){
        CategoryDTO updatesDto = categoryStubsBuilder.getDto();
        Category updatedCategory = categoryStubsBuilder.getInstance();
        when(categoryService.updateCategory(any(Long.class), any())).thenReturn(updatedCategory);

        CategoryDTO updatedCategoryDto = generalFacade.updateCategory(STUB_ID,updatesDto);
        assertEquals(updatesDto, updatedCategoryDto, "CategoryDTO returned from Facada is not correct. ");

        verify(categoryService).updateCategory(any(Long.class), any());
    }

    @Test
    @DisplayName("General Facade - removal of existing category.")
    public void removeCategoryById(){
        Category removedCategory = categoryStubsBuilder.getInstance();
        CategoryDTO expectedDto = categoryStubsBuilder.getDto();
        when(categoryService.removeCategory(any(Long.class))).thenReturn(removedCategory);

        CategoryDTO removedCategoryDto = generalFacade.removeCategory(STUB_ID);
        assertEquals(expectedDto, removedCategoryDto, "Returned Dto from facade removal is not the expected.");
    }

}