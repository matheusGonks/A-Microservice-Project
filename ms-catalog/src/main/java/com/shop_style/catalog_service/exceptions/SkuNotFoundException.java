package com.shop_style.catalog_service.exceptions;

import com.shop_style.catalog_service.model.Sku;

public class SkuNotFoundException extends EntityNotFoundException{

    public SkuNotFoundException(long id){
        super(Sku.class, id);
    }

}
