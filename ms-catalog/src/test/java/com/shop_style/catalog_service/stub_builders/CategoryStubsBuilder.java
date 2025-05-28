package com.shop_style.catalog_service.stub_builders;

import com.shop_style.catalog_service.dtos.category.CategoryDTO;
import com.shop_style.catalog_service.model.Category;

public class CategoryStubsBuilder {

    private Long id = 1L;

    private String name = "Masculine";

    private boolean active = true;

    private Long parentId = 1L;

    public CategoryStubsBuilder(){}

    public Category getInstance(){
        Category c = new Category(name, active);
        c.setId(id);
        return c;
    }

    public CategoryDTO getDto(){
        return new CategoryDTO(id, name, active, parentId);
    }

    // Instance Fields  ==================================
    // Instance Objects and lists will be generally initialized outside Builder;
    // Like Parent Category and List of children categories.

    public CategoryStubsBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public CategoryStubsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryStubsBuilder withActive(boolean active) {
        this.active = active;
        return this;
    }

    // Dto fields ==================================

    public CategoryStubsBuilder withParentId(Long id){
        this.parentId = id;
        return this;
    }

}
