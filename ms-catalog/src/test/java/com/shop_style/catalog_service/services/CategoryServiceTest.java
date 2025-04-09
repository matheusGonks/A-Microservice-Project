package com.shop_style.catalog_service.services;

import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
import com.shop_style.catalog_service.exceptions.CategoryNotFoundException;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private final long STUB_ID = 1L;

    CategoryStubsBuilder categoryStubsBuilder;

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        categoryStubsBuilder = new CategoryStubsBuilder();
    }


    @Test
    @DisplayName("CategoryService - successful get category by id")
    public void categoryServiceReturnsCategory(){
        Category stubCategory = categoryStubsBuilder.getInstance();
        when(categoryRepository.findById(STUB_ID)).thenReturn(Optional.of(stubCategory));

        Category found = categoryService.retrieveCategoryById(STUB_ID);
        assertEquals(found, stubCategory);
    }

    @Test
    @DisplayName("CategoryService - unsuccessful get of category by Id.")
    public void categoryServiceThrowsException(){
        when(categoryRepository.findById(any())).thenThrow(new CategoryNotFoundException(STUB_ID));
        assertThrows(CategoryNotFoundException.class , () -> categoryService.retrieveCategoryById(STUB_ID));
        verify(categoryRepository, times(1)).findById(STUB_ID);
    }

    @Test
    @DisplayName("CategoryService - successful retrieval of all categories")
    public void categoryServiceRetrievesTreeOfCategories(){
        Category rootCategory = categoryStubsBuilder.withName("Clothes").getInstance();
        Category childCategory = categoryStubsBuilder.withName("Masculine").getInstance();
        rootCategory.setChildrenCategories(List.of(childCategory));
        when(categoryRepository.findByParentIsNull()).thenReturn(List.of(rootCategory));

        List<Category> categoriesList = categoryService.retrieveAllCategories();

        assertTrue(categoriesList.contains(rootCategory), "Did not return all categories");
        verify(categoryRepository).findByParentIsNull();
    }

    @Test
    @DisplayName("CategoryService - successful creation of new category.")
    public void categoryServiceCreatesCategorySuccessfully(){
        Category newCategory = categoryStubsBuilder.getInstance();
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category savedCategory = categoryService.saveNewCategory(newCategory);
        assertEquals(savedCategory, newCategory, "Did not return category properly");
        verify(categoryRepository).save(newCategory);
    }

    @Test
    @DisplayName("CategoryService - successful creation of new category with parent.")
    public void categoryServiceCreatesCategoryWithParentSuccessfully(){
        Category newCategory = categoryStubsBuilder.withName("Clothes").getInstance();
        Category parentCategory = categoryStubsBuilder.withName("Masculine").getInstance();

        when(categoryRepository.findById(STUB_ID)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category savedCategory = categoryService.saveNewCategory(newCategory, STUB_ID);

        assertEquals(newCategory, savedCategory, "Did not return category properly");
        assertEquals(parentCategory, savedCategory.getParent(), "Did not set parent category properly.");
        verify(categoryRepository).findById(STUB_ID);
        verify(categoryRepository).save(newCategory);
    }

    @Test
    @DisplayName("CategoryService - unsuccessful creation of new category with non-existent parent id.")
    public void categoryServiceCreatesCategoryWithParentUnsuccessfully(){
        Category newCategory = categoryStubsBuilder.getInstance();
        when(categoryRepository.findById(STUB_ID)).thenThrow(new CategoryNotFoundException(STUB_ID));

        assertThrows(CategoryNotFoundException.class, () -> categoryService.saveNewCategory(newCategory, STUB_ID), "Did not throw expected exception.");
        verify(categoryRepository).findById(STUB_ID);
        verify(categoryRepository, never()).save(newCategory);
    }

    @Test
    @DisplayName("CategoryService - successful removal of category with Id.")
    public void categoryServiceRemovesCategorySuccessfully(){
        Category toBeRemovedCategory = categoryStubsBuilder.getInstance();
        when(categoryRepository.findById(STUB_ID)).thenReturn(Optional.of(toBeRemovedCategory));

        Category removedCategory = categoryService.removeCategory(STUB_ID);

        assertEquals(toBeRemovedCategory, removedCategory, "Did not return deleted category.");
        verify(categoryRepository).findById(STUB_ID);
        verify(categoryRepository).delete(toBeRemovedCategory);
    }

    @Test
    @DisplayName("CategoryService - unsuccessful removal of category for non-existent Id.")
    public void categoryServiceDoesNotRemoveCategoryForNonExistentId(){
        when(categoryRepository.findById(STUB_ID)).thenThrow(new CategoryNotFoundException(STUB_ID));

        assertThrows(CategoryNotFoundException.class, () -> categoryService.removeCategory(STUB_ID), "");
        verify(categoryRepository).findById(STUB_ID);
        verify(categoryRepository, never()).delete(any());
    }

    @Test
    @DisplayName("CategoryService - successful update of category name by Id.")
    public void categoryServiceUpdatesNameSuccessfully(){
        Category existingCategory = categoryStubsBuilder.withName("Clothes").getInstance();
        Category updates = categoryStubsBuilder.withName("Masculine").getInstance();

        when(categoryRepository.findById(STUB_ID)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category updatedCategory = categoryService.updateCategory(STUB_ID, updates);

        assertEquals("Masculine", updatedCategory.getName(), "Category before update is equal category after.");
        verify(categoryRepository).findById(STUB_ID);
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    @DisplayName("CategoryService - successful update of category activation by Id.")
    public void categoryServiceUpdatesActiveStateSuccessfully(){

        //Prep data
        Category rootCategory = categoryStubsBuilder.withName("Clothes").getInstance();
        Category rootCategoryUpdates = categoryStubsBuilder.withActive(false).getInstance();
        rootCategory.setId(STUB_ID);

        Category child1 = categoryStubsBuilder.withName("Shirt").withActive(true).getInstance();
        Category child2 = categoryStubsBuilder.withName("Shoes").getInstance();
        Category grandChild1 = categoryStubsBuilder.withName("T-Shirt").getInstance();
        Category grandChild2 = categoryStubsBuilder.withName("Boots").getInstance();

        child1.setChildrenCategories(List.of(grandChild1));
        child2.setChildrenCategories(List.of(grandChild2));
        rootCategory.setChildrenCategories(List.of(child1, child2));

        when(categoryRepository.findById(STUB_ID)).thenReturn(Optional.of(rootCategory));
        when(categoryRepository.findWithChildrenById(STUB_ID)).thenReturn(Optional.of(rootCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocations -> invocations.getArgument(0));

        //exercise
        Category updatedCategory = categoryService.updateCategory(STUB_ID, rootCategoryUpdates);

        //verif
        assertFalse(updatedCategory.isActive(), "Category was not deactivated.");
        assertTrue(isTreeDeactivated(updatedCategory), "Tree of categories was not properly deactivated.");
        verify(categoryRepository).findWithChildrenById(STUB_ID);
        verify(categoryRepository).save(rootCategory);
    }

    private boolean isTreeDeactivated(Category category){
        if(category.isActive()) return false;
        return category.getChildrenCategories().stream().allMatch(this::isTreeDeactivated);
    }

}
