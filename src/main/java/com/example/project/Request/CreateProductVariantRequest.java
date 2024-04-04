package com.example.project.Request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.project.entity.ProductEntity;
import com.example.project.entity.SizeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductVariantRequest {

    private int stockQuantity;
    private double price;
    private double discount;
    private String color;
    private String thumnail;
    private Set<SizeEntity> sizes = new HashSet<>();
    private ProductEntity product;
}
