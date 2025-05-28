package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.category.CategoryDTO;
import com.shop_style.catalog_service.dtos.product.ProductDto;
import com.shop_style.catalog_service.dtos.sku.SkuDto;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.model.Sku;
import com.shop_style.catalog_service.services.CategoryService;
import com.shop_style.catalog_service.services.ProductService;
import com.shop_style.catalog_service.services.SkuService;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
import com.shop_style.catalog_service.stub_builders.SkuStubsBuilder;
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

    SkuStubsBuilder skuStubsBuilder;

    private final Long STUB_ID = 1L;

    private Category stubCategory;

    private CategoryDTO stubCategoryDto;

    private Product stubProduct;

    private ProductDto stubProductDto;

    private Sku stubSku;

    private SkuDto stubSkuDto;

    @InjectMocks
    GeneralFacade generalFacade;

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    @Mock
    SkuService skuService;

    @Mock
    DtoConverter dtoConverter;

    @BeforeEach
    public void setup(){
        productStubsBuilder = new ProductStubsBuilder();
        categoryStubsBuilder = new CategoryStubsBuilder();
        skuStubsBuilder = new SkuStubsBuilder();

        stubCategory = categoryStubsBuilder.getInstance();
        stubCategoryDto = categoryStubsBuilder.getDto();

        stubProduct = productStubsBuilder.getInstance();
        stubProductDto = productStubsBuilder.getDto();

        stubSkuDto = skuStubsBuilder.getDto();
        stubSku = skuStubsBuilder.getInstance();
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
        when(dtoConverter.makeTreeOfDtosFromCategory(rootCategory1)).thenReturn(rootCategory1DTO);
        when(dtoConverter.makeTreeOfDtosFromCategory(rootCategory2)).thenReturn(rootCategory2DTO);
        rootCategory1DTO.setChildrenCategories(List.of(childCategory1DTO));

        List<CategoryDTO> allCategories = generalFacade.retrieveAllCategories();
        assertAll(
                () -> assertEquals(rootCategory1DTO, allCategories.get(0),"Did not convert 1st category properly."),

                () -> assertEquals(rootCategory2DTO, allCategories.get(1),"Did not convert 2nd category properly."),

                () -> assertTrue(allCategories.get(0).getChildrenCategories().contains(childCategory1DTO),
                        "Did not convert child category properly." )
        );
        verify(categoryService).retrieveAllCategories();
    }

    @Test
    @DisplayName("General Facade - retrieve one category by Id")
    public void retrievalOfCategoryById(){
        when(categoryService.retrieveCategoryById(any(Long.class))).thenReturn(stubCategory);
        when(dtoConverter.makeDtoFromCategory(stubCategory)).thenReturn(stubCategoryDto);

        CategoryDTO retrievedCategoryDto = generalFacade.retrieveCategoryById(STUB_ID);
        assertEquals(stubCategoryDto, retrievedCategoryDto,"Did not convert Category for Dto");
        verify(dtoConverter).makeDtoFromCategory(stubCategory);
        verify(categoryService).retrieveCategoryById(STUB_ID);
    }

    @Test
    @DisplayName("General Facade - post of category.")
    public void creationOfCategory(){
        when(dtoConverter.makeCategoryFromDto(stubCategoryDto)).thenReturn(stubCategory);
        when(categoryService.saveNewCategory(any(Category.class))).thenReturn(stubCategory);
        when(dtoConverter.makeDtoFromCategory(stubCategory)).thenReturn(stubCategoryDto);

        CategoryDTO createdCategoryDto = generalFacade.saveNewCategory(stubCategoryDto);
        assertEquals(stubCategoryDto, createdCategoryDto, "Returned DTO doesn't match.");
    }

    @Test
    @DisplayName("General Facade - update of existing category.")
    public void updateOfExistingCategory(){
        when(dtoConverter.makeCategoryFromDto(stubCategoryDto)).thenReturn(stubCategory);
        when(categoryService.updateCategory(any(Long.class), any())).thenReturn(stubCategory);
        when(dtoConverter.makeDtoFromCategory(stubCategory)).thenReturn(stubCategoryDto);

        CategoryDTO updatedCategoryDto = generalFacade.updateCategoryById(STUB_ID, stubCategoryDto);
        assertEquals(stubCategoryDto, updatedCategoryDto, "CategoryDTO returned from Facada is not correct. ");
    }

    @Test
    @DisplayName("General Facade - removal of existing category.")
    public void removeCategoryByIdById(){
        when(categoryService.removeCategory(any(Long.class))).thenReturn(stubCategory);
        when(dtoConverter.makeDtoFromCategory(stubCategory)).thenReturn(stubCategoryDto);

        CategoryDTO removedCategoryDto = generalFacade.removeCategoryById(STUB_ID);
        assertEquals(stubCategoryDto, removedCategoryDto, "Returned Dto from facade removal is not the expected.");
        verify(categoryService).removeCategory(STUB_ID);
        verify(dtoConverter).makeDtoFromCategory(stubCategory);
    }

    @Test
    @DisplayName("General Facade - retrieval of all products")
    public void retrieveAllProducts(){
        Product stubProduct1 = productStubsBuilder.getInstance();
        ProductDto stubProduct1Dto = productStubsBuilder.getDto();

        Product stubProduct2 = productStubsBuilder.withName("Dry fit Leggings").getInstance();
        ProductDto stubProduct2Dto = productStubsBuilder.getDto();

        when(productService.retrieveAllProducts()).thenReturn(List.of(stubProduct1, stubProduct2));
        when(dtoConverter.makeDtoFromProduct(stubProduct1)).thenReturn(stubProduct1Dto);
        when(dtoConverter.makeDtoFromProduct(stubProduct2)).thenReturn(stubProduct2Dto);

        List<ProductDto> allProducts = generalFacade.retrieveAllProducts();
        assertTrue(
                allProducts
                        .stream()
                        .allMatch(dto -> dto.equals(stubProduct1Dto) || dto.equals(stubProduct2Dto))
        );
    }

    @Test
    @DisplayName("General Facade - retrieval of one product by Id")
    public void retrieveOneProductById(){
        when(dtoConverter.makeDtoFromProduct(stubProduct)).thenReturn(stubProductDto);
        when(productService.retrieveById(STUB_ID)).thenReturn(stubProduct);

        assertEquals(stubProductDto, generalFacade.retrieveProductById(STUB_ID), "Retrieved product is not as expected.");
        verify(productService).retrieveById(STUB_ID);
        verify(dtoConverter).makeDtoFromProduct(stubProduct);
    }

    @Test
    @DisplayName("General Facade - creation of new Product")
    public void createProduct(){
        Long owningCategoryId = stubProductDto.getCategoryId();

        when(dtoConverter.makeProductFromDto(stubProductDto)).thenReturn(stubProduct);
        when(productService.saveProduct(stubProduct)).thenReturn(stubProduct);
        when(dtoConverter.makeDtoFromProduct(stubProduct)).thenReturn(stubProductDto);

        ProductDto createdProduct = generalFacade.saveNewProduct(stubProductDto);
        assertEquals(stubProductDto, createdProduct, "Product returned from facade after creation is not the expected.");
        verify(dtoConverter).makeDtoFromProduct(stubProduct);
        verify(categoryService).retrieveCategoryById(owningCategoryId);
        verify(productService).saveProduct(stubProduct);
        verify(dtoConverter).makeProductFromDto(stubProductDto);
    }

    @Test
    @DisplayName("General Facade - update of existing Product")
    public void updateExistingProduct(){
        Long owningCategoryId = stubProductDto.getCategoryId();
        when(dtoConverter.makeProductFromDto(stubProductDto)).thenReturn(stubProduct);
        when(productService.updateProduct(STUB_ID, stubProduct)).thenReturn(stubProduct);
        when(dtoConverter.makeDtoFromProduct(stubProduct)).thenReturn(stubProductDto);

        ProductDto updatedProductDto = generalFacade.updateProductById(STUB_ID, stubProductDto);
        assertEquals(stubProductDto, updatedProductDto, "Product returned from update is not as expected");
        verify(dtoConverter).makeProductFromDto(stubProductDto);
        verify(dtoConverter).makeDtoFromProduct(stubProduct);
        verify(categoryService).retrieveCategoryById(owningCategoryId);
    }

    @Test
    @DisplayName("General Facade - removal of existing Product")
    public void deleteExistingProduct(){
        when(productService.removeProduct(STUB_ID)).thenReturn(stubProduct);
        when(dtoConverter.makeDtoFromProduct(stubProduct)).thenReturn(stubProductDto);

        ProductDto removedProduct = generalFacade.removeProductById(STUB_ID);
        assertEquals(stubProductDto, removedProduct, "Product returned from removal is not as expected.");
        verify(productService).removeProduct(STUB_ID);
        verify(dtoConverter).makeDtoFromProduct(stubProduct);
    }

    @Test
    @DisplayName("General Facade - creation of new Sku")
    public void saveNewSku(){
        Long productId = stubSkuDto.getProductId();

        when(dtoConverter.makeSkuFromDto(stubSkuDto)).thenReturn(stubSku);
        when(dtoConverter.makeDtoFromSku(stubSku)).thenReturn(stubSkuDto);
        when(productService.retrieveById(productId)).thenReturn(stubProduct);
        when(skuService.saveSku(stubSku)).thenReturn(stubSku);

        SkuDto createdSku = generalFacade.saveNewSku(stubSkuDto);
        assertEquals(stubSkuDto, createdSku, "Sku returned from facade is not as expected.");
        verify(dtoConverter).makeDtoFromSku(stubSku);
        verify(dtoConverter).makeSkuFromDto(stubSkuDto);
        verify(skuService).saveSku(stubSku);
        verify(productService).retrieveById(productId);
    }

    @Test
    @DisplayName("General Facade - update of existing Sku")
    public void updateSkuById(){
        Long productId = stubSkuDto.getProductId();

        when(dtoConverter.makeSkuFromDto(stubSkuDto)).thenReturn(stubSku);
        when(dtoConverter.makeDtoFromSku(stubSku)).thenReturn(stubSkuDto);
        when(productService.retrieveById(productId)).thenReturn(stubProduct);
        when(skuService.updateSku(STUB_ID , stubSku)).thenReturn(stubSku);

        SkuDto updatedSku = generalFacade.updateSkuById(STUB_ID, stubSkuDto);
        assertEquals(stubSkuDto, updatedSku, "Sku returned from facade is not as expected.");
        verify(dtoConverter).makeDtoFromSku(stubSku);
        verify(dtoConverter).makeSkuFromDto(stubSkuDto);
        verify(skuService).updateSku(STUB_ID, stubSku);
        verify(productService).retrieveById(productId);
    }

    @Test
    @DisplayName("General Facade - removal of existing Sku")
    public void removeSkuById(){
        SkuDto stubSkuDto = skuStubsBuilder.getDto();
        Sku stubSku = skuStubsBuilder.getInstance();

        when(skuService.removeSku(STUB_ID)).thenReturn(stubSku);
        when(dtoConverter.makeDtoFromSku(stubSku)).thenReturn(stubSkuDto);

        SkuDto removedSku = generalFacade.removeSkuById(STUB_ID);
        assertEquals(stubSkuDto,removedSku, "Sku returned from removal is no as expected.");
        verify(dtoConverter).makeDtoFromSku(stubSku);
        verify(skuService).removeSku(STUB_ID);
    }

}