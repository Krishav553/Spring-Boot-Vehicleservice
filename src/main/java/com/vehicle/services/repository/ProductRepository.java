package com.vehicle.services.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.services.entity.Vehicleproduct;

@Repository

public interface ProductRepository extends CrudRepository<Vehicleproduct, Integer> {
	
	Vehicleproduct findById(int id);
	
//	vehicle comcode
	Vehicleproduct findByVehiclecomcode(String vehiclecomcode);
	
	//Vehicle comid
	Vehicleproduct findByVehiclecomid(String vehiclecomid);
}
