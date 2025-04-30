package com.shop_style.catalog_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private boolean active;

    private Category category;

    private List<Sku> skus;

    public Product(String name, String description, String brand, String material, boolean active) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.material = material;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return active == product.active && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) && Objects.equals(material, product.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, brand, material, active);
    }
}
