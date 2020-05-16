package com.vehicle.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.services.entity.DAOUser;
import com.vehicle.services.helper.PasswordHelper;
import com.vehicle.services.helper.Result;
import com.vehicle.services.repository.RegestrationRepository;

@RestController
public class Getregestration {
	
	@Autowired
	RegestrationRepository regestrationrepository;
	
	
	@PostMapping(value="/customerregestration")
	public ResponseEntity<?>customerRegestration(@RequestBody DAOUser daouser){
	
		Result result = new Result();
		
	try {
		DAOUser customeremail = regestrationrepository.findByEmailAndRole(daouser.getEmail(),0);
		DAOUser customerphone = regestrationrepository.findByPhonenumber(daouser.getPhonenumber());
		if(customeremail != null) {
			result.setStatusCode(250);
			result.setMessage("email allready exists");
		}
		else if(customerphone != null) {
			result.setStatusCode(250);
			result.setMessage("phone number allready exists");
		}
		else if(daouser.password != null && daouser.email != null && !daouser.email.isEmpty()
					&& daouser.name != null && !daouser.name.isEmpty() && daouser.phonenumber != 0)
		{
			daouser.setRole(0);

			daouser.setPassword(PasswordHelper.passwordEncode(daouser.getPassword()));
			regestrationrepository.save(daouser);
			result.setStatusCode(200);
			result.setMessage("Sucessfully registered");
		}
			else 
			{
				result.setStatusCode(300);
				result.setMessage("Please filled all");
			}
		}catch(Exception e)
		{
			result.setStatusCode(350);
			result.setMessage("Code error");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	

	@PostMapping(value="/adminregestration")
	public ResponseEntity<?>adminRegestration(@RequestBody DAOUser daouser){
		
		Result result = new Result();
		
	try {
		DAOUser customeremail = regestrationrepository.findByEmailAndRole(daouser.getEmail(),1);
		DAOUser customerphone = regestrationrepository.findByPhonenumber(daouser.getPhonenumber());
		if(customeremail != null) {
			result.setStatusCode(250);
			result.setMessage("email allready exists");
		}
		else if(customerphone != null) {
			result.setStatusCode(250);
			result.setMessage("phone number allready exists");
		}
		else if(daouser.password != null && daouser.email != null && !daouser.email.isEmpty()
					&& daouser.name != null && !daouser.name.isEmpty() && daouser.phonenumber != 0)
		{
			daouser.setRole(1);
			daouser.setPassword(PasswordHelper.passwordEncode(daouser.getPassword()));
			regestrationrepository.save(daouser);
			result.setStatusCode(200);
			result.setMessage("Sucessfully registered");
		}
			else 
			{
				result.setStatusCode(300);
				result.setMessage("Please filled all");
			}
		}catch(Exception e)
		{
			result.setStatusCode(350);
			result.setMessage("Code error");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
}
