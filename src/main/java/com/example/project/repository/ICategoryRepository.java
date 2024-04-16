package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.CategoryEntity;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long>{
    CategoryEntity findByName(String categoryName);
    CategoryEntity findByCategoryLevel(int level);
}
