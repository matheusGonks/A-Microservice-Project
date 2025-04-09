package com.shop_style.catalog_service.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @ManyToOne
    private Product product;

    public Sku(BigDecimal price, int quantity, String color, String size, int height, int width) {
        this.price = price;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.height = height;
        this.width = width;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
