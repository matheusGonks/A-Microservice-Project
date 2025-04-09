package com.shop_style.catalog_service.stub_builders;

import com.shop_style.catalog_service.dtos.ProductDto;
import com.shop_style.catalog_service.model.Product;
import org.postgresql.largeobject.BlobOutputStream;

public class ProductStubsBuilder {

    private String name = "Red Shirt";

    private String description = "An over sized red shirt thought for your comfort and drip.";

    private String brand = "The North Face";

    private String material = "Cotton";

    private Boolean active = true;

    private Long categoryId = 1L;

    public ProductStubsBuilder(){}

    public Product getInstance(){
        return new Product(
                this.name,
                this.description,
                this.brand,
                this.material,
                this.active
        );
    }

    public ProductDto getDto(){
        return new ProductDto(
                this.name,
                this.description,
                this.brand,
                this.material,
                this.active,
                this.categoryId
        );
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

    public ProductStubsBuilder withCategoryId(Long id){
        this.categoryId = id;
        return this;
    }

}
