package com.vehicle.services.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.services.entity.Vechiclecustomerinfo;

@Repository
public interface CustomerRepository  extends CrudRepository<Vechiclecustomerinfo,Integer>{
	
	Vechiclecustomerinfo findById(int id);
	Vechiclecustomerinfo findByEmail(String email);
	Vechiclecustomerinfo findByPhonenumber(long phonenumber);
}
