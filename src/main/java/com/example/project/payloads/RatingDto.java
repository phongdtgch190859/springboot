package com.example.project.payloads;

import com.example.project.entity.ProductEntity;
import com.example.project.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto extends BaseDto{
    private UserEntity user;
    private ProductEntity product;
    private double rating;
}
