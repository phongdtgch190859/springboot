package com.example.project.payloads;

import java.time.LocalDate;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.example.project.entity.OrderEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto extends BaseDto{

    private String cardNumber;
    private String cardHolder;
    private LocalDate expirationDate;
    private String cvv;
	private OrderDto order;
}
