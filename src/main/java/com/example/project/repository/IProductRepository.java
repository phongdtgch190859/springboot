package com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    public ProductEntity findByName(String name);

    public Optional<ProductEntity> findById(Long id);

    public ProductEntity findByCategory(String name);
}
