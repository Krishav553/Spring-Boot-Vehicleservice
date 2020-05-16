package com.vehicle.services.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.services.entity.DAOUser;

@Repository
public interface RegestrationRepository extends CrudRepository<DAOUser,Integer> {
	
	DAOUser findById(int id);
	DAOUser findByEmailAndRole(String email, int role);
	DAOUser findByPhonenumber(long phonenumber);
}
