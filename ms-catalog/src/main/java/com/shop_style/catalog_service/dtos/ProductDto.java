package com.shop_style.catalog_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank(message = "Product name is required.")
    private String name;

    private String description;

    @NotBlank(message = "Brand is required.")
    private String brand;

    private String material;

    @NotNull(message = "Active state is required.")
    private Boolean active;

    @NotNull(message = "An associated category must be provided.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    public ProductDto(String name, String description, String brand, String material, Boolean active, Long categoryId) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.material = material;
        this.active = active;
        this.categoryId = categoryId;
    }
}
