package com.example.project.service;

import java.util.List;
import com.example.project.payloads.ShippingInfoDto;

public interface IShippingInforService {
    ShippingInfoDto createAddress(ShippingInfoDto shipIfoDTO);
	
	List<ShippingInfoDto> getAddresses();
	
	ShippingInfoDto getAddress(Long shipIfoId);
	
	ShippingInfoDto updateAddress(Long shipIfoId, ShippingInfoDto shipIfo);
	
	String deleteAddress(Long shipIfoId);
}
