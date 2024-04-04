package com.example.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wishlist")
@Getter
@Setter

public class WishlistEntity extends BaseEntity {

}
