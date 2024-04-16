package com.example.project.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingInfoDto extends BaseDto{

    private String firstName;
    private String lastName;
    private String tag;
    private String streetAddress;
    private String street;
    private String city;
    private String state;
    private String buildingName;
    private String mobie;
    private UserDto user;
}
