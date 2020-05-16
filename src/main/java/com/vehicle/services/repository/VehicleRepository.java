package com.vehicle.services.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.services.entity.Vehiclestationinfo;

@Repository
public interface VehicleRepository extends CrudRepository<Vehiclestationinfo, Integer> {
	
	Vehiclestationinfo findById(int id);

}
