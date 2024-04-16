package com.example.project.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.project.entity.ShippingInfoEntity;
import com.example.project.entity.UserEntity;
import com.example.project.exception.APIException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.payloads.ShippingInfoDto;
import com.example.project.repository.ShippingIfoPepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.IShippingInforService;
@Service
public class ShippingInforService implements IShippingInforService{
    @Autowired
	private ShippingIfoPepository shipIfoRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ShippingInfoDto createShippingInfo(ShippingInfoDto shipInfoDTO) {

		String state = shipInfoDTO.getState();
		String city = shipInfoDTO.getCity();
		String street = shipInfoDTO.getStreet();
		String buildingName = shipInfoDTO.getBuildingName();

		ShippingInfoEntity shipIfoFromDB = shipIfoRepo.findByStateAndCityAndStreetAndBuildingName(
				state, city, street, buildingName);

		if (shipIfoFromDB  != null) {
			throw new APIException("ShippingInfo already exists with ShippingInfoId: " +shipIfoFromDB.getId());
		}

		ShippingInfoEntity ShippingInfo = modelMapper.map(shipInfoDTO, ShippingInfoEntity.class);

		ShippingInfoEntity savedShippingInfo = shipIfoRepo.save(ShippingInfo);

		return modelMapper.map(savedShippingInfo,ShippingInfoDto.class);
	}

	@Override
	public List<ShippingInfoDto> getShippingInfos() {
		List<ShippingInfoEntity> shipIfos = shipIfoRepo.findAll();

		List<ShippingInfoDto> shipIfoDTOs = shipIfos.stream().map(ShippingInfo -> modelMapper.map(ShippingInfo, ShippingInfoDto.class))
				.collect(Collectors.toList());

		return shipIfoDTOs;
	}

	@Override
	public ShippingInfoDto getShipInfo(Long shipIfoId){
		ShippingInfoEntity shipInfo = shipIfoRepo.findById(shipIfoId)
				.orElseThrow(() -> new ResourceNotFoundException("Shipping Information", "Id", shipIfoId));

		return modelMapper.map(shipInfo, ShippingInfoDto.class);
	}

	

	@Override
	public ShippingInfoDto updateShipInfo(Long shipInfoId, ShippingInfoDto shipIfo) {
		ShippingInfoEntity siFromDB = shipIfoRepo.findByStateAndCityAndStreetAndBuildingName(
			shipIfo.getState(), shipIfo.getCity(), shipIfo.getStreet(),
			shipIfo.getBuildingName());

		if (siFromDB == null) {
			siFromDB = shipIfoRepo.findById(shipInfoId)
					.orElseThrow(() -> new ResourceNotFoundException("Shipping Information", "Id", shipInfoId));

		
			siFromDB.setState(shipIfo.getState());
			siFromDB.setCity(shipIfo.getCity());
			siFromDB.setStreet(shipIfo.getStreet());
			siFromDB.setBuildingName(shipIfo.getBuildingName());

			CrudRepository<ShippingInfoEntity, Long> shipInfoIdRepo;
            ShippingInfoEntity updatedSi = shipIfoRepo.save(siFromDB);

			return modelMapper.map(updatedSi, ShippingInfoDto.class);
		} else {
			Optional<UserEntity> user = userRepo.findById(shipInfoId);
			final ShippingInfoEntity si = siFromDB;

			user.get().getShippingInfos().add(si);

			deleteShipInfo(shipInfoId);

			return modelMapper.map(siFromDB, ShippingInfoDto.class);
		}
	}

	@Override
	public String deleteShipInfo(Long shipIfoId) {
		ShippingInfoEntity siFromDB = shipIfoRepo.findById(shipIfoId)
		.orElseThrow(() -> new ResourceNotFoundException("Shpping info", "Id", shipIfoId));

		Optional<UserEntity> user = userRepo.findById(siFromDB.getUser().getId());


		user.get().getShippingInfos().remove(siFromDB);

		shipIfoRepo.deleteById(shipIfoId);

		return "Shipping information deleted succesfully with ShippingInfoId: " + shipIfoId;
	}

	
}
