package com.shop_style.catalog_service.controllers;

import com.shop_style.catalog_service.dtos.sku.SkuDto;
import com.shop_style.catalog_service.facade.GeneralFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/skus")
public class SkuController {

    private GeneralFacade generalFacade;

    public SkuController(GeneralFacade generalFacade){
        this.generalFacade = generalFacade;
    }

    @PostMapping
    public ResponseEntity<SkuDto> postSku(@RequestBody SkuDto skuDto){
        SkuDto created = generalFacade.saveNewSku(skuDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkuDto> putSkuById(@PathVariable Long id, @RequestBody SkuDto skuDto){
        SkuDto updatedSku = generalFacade.updateSkuById(id, skuDto);
        return ResponseEntity.ok(updatedSku);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SkuDto> deleteSkuById(@PathVariable Long id){
        SkuDto removedSku = generalFacade.removeSkuById(id);
        return ResponseEntity.ok(removedSku);
    }

}
