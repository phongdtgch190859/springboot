package com.example.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.ShippingInfoEntity;


@Repository
public interface ShippingIfoPepository extends JpaRepository<ShippingInfoEntity, Long> {

    public ShippingInfoEntity findByStateAndCityAndStreetAndBuildingName(String state,
            String city, String street, String buildingName); 


}
