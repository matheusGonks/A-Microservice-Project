package com.shop_style.catalog_service.controllers;

import com.shop_style.catalog_service.dtos.product.ProductDto;
import com.shop_style.catalog_service.facade.GeneralFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private GeneralFacade generalFacade;

    public ProductController(GeneralFacade generalFacade){
        this.generalFacade = generalFacade;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> retrieved = generalFacade.retrieveAllProducts();
        return ResponseEntity.ok(retrieved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getAllProducts(@PathVariable Long id){
        ProductDto retrieved = generalFacade.retrieveProductById(id);
        return ResponseEntity.ok(retrieved);
    }

    @PostMapping
    public ResponseEntity<ProductDto> postProduct(@RequestBody ProductDto productDto){
        ProductDto created = generalFacade.saveNewProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> putProductById(@PathVariable Long id, @RequestBody ProductDto productDto){
        ProductDto updated = generalFacade.updateProductById(id, productDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProductById(@PathVariable Long id){
        ProductDto deleted = generalFacade.removeProductById(id);
        return ResponseEntity.ok(deleted);
    }

}
