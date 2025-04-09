package com.shop_style.catalog_service.services;

import com.shop_style.catalog_service.exceptions.SkuNotFoundException;
import com.shop_style.catalog_service.model.Sku;
import com.shop_style.catalog_service.repositories.SkuRepository;

public class SkuService {

    private final SkuRepository skuRepository;

    public SkuService(SkuRepository skuRepository){
        this.skuRepository = skuRepository;
    }

    public Sku saveSku(Sku sku){
        return skuRepository.save(sku);
    }

    public Sku updateSku(long id, Sku updates){
        Sku toBeUpdated = retrieveSku(id);
        updateSkuFields(toBeUpdated, updates);
        return saveSku(toBeUpdated);
    }

    public Sku removeSku(long id){
        Sku toBeRemoved = retrieveSku(id);
        skuRepository.delete(toBeRemoved);
        return toBeRemoved;
    }

    private Sku retrieveSku(long id){
        return skuRepository.findById(id).orElseThrow(() -> new SkuNotFoundException(id));
    }

    private void updateSkuFields(Sku toBeUpdated, Sku updates) {
        toBeUpdated.setPrice(updates.getPrice());
        toBeUpdated.setQuantity(updates.getQuantity());
        toBeUpdated.setColor(updates.getColor());
        toBeUpdated.setSize(updates.getSize());
        toBeUpdated.setHeight(updates.getHeight());
        toBeUpdated.setWidth(updates.getWidth());
        toBeUpdated.setProduct(updates.getProduct());
    }

}
