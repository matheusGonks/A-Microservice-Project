package com.shop_style.catalog_service.services;

import com.shop_style.catalog_service.exceptions.CategoryNotFoundException;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category retrieveCategoryById(long id){
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<Category> retrieveAllCategories(){
        return categoryRepository.findByParentIsNull();
    }

    public Category saveNewCategory(Category newCategory){
        return categoryRepository.save(newCategory);
    }

    public Category saveNewCategory(Category newCategory, long parentCategoryId){
        Category parentCategory = retrieveCategoryById(parentCategoryId);
        newCategory.setParent(parentCategory);
        return saveNewCategory(newCategory);
    }

    public Category removeCategory(long id){
        Category removed = retrieveCategoryById(id);
        categoryRepository.delete(removed);
        return removed;
    }

    public Category updateCategory(long id, Category updates){
        Category existing = retrieveCategoryById(id);
        existing = updateCategoryFields(existing, updates);
        return categoryRepository.save(existing);
    }

    private Category updateCategoryFields(Category existing, Category updates){
        if(activationHasChanged(existing, updates)){
            existing = retrieveCategoryWithChildrenFetched(existing.getId());
            updateActivationOfAllCategories(existing);
        }

        existing.setName(updates.getName());
        return existing;
    }

    private boolean activationHasChanged(Category existing, Category updates){
        return !updates.isActive() & existing.isActive();
    }

    private Category retrieveCategoryWithChildrenFetched(long id){
        return categoryRepository
                .findWithChildrenById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    private void updateActivationOfAllCategories(Category category){
        category.setActive(false);
        category.getChildrenCategories().forEach(this::updateActivationOfAllCategories);
    }

}
