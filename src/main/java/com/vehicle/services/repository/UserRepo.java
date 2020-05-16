package com.vehicle.services.repository;

//import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.services.entity.DAOUser;


@Repository
public interface UserRepo extends CrudRepository<DAOUser, Integer>{

	DAOUser findById(int id);
	DAOUser findByEmail(String email);
	DAOUser findByPhonenumber(long phonenumber);
	DAOUser findByOtp(String otp);
}