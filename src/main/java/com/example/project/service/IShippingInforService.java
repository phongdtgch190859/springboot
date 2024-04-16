package com.example.project.service;

import java.util.List;
import com.example.project.payloads.ShippingInfoDto;

public interface IShippingInforService {
    ShippingInfoDto createShippingInfo(ShippingInfoDto shipIfoDTO);
	
	List<ShippingInfoDto>  getShippingInfos();
	
	ShippingInfoDto getShipInfo(Long shipIfoId);
	
	ShippingInfoDto updateShipInfo(Long shipIfoId, ShippingInfoDto shipIfo);
	
	String deleteShipInfo(Long shipIfoId);
	
}
