package com.example.project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    public ProductEntity findByName(String name);

    Page<ProductEntity> findByNameLike(String keyword, Pageable pageDetails);

  
}
