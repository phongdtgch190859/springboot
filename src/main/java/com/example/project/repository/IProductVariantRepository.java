package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project.entity.ProductVariantEntity;

@Repository
public interface IProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {

    @Query("SELECT pv from productVariants pv" +
            "WHERE (pv.product.name=:prouct OR :product='')")
    public List<ProductVariantEntity> filterProuctVariants(@Param("product") String product);
}
