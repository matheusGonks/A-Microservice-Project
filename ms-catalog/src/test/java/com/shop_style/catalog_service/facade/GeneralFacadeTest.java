package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.dtos.ProductDto;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.services.CategoryService;
import com.shop_style.catalog_service.services.ProductService;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
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

    ProductStubsBuilder productStubsBuilder;

    private final Long STUB_ID = 1L;

    @InjectMocks
    GeneralFacade generalFacade;

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    @BeforeEach
    public void setup(){
        productStubsBuilder = new ProductStubsBuilder();
        categoryStubsBuilder = new CategoryStubsBuilder();
    }

    @Test
    @DisplayName("GeneralFacade - retrieves all Categories")
    public void retrievalOfAllCategories(){
        Category rootCategory1 = categoryStubsBuilder.withName("Feminine").getInstance();
        CategoryDTO rootCategory1DTO = categoryStubsBuilder.getDto();

        Category childCategory = categoryStubsBuilder.withName("Shirt").getInstance();
        rootCategory1.setChildrenCategories(List.of(childCategory));
        CategoryDTO childCategory1DTO = categoryStubsBuilder.getDto();

        Category rootCategory2 = categoryStubsBuilder.withName("Masculine").getInstance();
        CategoryDTO rootCategory2DTO = categoryStubsBuilder.getDto();
        when(categoryService.retrieveAllCategories()).thenReturn(List.of(rootCategory1, rootCategory2));

        List<CategoryDTO> allCategories = generalFacade.retrieveAllCategories();
        assertAll(
                () -> assertEquals(allCategories.get(0), rootCategory1DTO, "Did not convert 1st category properly."),

                () -> assertEquals(allCategories.get(1), rootCategory2DTO, "Did not convert 2nd category properly."),

                () -> assertTrue(allCategories.get(0).getChildrenCategories().contains(childCategory1DTO),
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

        CategoryDTO retrievedCategoryDto = generalFacade.retrieveCategoryById(STUB_ID);
        assertEquals(stubDto, retrievedCategoryDto,"Did not convert Category for Dto");

        verify(categoryService).retrieveCategoryById(STUB_ID);
    }

    @Test
    @DisplayName("General Facade - post of category.")
    public void creationOfCategory(){
        CategoryDTO stubDto = categoryStubsBuilder.withName("Shirt").getDto();
        Category stub = categoryStubsBuilder.getInstance();
        when(categoryService.saveNewCategory(any(Category.class))).thenReturn(stub);

        CategoryDTO createdCategoryDto = generalFacade.saveNewCategory(stubDto);
        assertAll(
                () -> assertEquals(stubDto.getName() , createdCategoryDto.getName(), "Returned DTO name doesn't match."),
                () -> assertEquals(stubDto.isActive(), createdCategoryDto.isActive(), "Returned DTO state doesn't match.")
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

    @Test
    @DisplayName("General Facade - retrieval of all products")
    public void retrieveAllProducts(){
        Product stubProduct1 = productStubsBuilder.getInstance();
        ProductDto stubProduct1Dto = productStubsBuilder.getDto();

        Product stubProduct2 = productStubsBuilder.withName("Dry fit Leggings").getInstance();
        ProductDto stubProduct2Dto = productStubsBuilder.getDto();

        when(productService.retrieveAllProducts()).thenReturn(List.of(stubProduct1, stubProduct2));
        List<ProductDto> allProducts = generalFacade.retrieveAllProducts();
        assertTrue(
                allProducts
                        .stream()
                        .allMatch(dto -> dto.equals(stubProduct1Dto) || dto.equals(stubProduct2Dto))
        );

    }


}