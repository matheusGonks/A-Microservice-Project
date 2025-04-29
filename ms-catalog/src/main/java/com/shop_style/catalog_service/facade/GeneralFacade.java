package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.services.CategoryService;

import java.util.List;

public class GeneralFacade {

    private final CategoryService categoryService;

    private final DtoConverter dtoConverter = new DtoConverter();

    public GeneralFacade(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    public List<CategoryDTO> retrieveAllCategories(){

        return categoryService
                .retrieveAllCategories()
                .stream()
                .map(dtoConverter::makeDtoFromCategory)
                .toList();
    }

    public CategoryDTO retrieveCategoryById(Long id){
        Category retrieved = categoryService.retrieveCategoryById(id);
        return dtoConverter.makeDtoFromCategory(retrieved);
    }

    public CategoryDTO saveNewCategory(CategoryDTO categoryDTO){
        Category newCategory = dtoConverter.makeCategoryFromDto(categoryDTO);

        Category saved = (newCategory.hasParent())?
                categoryService.saveNewCategory(newCategory, categoryDTO.getParentId())
                :
                categoryService.saveNewCategory(newCategory);

        return dtoConverter.makeDtoFromCategory(saved);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO updatesDto){
        Category updates = dtoConverter.makeCategoryFromDto(updatesDto);
        Category updatedCategory = categoryService.updateCategory(id, updates);
        return dtoConverter.makeDtoFromCategory(updatedCategory);
    }

    public CategoryDTO removeCategory(Long id){
        Category removedCategory = categoryService.removeCategory(id);
        return dtoConverter.makeDtoFromCategory(removedCategory);
    }

}
