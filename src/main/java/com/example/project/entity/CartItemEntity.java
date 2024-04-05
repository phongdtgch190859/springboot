package com.example.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity extends BaseEntity{
    @ManyToOne
	@JoinColumn(name = "cart_id")
	private CartEntity cart;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	private Integer quantity;
	private double discount;
	private double productPrice;
}
