package com.shop_style.catalog_service.exceptions;

import com.shop_style.catalog_service.model.Category;

public class CategoryNotFoundException extends EntityNotFoundException{

    public CategoryNotFoundException(long id){
        super(Category.class, id);
    }

}
