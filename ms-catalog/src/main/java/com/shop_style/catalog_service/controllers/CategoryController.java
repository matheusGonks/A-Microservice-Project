package com.shop_style.catalog_service.controllers;

import com.shop_style.catalog_service.dtos.category.CategoryDTO;
import com.shop_style.catalog_service.facade.GeneralFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private GeneralFacade generalFacade;

    public CategoryController(GeneralFacade generalFacade){
        this.generalFacade = generalFacade;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> retrieved = generalFacade.retrieveAllCategories();
        return ResponseEntity.ok(retrieved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        CategoryDTO retrieved = generalFacade.retrieveCategoryById(id);
        return ResponseEntity.ok(retrieved);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> postCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO retrieved = generalFacade.saveNewCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(retrieved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> putCategoryById(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        CategoryDTO retrieved = generalFacade.updateCategoryById(id, categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(retrieved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategoryById(@PathVariable Long id){
        CategoryDTO removed = generalFacade.removeCategoryById(id);
        return ResponseEntity.ok(removed);
    }

}
