package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.model.Category;

import java.util.List;

public class DtoConverter {

    public Category makeCategoryFromDto(CategoryDTO categoryDTO){
        return new Category(
                categoryDTO.getName(),
                categoryDTO.isActive()
        );
    }

    public CategoryDTO makeDtoFromCategory(Category category){
        if(category == null) return null;

        List<CategoryDTO> childrenDtos = category
                .getChildrenCategories()
                .stream()
                .map(this::makeDtoFromCategory)
                .toList();

        CategoryDTO categoryDTO = buildDtoFromInstance(category);
        categoryDTO.setChildrenCategories(childrenDtos);

        return categoryDTO;
    }

    private CategoryDTO buildDtoFromInstance(Category category){
        return (category.hasParent())?
                new CategoryDTO(category.getId(), category.getName(), category.isActive(), category.getParent().getId())
                :
                new CategoryDTO(category.getId(), category.getName(), category.isActive());
    }

}
