package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.dtos.ProductDto;
import com.shop_style.catalog_service.dtos.SkuDto;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.model.Sku;

import java.util.List;

public class DtoConverter {

    public Category makeCategoryFromDto(CategoryDTO categoryDTO){
        return new Category(
                categoryDTO.getName(),
                categoryDTO.isActive()
        );
    }

    public Product makeProductFromDto(ProductDto productDto){
        return new Product(
            productDto.getName(),
            productDto.getDescription(),
            productDto.getBrand(),
            productDto.getMaterial(),
            productDto.getActive()
        );
    }

    public Sku makeSkuFromDto(SkuDto skuDto){
        return new Sku(
                skuDto.getPrice(),
                skuDto.getQuantity(),
                skuDto.getColor(),
                skuDto.getSize(),
                skuDto.getHeight(),
                skuDto.getWidth()
        );
    }

    public CategoryDTO makeTreeOfDtosFromCategory(Category category){
        if(category == null) return null;

        List<CategoryDTO> childrenDtos = category
                .getChildrenCategories()
                .stream()
                .map(this::makeTreeOfDtosFromCategory)
                .toList();

        CategoryDTO categoryDTO = makeDtoFromCategory(category);
        categoryDTO.setChildrenCategories(childrenDtos);

        return categoryDTO;
    }

    public CategoryDTO makeDtoFromCategory(Category category){
        return (category.hasParent())?
                new CategoryDTO(category.getId(), category.getName(), category.isActive(), category.getParent().getId())
                :
                new CategoryDTO(category.getId(), category.getName(), category.isActive());
    }

    public ProductDto makeDtoFromProduct(Product product){
        ProductDto productDto = getProductDtoWithFieldsSet(product);
        CategoryDTO categoryDTO = makeDtoFromCategory(product.getCategory());
        List<SkuDto> skusDtos = product.getSkus().stream().map(this::makeDtoFromSku).toList();

        productDto.setCategory(categoryDTO);
        productDto.setSkus(skusDtos);
        return productDto;
    }

    private ProductDto getProductDtoWithFieldsSet(Product product){
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getMaterial(),
                product.isActive()
        );
    }

    public SkuDto makeDtoFromSku(Sku sku){
        return new SkuDto(
            sku.getPrice(),
            sku.getQuantity(),
            sku.getColor(),
            sku.getSize(),
            sku.getHeight(),
            sku.getWidth(),
            sku.getProduct().getId()
        );
    }

}
