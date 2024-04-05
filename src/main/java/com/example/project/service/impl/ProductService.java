package com.example.project.service.impl;

import java.io.IOException;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.entity.CartEntity;
import com.example.project.entity.CategoryEntity;
import com.example.project.entity.ProductEntity;
import com.example.project.exception.APIException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.payloads.CartDto;
import com.example.project.payloads.ProductDto;
import com.example.project.payloads.ProductResponse;
import com.example.project.repository.ICartRepository;
import com.example.project.repository.ICategoryRepository;
import com.example.project.repository.IProductRepository;
import com.example.project.service.ICartService;
import com.example.project.service.IFileService;
import com.example.project.service.IProductSevice;

@Service
public class ProductService implements IProductSevice{
    @Autowired
	private IProductRepository productRepo;

	@Autowired
	private ICategoryRepository categoryRepo;

	@Autowired
	private ICartRepository cartRepo;

	@Autowired
	private ICartService cartService;

	@Autowired
	private IFileService fileService;

	@Autowired
	private ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(Long categoryId, ProductEntity product) {
        CategoryEntity category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		boolean isProductNotPresent = true;

		List<ProductEntity> products = category.getProducts();

		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getName().equals(product.getName())
					&& products.get(i).getDescription().equals(product.getDescription())) {

				isProductNotPresent = false;
				break;
			}
		}

		if (isProductNotPresent) {

			product.setCategory(category);

			double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
			product.setSpecialPrice(specialPrice);

			ProductEntity savedProduct = productRepo.save(product);

			return modelMapper.map(savedProduct, ProductDto.class);
		} else {
			throw new APIException("Product already exists !!!");
		}
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

		Page<ProductEntity> pageProducts = productRepo.findAll(pageDetails);

		List<ProductEntity> products = pageProducts.getContent();

		List<ProductDto> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		ProductResponse productResponse = new ProductResponse();

		productResponse.setContent(productDTOs);
		productResponse.setPageNumber(pageProducts.getNumber());
		productResponse.setPageSize(pageProducts.getSize());
		productResponse.setTotalElements(pageProducts.getTotalElements());
		productResponse.setTotalPages(pageProducts.getTotalPages());
		productResponse.setLastPage(pageProducts.isLast());

		return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder) {

		CategoryEntity category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

		Page<ProductEntity> pageProducts = productRepo.findAll(pageDetails);

		List<ProductEntity> products = pageProducts.getContent();

		if (products.size() == 0) {
			throw new APIException(category.getName() + " category doesn't contain any products !!!");
		}

		List<ProductDto> productDTOs = products.stream().map(p -> modelMapper.map(p, ProductDto.class))
				.collect(Collectors.toList());

		ProductResponse productResponse = new ProductResponse();

		productResponse.setContent(productDTOs);
		productResponse.setPageNumber(pageProducts.getNumber());
		productResponse.setPageSize(pageProducts.getSize());
		productResponse.setTotalElements(pageProducts.getTotalElements());
		productResponse.setTotalPages(pageProducts.getTotalPages());
		productResponse.setLastPage(pageProducts.isLast());

		return productResponse;
	}


    @Override
    public ProductDto updateProduct(Long productId, ProductEntity product) {
        ProductEntity productFromDB = productRepo.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (productFromDB == null) {
            throw new APIException("Product not found with productId: " + productId);
        }

        product.setThumnail(productFromDB.getThumnail());
        product.setId(productId);
        product.setCategory(productFromDB.getCategory());
        product.setColor(productFromDB.getColor());
        product.setStatus(productFromDB.getStatus());
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        ProductEntity savedProduct = productRepo.save(product);

        List<CartEntity> carts = cartRepo.findCartsByProductId(productId);

        List<CartDto> cartDTOs = carts.stream().map(cart -> {
            CartDto cartDTO = modelMapper.map(cart, CartDto.class);

            List<ProductDto> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDto.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getId(), productId));

        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
        ProductEntity productFromDB = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		if (productFromDB == null) {
			throw new APIException("Product not found with productId: " + productId);
		}
		
		String fileName = fileService.uploadImage(image);
		
		productFromDB.setThumnail(fileName);
		
		ProductEntity updatedProduct = productRepo.save(productFromDB);
		
		return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        // TODO Auto-generated method stub
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

		Page<ProductEntity> pageProducts = productRepo.findByNameLike(keyword, pageDetails);

		List<ProductEntity> products = pageProducts.getContent();
		
		if (products.size() == 0) {
			throw new APIException("Products not found with keyword: " + keyword);
		}

		List<ProductDto> productDTOs = products.stream().map(p -> modelMapper.map(p, ProductDto.class))
				.collect(Collectors.toList());

		ProductResponse productResponse = new ProductResponse();

		productResponse.setContent(productDTOs);
		productResponse.setPageNumber(pageProducts.getNumber());
		productResponse.setPageSize(pageProducts.getSize());
		productResponse.setTotalElements(pageProducts.getTotalElements());
		productResponse.setTotalPages(pageProducts.getTotalPages());
		productResponse.setLastPage(pageProducts.isLast());

		return productResponse;
    }

    @Override
    public String deleteProduct(Long productId) {
        ProductEntity product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		List<CartEntity> carts = cartRepo.findCartsByProductId(productId);

		carts.forEach(cart -> cartService.deleteProductFromCart(cart.getId(), productId));

		productRepo.delete(product);

		return "Product with productId: " + productId + " deleted successfully !!!";
    }

    
    
}
