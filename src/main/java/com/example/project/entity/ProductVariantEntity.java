package com.example.project.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productVariants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantEntity extends BaseEntity {
    @Column
    private int stockQuantity;
    @Column
    private double price;
    @Column
    private double discount;
    @Column
    private String color;
    @Column
    private String thumnail;
    @Embedded
    @ElementCollection
    @Column
    private Set<SizeEntity> sizes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
