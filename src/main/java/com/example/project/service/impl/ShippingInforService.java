package com.example.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.project.entity.ShippingInfoEntity;
import com.example.project.entity.UserEntity;
import com.example.project.exception.APIException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.payloads.ShippingInfoDto;
import com.example.project.repository.ShippingIfoPepository;
import com.example.project.repository.UserRepository;
@Service
public class ShippingInforService {
    @Autowired
	private ShippingIfoPepository shipIfoRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ShippingInfoDto createAddress(ShippingInfoDto shipInfoDTO) {

		String state = shipInfoDTO.getState();
		String city = shipInfoDTO.getCity();
		String street = shipInfoDTO.getStreet();
		String buildingName = shipInfoDTO.getBuildingName();

		ShippingInfoEntity shipIfoFromDB = shipIfoRepo.findByStateAndCityAndStreetAndBuildingName(
				state, city, street, buildingName);

		if (shipIfoFromDB  != null) {
			throw new APIException("Address already exists with addressId: " +shipIfoFromDB.getId());
		}

		ShippingInfoEntity address = modelMapper.map(shipInfoDTO, ShippingInfoEntity.class);

		ShippingInfoEntity savedAddress = shipIfoRepo.save(address);

		return modelMapper.map(savedAddress,ShippingInfoDto.class);
	}

	@Override
	public List<ShippingInfoDto> getAddresses() {
		List<ShippingInfoEntity> shipIfos = shipIfoRepo.findAll();

		List<ShippingInfoDto> shipIfoDTOs = shipIfos.stream().map(address -> modelMapper.map(address, ShippingInfoDto.class))
				.collect(Collectors.toList());

		return shipIfoDTOs;
	}

	@Override
	public ShippingInfoDto getAddress(Long shipIfoId){
		ShippingInfoEntity shipInfo = shipIfoRepo.findById(shipIfoId)
				.orElseThrow(() -> new ResourceNotFoundException("Shipping Information", "Id", shipIfoId));

		return modelMapper.map(shipInfo, ShippingInfoDto.class);
	}

	@Override
	public ShippingInfoDto updateShipInfo(Long shipInfoId, ShippingInfoEntity si) {
		ShippingInfoEntity siFromDB = shipIfoRepo.findByStateAndCityAndStreetAndBuildingName(
				si.getState(), si.getCity(), si.getStreet(),
				si.getBuildingName());

		if (siFromDB == null) {
			siFromDB = shipIfoRepo.findById(shipInfoId)
					.orElseThrow(() -> new ResourceNotFoundException("Shipping Information", "Id", shipInfoId));

		
			siFromDB.setState(si.getState());
			siFromDB.setCity(si.getCity());
			siFromDB.setStreet(si.getStreet());
			siFromDB.setBuildingName(si.getBuildingName());

			CrudRepository<ShippingInfoEntity, Long> shipInfoIdRepo;
            ShippingInfoEntity updatedSi = shipInfoIdRepo.save(siFromDB);

			return modelMapper.map(updatedSi, ShippingInfoDto.class);
		} else {
			UserEntity user = userRepo.findById(shipInfoId);
			final ShippingInfoEntity a = siFromDB;

			user.getShippingInfos().add(a);

			deleteShipInfo(shipInfoId);

			return modelMapper.map(siFromDB, ShippingInfoDto.class);
		}
	}

	@Override
	public String deleteShipInfo(Long siId) {
		ShippingInfoEntity siFromDB = shipIfoRepo.findById(siId)
				.orElseThrow(() -> new ResourceNotFoundException("Shpping info", "Id", siId));

		UserEntity user = userRepo.findById(siFromDB.getUser().getId());

		
		user.getAddresses().remove(siFromDB);

		shipIfoRepo.deleteById(siId);

		return "Shipping information deleted succesfully with addressId: " + siId;
	}
}
