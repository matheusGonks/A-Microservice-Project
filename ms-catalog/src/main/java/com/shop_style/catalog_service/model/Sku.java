package com.shop_style.catalog_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sku sku)) return false;
        return quantity == sku.quantity && height == sku.height && width == sku.width && Objects.equals(price, sku.price) && Objects.equals(color, sku.color) && Objects.equals(size, sku.size) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity, color, size, height, width);
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", product=" + product +
                '}';
    }
}
