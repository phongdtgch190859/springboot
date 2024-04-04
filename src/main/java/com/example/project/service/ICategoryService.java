package com.example.project.service;



import java.util.List;

import com.example.project.entity.CategoryEntity;
import com.example.project.payloads.CategoryDto;

public interface ICategoryService {

    CategoryDto createCategory(CategoryEntity category);

	List<CategoryDto> getCategories();

	CategoryDto updateCategory(CategoryEntity category, Long categoryId);

	String deleteCategory(Long categoryId);
} 