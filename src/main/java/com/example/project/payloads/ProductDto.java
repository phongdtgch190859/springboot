package com.example.project.payloads;

import com.example.project.entity.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends BaseDto<ProductDto>{
    
    private int stockQuantity;
    private double price;
    private double discount;
    private String color;
    private String thumnail;
    private String size;
    private int numRatings;
    private CategoryEntity category;
    private int status;

}
