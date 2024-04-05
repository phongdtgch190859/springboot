package com.example.project.service;



import com.example.project.entity.CategoryEntity;
import com.example.project.payloads.CategoryDto;
import com.example.project.payloads.CategoryResponse;

public interface ICategoryService {

    CategoryDto createCategory(CategoryEntity category);

	CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	CategoryDto updateCategory(CategoryEntity category, Long categoryId);

	String deleteCategory(Long categoryId);
} 