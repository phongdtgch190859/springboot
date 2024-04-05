package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.entity.CartItemEntity;
import com.example.project.entity.ProductEntity;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long>{
    @Query(value = "SELECT ci.product FROM cart_items ci WHERE ci.product.id = ?1", nativeQuery = true)
	ProductEntity findProductById(Long productId);
	
//	@Query("SELECT ci.cart FROM CartItem ci WHERE ci.product.id = ?1")
//	List<Cart> findCartsByProductId(Long productId);
	
	@Query(value = "SELECT ci FROM cart_items ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2", nativeQuery = true)
	CartItemEntity findCartItemByProductIdAndCartId(Long cartId, Long productId);
	
//	@Query("SELECT ci.cart FROM CartItem ci WHERE ci.cart.user.email = ?1 AND ci.cart.id = ?2")
//	Cart findCartByEmailAndCartId(String email, Integer cartId);
	
//	@Query("SELECT ci.order FROM CartItem ci WHERE ci.order.user.email = ?1 AND ci.order.id = ?2")
//	Order findOrderByEmailAndOrderId(String email, Integer orderId);
	
	@Modifying
    @Query(value = "DELETE FROM cart_items ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2", nativeQuery = true)
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}
