package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

}
