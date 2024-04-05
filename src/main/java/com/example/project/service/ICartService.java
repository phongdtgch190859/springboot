package com.example.project.service;

import java.util.List;


import com.example.project.payloads.CartDto;

public interface ICartService {
    CartDto addProductToCart(Long cartId, Long productId, Integer quantity);
	
	List<CartDto> getAllCarts();
	
	CartDto getCart(String emailId, Long cartId);
	
	CartDto updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);
	
	void updateProductInCarts(Long cartId, Long productId);
	
	String deleteProductFromCart(Long cartId, Long productId);
}
