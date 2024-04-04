package com.example.project.service;

import java.util.List;

import com.example.project.Request.CreateProductVariantRequest;
import com.example.project.entity.ProductVariantEntity;
import com.example.project.exception.ProductException;

public interface IProductVariantService {
    public ProductVariantEntity createProductVariant(CreateProductVariantRequest req);

    public String deleteProductVariant(Long productVariantId) throws ProductException;

    public ProductVariantEntity updateProductVariant(Long productVariantId, ProductVariantEntity pVariantEntity)
            throws ProductException;

    public ProductVariantEntity findProductVariant(Long productVariantId) throws ProductException;

    public List<ProductVariantEntity> findProductVariantByProduct(String product) throws ProductException;
}
