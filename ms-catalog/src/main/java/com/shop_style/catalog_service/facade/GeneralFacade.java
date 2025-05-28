package com.shop_style.catalog_service.facade;

import com.shop_style.catalog_service.dtos.category.CategoryDTO;
import com.shop_style.catalog_service.dtos.product.ProductDto;
import com.shop_style.catalog_service.dtos.sku.SkuDto;
import com.shop_style.catalog_service.model.Category;
import com.shop_style.catalog_service.model.Product;
import com.shop_style.catalog_service.model.Sku;
import com.shop_style.catalog_service.services.CategoryService;
import com.shop_style.catalog_service.services.ProductService;
import com.shop_style.catalog_service.services.SkuService;

import java.util.List;

public class GeneralFacade {

    private final CategoryService categoryService;

    private final ProductService productService;

    private final DtoConverter dtoConverter;

    private final SkuService skuService;

    public GeneralFacade(CategoryService categoryService, ProductService productService, SkuService skuService, DtoConverter dtoConverter){
        this.categoryService = categoryService;
        this.productService = productService;
        this.skuService = skuService;
        this.dtoConverter = dtoConverter;
    }

    public List<CategoryDTO> retrieveAllCategories(){

        return categoryService
                .retrieveAllCategories()
                .stream()
                .map(dtoConverter::makeTreeOfDtosFromCategory)
                .toList();
    }

    public CategoryDTO retrieveCategoryById(Long id){
        Category retrieved = categoryService.retrieveCategoryById(id);
        return dtoConverter.makeDtoFromCategory(retrieved);
    }

    public CategoryDTO saveNewCategory(CategoryDTO categoryDTO){
        Category newCategory = dtoConverter.makeCategoryFromDto(categoryDTO);

        Category saved = (newCategory.hasParent())?
                categoryService.saveNewCategory(newCategory, categoryDTO.getParentId())
                :
                categoryService.saveNewCategory(newCategory);

        return dtoConverter.makeDtoFromCategory(saved);
    }

    public CategoryDTO updateCategoryById(Long id, CategoryDTO updatesDto){
        Category updates = dtoConverter.makeCategoryFromDto(updatesDto);
        Category updatedCategory = categoryService.updateCategory(id, updates);
        return dtoConverter.makeDtoFromCategory(updatedCategory);
    }

    public CategoryDTO removeCategoryById(Long id){
        Category removedCategory = categoryService.removeCategory(id);
        return dtoConverter.makeDtoFromCategory(removedCategory);
    }

    public List<ProductDto> retrieveAllProducts(){
        return productService
                .retrieveAllProducts()
                .stream()
                .map(dtoConverter::makeDtoFromProduct)
                .toList();
    }

    public ProductDto retrieveProductById(Long id){
        Product retrieved = productService.retrieveById(id);
        return dtoConverter.makeDtoFromProduct(retrieved);
    }

    public ProductDto saveNewProduct(ProductDto productDto){
        Product product = dtoConverter.makeProductFromDto(productDto);
        Category productOwningCategory = categoryService.retrieveCategoryById(productDto.getCategoryId());
        product.setCategory(productOwningCategory);

        Product productReturnedFromCreation = productService.saveProduct(product);
        return dtoConverter.makeDtoFromProduct(productReturnedFromCreation);
    }

    public ProductDto updateProductById(Long id, ProductDto updatesDto){
        Product updatesInstance = dtoConverter.makeProductFromDto(updatesDto);
        Category productOwningCategory = categoryService.retrieveCategoryById(updatesDto.getCategoryId());
        updatesInstance.setCategory(productOwningCategory);

        Product productReturnedFromUpdate = productService.updateProduct(id, updatesInstance);
        return dtoConverter.makeDtoFromProduct(productReturnedFromUpdate);
    }

    public ProductDto removeProductById(Long id){
        Product removedProduct = productService.removeProduct(id);
        return dtoConverter.makeDtoFromProduct(removedProduct);
    }

    public SkuDto saveNewSku(SkuDto skuDto){
        Product owningProduct = productService.retrieveById(skuDto.getProductId());
        Sku sku = dtoConverter.makeSkuFromDto(skuDto);
        sku.setProduct(owningProduct);

        Sku savedSku = skuService.saveSku(sku);
        return dtoConverter.makeDtoFromSku(savedSku);
    }

    public SkuDto updateSkuById(Long id, SkuDto skuDto){
        Product owningProduct = productService.retrieveById(skuDto.getProductId());
        Sku sku = dtoConverter.makeSkuFromDto(skuDto);
        sku.setProduct(owningProduct);

        Sku savedSku = skuService.updateSku(id,sku);
        return dtoConverter.makeDtoFromSku(savedSku);
    }

    public SkuDto removeSkuById(Long id){
        Sku removedSku = skuService.removeSku(id);
        return dtoConverter.makeDtoFromSku(removedSku);
    }

}
