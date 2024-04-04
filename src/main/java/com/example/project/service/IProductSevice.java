package com.example.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.entity.ProductEntity;
import com.example.project.payloads.ProductDto;

public interface IProductSevice {
    ProductDto addProduct(Long categoryId, ProductEntity product);

	ProductDto getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductDto searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder);

	ProductDto updateProduct(Long productId, ProductEntity product);

	ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;

	ProductDto searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder);

	String deleteProduct(Long productId);
}
