package com.shop_style.catalog_service.exceptions;

import com.shop_style.catalog_service.model.Product;

public class ProductNotFoundException extends EntityNotFoundException{

    public ProductNotFoundException(long id){
        super(Product.class, id);
    }

}
