package com.shop_style.catalog_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop_style.catalog_service.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CategoryDTO category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<SkuDto> skus;

    public ProductDto(String name, String description, String brand, String material, Boolean active) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.material = material;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductDto that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(brand, that.brand) && Objects.equals(material, that.material) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, brand, material, active, categoryId);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", material='" + material + '\'' +
                ", active=" + active +
                ", categoryId=" + categoryId +
                ", category=" + category +
                '}';
    }
}
