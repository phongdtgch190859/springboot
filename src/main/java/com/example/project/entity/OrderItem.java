package com.example.project.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrderItem extends BaseEntity{
    @ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order;
	
	private Integer quantity;
	private double discount;
	private double orderedProductPrice;
}
