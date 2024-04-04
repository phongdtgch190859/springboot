package com.example.project.payloads;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseDto<CategoryDto>{
    private String name;
    private List<ProductDto> products;
}
