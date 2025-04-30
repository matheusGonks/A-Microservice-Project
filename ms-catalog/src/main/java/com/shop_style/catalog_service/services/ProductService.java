package com.shop_style.catalog_service.services;

import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.repositories.ProductRepository;

import java.util.List;

public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> retrieveAllProducts(){
        return productRepository.findAll();
    }

    public Product retrieveById(long id){
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Product removeProduct(long id){
        Product removedProduct = retrieveById(id);
        productRepository.delete(removedProduct);
        return removedProduct;
    }

    public Product updateProduct(Long id, Product updates){
        Product existingProduct = retrieveById(id);
        updateExistingProduct(existingProduct, updates);
        return saveProduct(existingProduct);
    }

    private void updateExistingProduct(Product existing, Product updates){
        existing.setName(updates.getName());
        existing.setBrand(updates.getBrand());
        existing.setDescription(updates.getDescription());
        existing.setMaterial(updates.getMaterial());
        existing.setActive(updates.isActive());
        existing.setCategory(updates.getCategory());
    }
}
