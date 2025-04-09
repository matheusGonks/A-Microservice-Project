package com.shop_style.catalog_service.services;

import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
import com.shop_style.catalog_service.exceptions.ProductNotFoundException;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private final long STUB_ID = 1L;

    ProductStubsBuilder productStubsBuilder;

    CategoryStubsBuilder categoryStubsBuilder;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        productStubsBuilder = new ProductStubsBuilder();
        categoryStubsBuilder = new CategoryStubsBuilder();
    }

    @Test
    @DisplayName("ProductService - retrieve all products")
    public void productServiceRetrievesMultipleProducts(){
        List<Product> products = List.of(productStubsBuilder.getInstance());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.retrieveAll();
        assertEquals(products, foundProducts, "Not returned expected list of products." );
    }

    @Test
    @DisplayName("ProductService - retrieval of one product by Id")
    public void productServiceRetrievesProductById(){
        Product product = productStubsBuilder.getInstance();
        when(productRepository.findById(STUB_ID)).thenReturn(Optional.of(product));

        assertEquals(product, productService.retrieveById(STUB_ID), "Did not return correct product.");
        verify(productRepository).findById(STUB_ID);
    }

    @Test
    @DisplayName("ProductService - retrieval of product with non-existent id throws exception")
    public void productServiceDoesNotRetrieveProductOfNonExistentId(){
        when(productRepository.findById(STUB_ID)).thenThrow(new ProductNotFoundException(STUB_ID));
        assertThrows(ProductNotFoundException.class,
                () -> productService.retrieveById(STUB_ID),
                "Did not throw expected exception when searching for product with non-existent id");
    }

    // criar produto
    @Test
    @DisplayName("ProductService - creation of new product")
    public void productServiceCreatesNewProduct(){
        Category category = categoryStubsBuilder.getInstance();
        Product product = productStubsBuilder.getInstance();
        product.setCategory(category);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertEquals(product, productService.saveProduct(product), "Did not return product after saving it in database.");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("ProductService - update of product")
    public void productServiceUpdatesExistingProduct(){
        Category productCategoryBeforeUpdate = categoryStubsBuilder.getInstance();
        Product existingProduct = productStubsBuilder.getInstance();
        existingProduct.setCategory(productCategoryBeforeUpdate);

        Product updatesOfExistingProduct = productStubsBuilder.withDescription("Not as red").getInstance();
        Category newCategory = categoryStubsBuilder.withName("Feminine").getInstance();
        updatesOfExistingProduct.setCategory(newCategory);

        when(productRepository.findById(STUB_ID)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        Product productReturnedFromUpdate = productService.updateProduct(STUB_ID, updatesOfExistingProduct);

        assertEquals(updatesOfExistingProduct, productReturnedFromUpdate, "Did not update product properly.");
        assertEquals(newCategory, productReturnedFromUpdate.getCategory(), "Did not set product new category properly.");
    }

    @Test
    @DisplayName("ProductService - update of product with non-existent id")
    public void productServiceUpdatesProductWithNonExistentId(){
        Product product = productStubsBuilder.getInstance();
        when(productRepository.findById(STUB_ID)).thenThrow(new ProductNotFoundException(STUB_ID));

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(STUB_ID, product),
                "Did not throw excepted exception.");
    }

    @Test
    @DisplayName("ProductService - removal of product")
    public void productServiceRemovesProduct(){
        Product product = productStubsBuilder.getInstance();
        when(productRepository.findById(STUB_ID)).thenReturn(Optional.of(product));

        Product removedProduct = productService.removeProduct(STUB_ID);
        assertEquals(product, removedProduct, "Did not return removed product");
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("ProductService - removal of product with non-existent id")
    public void productServiceRemovesProductWithNonExistentId(){
        when(productRepository.findById(STUB_ID)).thenThrow(new ProductNotFoundException(STUB_ID));
        assertThrows(ProductNotFoundException.class, () -> productService.removeProduct(STUB_ID), "Did not throw expected exception");
    }
}