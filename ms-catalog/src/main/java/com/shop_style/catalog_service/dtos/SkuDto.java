package com.shop_style.catalog_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop_style.catalog_service.model.Product;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be at least $0.01.")
    @Digits(integer = 10, fraction = 2, message = "Price have two decimal places.")
    private BigDecimal price;

    @NotNull(message = "A quantity must be provided.")
    @Min(value = 0, message = "Quantity must be at least 0.")
    private Integer quantity;

    @NotBlank(message = "Size is required.")
    @Pattern(regexp = "S|M|L|XL|XXL", message = "Size must be S, M, L, XL or XXL.")
    private String size;

    @NotBlank(message = "Color is required.")
    private String color;

    @NotNull(message = "Height is required.")
    @Min(value = 15, message = "Height must be provided in centimeters and must be at least 15.")
    private Integer height;

    @NotNull(message = "Width is required.")
    @Min(value = 10, message = "Width must be provided in centimeters and must be at least 10.")
    private Integer width;

    @NotNull(message = "Product id is required.")
    @Min(value = 1, message = "Product id must be at least 1.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Product product;

    public SkuDto(BigDecimal price, Integer quantity, String color, String size, Integer height, Integer width, Long productId) {
        this.price = price;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.height = height;
        this.width = width;
        this.productId = productId;
    }
}
