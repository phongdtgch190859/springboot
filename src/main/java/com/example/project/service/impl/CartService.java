package com.example.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.CartEntity;
import com.example.project.entity.CartItemEntity;
import com.example.project.entity.ProductEntity;
import com.example.project.exception.APIException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.payloads.CartDto;
import com.example.project.payloads.ProductDto;
import com.example.project.repository.ICartItemRepository;
import com.example.project.repository.ICartRepository;
import com.example.project.repository.IProductRepository;
import com.example.project.service.ICartService;

@Service
public class CartService implements ICartService{
    @Autowired
	private ICartRepository cartRepo;

	@Autowired
	private IProductRepository productRepo;

	@Autowired
	private ICartItemRepository cartItemRepo;

	@Autowired
	private ModelMapper modelMapper;


    @Override
    public CartDto addProductToCart(Long cartId, Long productId, Integer quantity) {
        CartEntity cart = cartRepo.findById(cartId)
        .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItemEntity cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem != null) {
            throw new APIException("Product " + product.getName() + " already exists in the cart");
        }

        if (product.getStockQuantity() == 0) {
            throw new APIException(product.getName() + " is not available");
        }

        if (product.getStockQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getName()
                    + " less than or equal to the quantity " + product.getStockQuantity() + ".");
        }

        CartItemEntity newCartItem = new CartItemEntity();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepo.save(newCartItem);

        product.setStockQuantity(product.getStockQuantity() - quantity);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<ProductDto> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDto.class)).collect(Collectors.toList());

        cartDTO.setProducts(productDTOs);

        return cartDTO;
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<CartEntity> carts = cartRepo.findAll();

		if (carts.size() == 0) {
			throw new APIException("No cart exists");
		}
     

		List<CartDto> cartDTOs = carts.stream()
                .map(cartEntity -> modelMapper.map(cartEntity, CartDto.class))
                .collect(Collectors.toList());

		return cartDTOs;
    }

    @Override
    public CartDto getCart(String emailId, Long cartId) {
        CartEntity cart = cartRepo.findCartByEmailAndCartId(emailId, cartId);

		if (cart == null) {
			throw new ResourceNotFoundException("Cart", "cartId", cartId);
		}

		CartDto cartDTO = modelMapper.map(cart, CartDto.class);
		
		List<ProductDto> products = cart.getCartItems().stream()
				.map(p -> modelMapper.map(p.getProduct(), ProductDto.class)).collect(Collectors.toList());

		cartDTO.setProducts(products);

		return cartDTO;
    }

    @Override
    public CartDto updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
        CartEntity cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		ProductEntity product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		if (product.getStockQuantity() == 0) {
			throw new APIException(product.getName() + " is not available");
		}

		if (product.getStockQuantity() < quantity) {
			throw new APIException("Please, make an order of the " + product.getName()
					+ " less than or equal to the quantity " + product.getStockQuantity() + ".");
		}

		CartItemEntity cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new APIException("Product " + product.getName() + " not available in the cart!!!");
		}

		double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

		product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity() - quantity);

		cartItem.setProductPrice(product.getSpecialPrice());
		cartItem.setQuantity(quantity);
		cartItem.setDiscount(product.getDiscount());

		cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

		cartItem = cartItemRepo.save(cartItem);

		CartDto cartDTO = modelMapper.map(cart, CartDto.class);

		List<ProductDto> productDTOs = cart.getCartItems().stream()
				.map(p -> modelMapper.map(p.getProduct(), ProductDto.class)).collect(Collectors.toList());

		cartDTO.setProducts(productDTOs);

		return cartDTO;
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        CartEntity cart = cartRepo.findById(cartId)
        .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItemEntity cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemRepo.save(cartItem);
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        // TODO Auto-generated method stub
        CartEntity cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		CartItemEntity cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new ResourceNotFoundException("Product", "productId", productId);
		}

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

		ProductEntity product = cartItem.getProduct();
		product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());

		cartItemRepo.deleteCartItemByProductIdAndCartId(cartId, productId);

		return "Product " + cartItem.getProduct().getName() + " removed from the cart !!!";
    }
    
}
