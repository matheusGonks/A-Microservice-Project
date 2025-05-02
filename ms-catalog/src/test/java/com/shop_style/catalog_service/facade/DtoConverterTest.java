package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.dtos.ProductDto;
import com.shop_style.catalog_service.dtos.SkuDto;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.model.Sku;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
import com.shop_style.catalog_service.stub_builders.SkuStubsBuilder;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DtoConverterTest {

    private static DtoConverter dtoConverter;

    private CategoryStubsBuilder categoryStubsBuilder;

    private ProductStubsBuilder productStubsBuilder;

    private SkuStubsBuilder skuStubsBuilder;

    @BeforeAll
    public static void setupAll(){
        dtoConverter = new DtoConverter();
    }

    @BeforeEach
    public void setup(){
        categoryStubsBuilder = new CategoryStubsBuilder();
        productStubsBuilder = new ProductStubsBuilder();
        skuStubsBuilder = new SkuStubsBuilder();
    }

    @Test
    @DisplayName("Dto Converter - creation of tree of Dtos from Category")
    public void createTreeOfDtosFromCategory(){
        Category stub1 = categoryStubsBuilder.withName("Masculine").getInstance();
        CategoryDTO stub1Dto = categoryStubsBuilder.getDto();

        Category stub2 = categoryStubsBuilder.withName("Shirt").getInstance();
        CategoryDTO stub2Dto = categoryStubsBuilder.getDto();
        stub1.setChildrenCategories(List.of(stub2));

        Category stub3 = categoryStubsBuilder.withName("Oversized").getInstance();
        CategoryDTO stub3Dto = categoryStubsBuilder.getDto();
        stub2.setChildrenCategories(List.of(stub3));

        CategoryDTO madeDTO = dtoConverter.makeTreeOfDtosFromCategory(stub1);
        CategoryDTO childDto = madeDTO.getChildrenCategories().get(0);
        CategoryDTO grandchildDto = childDto.getChildrenCategories().get(0);
        assertAll(
                () -> assertEquals( stub2Dto, childDto, "1st generation child category was not properly converted to Dto."),
                () -> assertEquals( stub1Dto, madeDTO, "Did not properly created Dto for root Category"),
                () -> assertEquals( stub3Dto, grandchildDto, "2nd generation child category was not properly converted to Dto.")
        );
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

    @Test
    @DisplayName("Dto Converter - creation of Dto from Sku")
    public void createDtoFromSku(){
        Sku sku = skuStubsBuilder.getInstance();
        Product product = productStubsBuilder.getInstance();
        sku.setProduct(product);
        SkuDto expectedDto = skuStubsBuilder.getDto();

        SkuDto createdDto = dtoConverter.makeDtoFromSku(sku);
        assertEquals(expectedDto, createdDto, "Created Sku Dto is not as expected.");
    }

    @Test
    @DisplayName("Dto Converter - creation of Sku from Dto")
    public void createSkuFromDto(){
        Sku expectedSku = skuStubsBuilder.getInstance();
        SkuDto sourceDto = skuStubsBuilder.getDto();

        Sku createdSku = dtoConverter.makeSkuFromDto(sourceDto);
        assertEquals(expectedSku, createdSku, "Sku created is not as expected.");
    }

    @Test
    @DisplayName("Dto Converter - creation of Product from Dto")
    public void createProductFromDto(){
        Product expectedProduct = productStubsBuilder.getInstance();
        ProductDto sourceDro = productStubsBuilder.getDto();

        Product createdProduct = dtoConverter.makeProductFromDto(sourceDro);
        assertEquals(expectedProduct, createdProduct, "Product created is not as expected.");
    }

    @Test
    @DisplayName("Dto Converter - creation of DTO from Product")
    public void createDtoFromProduct() {
        Product product = productStubsBuilder.getInstance();
        ProductDto expectedProductDto = productStubsBuilder.getDto();

        Category category = categoryStubsBuilder.getInstance();
        CategoryDTO expectedCategoryDTO = categoryStubsBuilder.getDto();

        Sku sku = skuStubsBuilder.getInstance();
        sku.setProduct(product);
        SkuDto expectedSkuDto = skuStubsBuilder.getDto();

        product.setCategory(category);
        product.setSkus(Collections.singletonList(sku));

        ProductDto createdProductDto = dtoConverter.makeDtoFromProduct(product);
        assertAll(
                () -> assertEquals(expectedProductDto, createdProductDto, "Product dto fields don't match."),
                () -> assertEquals(expectedCategoryDTO, createdProductDto.getCategory(), "Category dto in Product doesn't match."),
                () -> assertEquals(expectedSkuDto, createdProductDto.getSkus().get(0), "Sku dto in Product doesn't match.")
        );
    }
}