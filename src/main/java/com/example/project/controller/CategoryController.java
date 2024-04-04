package com.example.project.controller;

import java.util.List;
import java.util.Locale.Category;

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

import com.example.project.entity.CategoryEntity;
import com.example.project.payloads.CategoryDto;
import com.example.project.service.impl.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class CategoryController {
    @Autowired
	private CategoryService categoryService;

	@PostMapping("/admin/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryEntity category) {
		CategoryDto savedCategoryDTO = categoryService.createCategory(category);

		return new ResponseEntity<CategoryDto>(savedCategoryDTO, HttpStatus.CREATED);
	}

	@GetMapping("/public/categories")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		
		List<CategoryDto> categories = categoryService.getCategories();

		return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.FOUND);
	}

	@PutMapping("/admin/categories/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryEntity category,
			@PathVariable Long categoryId) {
		CategoryDto categoryDTO = categoryService.updateCategory(category, categoryId);

		return new ResponseEntity<CategoryDto>(categoryDTO, HttpStatus.OK);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		String status = categoryService.deleteCategory(categoryId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
