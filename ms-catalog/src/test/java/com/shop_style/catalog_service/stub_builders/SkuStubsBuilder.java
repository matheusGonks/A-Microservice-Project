package com.shop_style.catalog_service.stub_builders;

import com.shop_style.catalog_service.dtos.SkuDto;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.model.Sku;

import java.math.BigDecimal;

public class SkuStubsBuilder {

    private Long id = 1L;

    private BigDecimal price = new BigDecimal("12.49");

    private Integer quantity = 40;

    private String color = "Red";

    private String size = "M";

    private Integer height = 55;

    private Integer width = 39;

    private Long productId = 1L;

    public Sku getInstance(){
        Sku s = new Sku(price, quantity, color, size, height, width);
        s.setId(id);
        return s;
    }

    public SkuDto getDto(){
        return new SkuDto(price, quantity, color, size, height, width, productId);
    }

    // Instance Fields ==============================
    public SkuStubsBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public SkuStubsBuilder withPrice(String price) {
        this.price = new BigDecimal(price);
        return this;
    }

    public SkuStubsBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public SkuStubsBuilder withColor(String color) {
        this.color = color;
        return this;
    }

    public SkuStubsBuilder withSize(String size) {
        this.size = size;
        return this;
    }

    public SkuStubsBuilder withHeight(Integer height) {
        this.height = height;
        return this;
    }

    public SkuStubsBuilder withWidth(Integer width) {
        this.width = width;
        return this;
    }

    // Dto fields ======================================
    public SkuStubsBuilder withProductId(Long productId){
        this.productId = productId;
        return this;
    }
}
