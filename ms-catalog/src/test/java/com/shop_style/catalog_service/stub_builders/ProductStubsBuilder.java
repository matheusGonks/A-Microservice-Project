package com.shop_style.catalog_service.stub_builders;

import com.shop_style.catalog_service.dtos.ProductDto;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.model.Sku;

import java.util.List;

public class ProductStubsBuilder {

    private Long id = 1L;

    private String name = "Red Shirt";

    private String description = "An over sized red shirt thought for your comfort and drip.";

    private String brand = "The North Face";

    private String material = "Cotton";

    private Boolean active = true;

    private Long categoryId = 1L;

    public ProductStubsBuilder(){}

    public Product getInstance(){
        Product p = new Product(
                this.name,
                this.description,
                this.brand,
                this.material,
                this.active
        );

        p.setId(id);
        return p;
    }

    public ProductDto getDto(){
        ProductDto p = new ProductDto(
                this.name,
                this.description,
                this.brand,
                this.material,
                this.active
        );
        p.setCategoryId(id);
        return p;
    }

    // Instance setters  ==================================
    // Instance Objects and lists will be generally initialized outside Builder;
    // Like SKUs list and Category which it belongs to.

    public ProductStubsBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public ProductStubsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductStubsBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductStubsBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductStubsBuilder withMaterial(String material) {
        this.material = material;
        return this;
    }

    public ProductStubsBuilder withActive(Boolean active) {
        this.active = active;
        return this;
    }

    // DTO setters ==================================

    public ProductStubsBuilder withCategoryId(Long id){
        this.categoryId = id;
        return this;
    }
}
