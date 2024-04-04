package com.example.project.service.impl;

import java.util.List;
import java.util.Locale.Category;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.CategoryEntity;
import com.example.project.entity.ProductEntity;
import com.example.project.exception.APIException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.payloads.CategoryDto;
import com.example.project.repository.ICategoryRepository;
import com.example.project.service.ICategoryService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CategoryService implements ICategoryService{

    @Autowired
    ICategoryRepository categoryRepo;

    @Autowired
	private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryEntity category) {
        // TODO Auto-generated method stub
        CategoryEntity savedCategory = categoryRepo.findByName(category.getName());

		if (savedCategory != null) {
			throw new APIException("Category with the name '" + category.getName() + "' already exists !!!");
		}

		savedCategory = categoryRepo.save(category);

		return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        // TODO Auto-generated method stub
        List<CategoryEntity> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDTOs = categories.stream()
				.map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		if (categories.size() == 0) {
			throw new APIException("No category is created till now");
		}

        return categoryDTOs;

    }

    @Override
    public CategoryDto updateCategory(CategoryEntity category, Long categoryId) {
        CategoryEntity savedCategory = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		category.setId(categoryId);

		savedCategory = categoryRepo.save(category);

		return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        CategoryEntity category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		List<ProductEntity> products = category.getProducts();

		products.forEach(product -> {
			productService.deleteProduct(product.getId());
		});
		
		categoryRepo.delete(category);

		return "Category with categoryId: " + categoryId + " deleted successfully !!!";
    }
    
}
