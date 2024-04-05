package com.example.project.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.config.AppConstants;
import com.example.project.entity.ProductEntity;
import com.example.project.payloads.ProductDto;
import com.example.project.payloads.ProductResponse;
import com.example.project.service.impl.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class ProductController {
    @Autowired
	private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductEntity product, @PathVariable Long categoryId) {

		ProductDto savedProduct = productService.addProduct(categoryId, product);

		return new ResponseEntity<ProductDto>(savedProduct, HttpStatus.CREATED);
	}

	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

		ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.FOUND);
	}

    @GetMapping("/public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

		ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy,
				sortOrder);

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.FOUND);
	}
	
	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

		ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy,
				sortOrder);

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.FOUND);
	}

	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductEntity product,
			@PathVariable Long productId) {
		ProductDto updatedProduct = productService.updateProduct(productId, product);

		return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.OK);
	}
	
	@PutMapping("/admin/products/{productId}/image")
	public ResponseEntity<ProductDto> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
		ProductDto updatedProduct = null;
        try {
            updatedProduct = productService.updateProductImage(productId, image);
        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<String> deleteProductByCategory(@PathVariable Long productId) {
		String status = productService.deleteProduct(productId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

}
