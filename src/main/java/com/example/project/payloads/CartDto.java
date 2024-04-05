package com.example.project.payloads;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto extends BaseDto<CartDto>   {
    private Double totalPrice = 0.0;
	private List<ProductDto> products = new ArrayList<>();
}
