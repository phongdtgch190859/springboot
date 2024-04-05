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
public class UserDto extends BaseDto{
    private String password;
    private String email;
    private int role;
    private String name;
    private String avatar;
    private List<ShippingInfoDto> shippingInfos = new ArrayList<>();
    private List<PaymentDto> paymentInfos = new ArrayList<>();
    private List<RatingDto> ratings = new ArrayList<>();
    private List<ReviewDto> reviews = new ArrayList<>();
	private CartDto cart;
}
