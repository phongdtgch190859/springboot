package com.example.project.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class PaymentInfo {
    @Column
    private String cardNumber;
    @Column(name = "card_holder")
    private String cardHolder;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "cvv")
    private String cvv;
}
