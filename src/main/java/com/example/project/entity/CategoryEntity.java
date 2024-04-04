package com.example.project.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catrgoties")
public class CategoryEntity extends BaseEntity {
    @Column
    private String name;
    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL )
	private List<ProductEntity> products;
}
