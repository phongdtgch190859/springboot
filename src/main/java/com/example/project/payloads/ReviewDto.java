package com.example.project.payloads;

import com.example.project.entity.ProductEntity;
import com.example.project.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto extends BaseDto{
    private String content;
    private String images;
    private ProductEntity product;
    private UserEntity user;
}
