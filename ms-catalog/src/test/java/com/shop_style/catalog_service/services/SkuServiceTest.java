package com.shop_style.catalog_service.services;

import com.shop_style.catalog_service.model.Sku;
import com.shop_style.catalog_service.repositories.SkuRepository;
import com.shop_style.catalog_service.stub_builders.SkuStubsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkuServiceTest {

    private final long STUB_ID = 1L;

    SkuStubsBuilder skuStubsBuilder;

    @InjectMocks
    SkuService skuService;

    @Mock
    SkuRepository skuRepository;

    @BeforeEach
    public void setUp(){
        skuStubsBuilder = new SkuStubsBuilder();
    }

    @Test
    @DisplayName("SkuService - successful creation of new Sku")
    public void skuServiceSavesNewSku(){
        Sku newSku = skuStubsBuilder.getInstance();
        when(skuRepository.save(any(Sku.class))).thenReturn(newSku);

        Sku savedSku = skuService.saveSku(newSku);

        assertEquals(newSku, savedSku, "Did not return correct category.");
        verify(skuRepository).save(newSku);
    }

    @Test
    @DisplayName("SkuService - successful update of Sku")
    public void skuServiceUpdatesNewSku(){
        Sku existing = skuStubsBuilder.getInstance();
        when(skuRepository.findById(STUB_ID)).thenReturn(Optional.of(existing));
        when(skuRepository.save(any(Sku.class))).thenReturn(existing);

        String newColor = "Blue";
        int newQuantity = 30;
        Sku updates = skuStubsBuilder.withQuantity(newQuantity).withColor(newColor).getInstance();

        Sku updatedSku = skuService.updateSku(STUB_ID, updates);

        assertEquals(newQuantity, updatedSku.getQuantity(), "Did not update Sku properly.");
        assertEquals(newColor, updatedSku.getColor(), "Did not update Sku properly.");
        verify(skuRepository).findById(STUB_ID);
    }

    @Test
    @DisplayName("SkuService - succesful removal of Sku")
    public void skuServiceRemovesSku(){
        Sku existing = skuStubsBuilder.getInstance();
        when(skuRepository.findById(STUB_ID)).thenReturn(Optional.of(existing));

        Sku deletedSku = skuService.removeSku(STUB_ID);
        assertEquals(deletedSku, existing);
        verify(skuRepository).delete(existing);
    }

}