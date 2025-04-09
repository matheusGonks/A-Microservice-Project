package com.shop_style.catalog_service.stub_builders;

import com.shop_style.catalog_service.dtos.CategoryDTO;
import com.shop_style.catalog_service.model.Category;

public class CategoryStubsBuilder {

    private long id = 1L;

    private String name = "Masculine";

    private boolean active = true;

    private Long parentId = 1L;

    public CategoryStubsBuilder(){}

    public Category getInstance(){
        return new Category(name, active);
    }

    public CategoryDTO getDto(){
        return new CategoryDTO(id, name, active, parentId);
    }

    public CategoryStubsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryStubsBuilder withActive(boolean active) {
        this.active = active;
        return this;
    }

    public CategoryStubsBuilder withParentId(Long id){
        this.parentId = id;
        return this;
    }

}
