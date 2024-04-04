package com.example.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Request.CreateProductVariantRequest;
import com.example.project.entity.ProductEntity;
import com.example.project.entity.ProductVariantEntity;
import com.example.project.exception.ProductException;
import com.example.project.repository.IProductRepository;
import com.example.project.repository.IProductVariantRepository;
import com.example.project.service.IProductVariantService;

@Service
public class ProductVariantService implements IProductVariantService {
    @Autowired
    private IProductVariantRepository productVariantRepo;
    @Autowired
    private IProductRepository productRepo;

    @Override
    public ProductVariantEntity createProductVariant(CreateProductVariantRequest req) {
        ProductEntity prod = productRepo.findByName(req.getProduct().getName());
        if (prod == null) {
            ProductEntity newProd = new ProductEntity();
            newProd.setName(req.getProduct().getName());
            prod = productRepo.save(newProd);
        }

        return null;
    }

    @Override
    public String deleteProductVariant(Long productVariantId) throws ProductException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProductVariant'");
    }

    @Override
    public ProductVariantEntity updateProductVariant(Long productVariantId, ProductVariantEntity pVariantEntity)
            throws ProductException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductVariant'");
    }

    @Override
    public ProductVariantEntity findProductVariant(Long productVariantId) throws ProductException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProductVariant'");
    }

    @Override
    public List<ProductVariantEntity> findProductVariantByProduct(String product) throws ProductException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProductVariantByProduct'");
    }

}
