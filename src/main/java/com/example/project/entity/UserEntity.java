package com.example.project.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column
    private String password;
    @Column
    private String email;
    @Column
    private int role;
    @Column
    private String name;
    @Column
    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShippingInfoEntity> shippingInfos = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_infomation", joinColumns = @JoinColumn(name = "user_id"))
    private List<PaymentInfo> paymentInfos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RatingEntity> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviews = new ArrayList<>();
}
