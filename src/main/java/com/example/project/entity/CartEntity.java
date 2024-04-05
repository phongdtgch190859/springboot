package com.example.project.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity extends BaseEntity{
    @OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToMany(mappedBy = "cart", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	private List<CartItemEntity> cartItems = new ArrayList<>();

	private Double totalPrice = 0.0;
}
