package com.bruno.projectMc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.City;
import com.bruno.projectMc.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepo;
	
	public List<City> findByState(Integer stateId) {
		return cityRepo.findCities(stateId);
	}
}
