package com.example.project.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentEntity extends BaseEntity{
    @Column
    private String cardNumber;
    @Column(name = "card_holder")
    private String cardHolder;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "cvv")
    private String cvv;

    @OneToOne(mappedBy = "payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private OrderEntity order;
}
