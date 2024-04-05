package com.example.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.CartEntity;


@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Long>{
    @Query(value = "SELECT * FROM carts c WHERE c.user_email = ?1 AND c.id = ?2", nativeQuery = true)
	CartEntity findCartByEmailAndCartId(String email, Long cartId);

	@Query(value = "SELECT c.id AS cart_id, p.id AS product_id FROM carts c " +
               "JOIN cart_items ci ON ci.cart_id = c.id " +
               "JOIN products p ON ci.product_id = p.id " +
               "WHERE p.id = ?1", nativeQuery = true)
	List<CartEntity> findCartsByProductId(Long productId);
}
