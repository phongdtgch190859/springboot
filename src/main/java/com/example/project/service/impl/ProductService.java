package com.example.project.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.entity.ProductEntity;
import com.example.project.payloads.ProductDto;
import com.example.project.service.IProductSevice;

@Service
public class ProductService implements IProductSevice{

    @Override
    public ProductDto addProduct(Long categoryId, ProductEntity product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public ProductDto getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

    @Override
    public ProductDto searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByCategory'");
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductEntity product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductImage'");
    }

    @Override
    public ProductDto searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchProductByKeyword'");
    }

    @Override
    public String deleteProduct(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }
    
}
