package com.vehicle.services.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.services.helper.Result;
import com.vehicle.services.security.JwtUserDetailsService;
import com.vehicle.services.service.DAOUserService;

@RestController

public class UserController { 
	
	@Autowired
	DAOUserService dAOUserService;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@PostMapping(value="/validiateuser/{email}/{password}")
	public  ResponseEntity<?> validiateuser(@PathVariable String email,@PathVariable String password)
	{
		Result result = null;
		try {
			long phonenumber = Long.valueOf(email);
			 result=dAOUserService.checklogin(null, password, true, phonenumber);
		}catch(Exception e)
		{
			result=dAOUserService.checklogin(email, password, false, 0);
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value="/validiatadmineuser/{email}/{password}")
	public  ResponseEntity<?> validiatadmineuser(@PathVariable String email,@PathVariable String password)
	{
		Result result = null;
		try {
			long phonenumber = Long.valueOf(email);
			 result=dAOUserService.checkadminlogin(null, password, true, phonenumber);
		}catch(Exception e)
		{
			result=dAOUserService.checkadminlogin(email, password, false, 0);
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/checkAuth")
	public ResponseEntity<?>checkAuth(HttpServletRequest request)
	{
		 final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser!=null)
		 {
			 Result result=new Result();
			 if(daouser.role != 0 ||  daouser.role!= 1 )
				 result.setStatusCode(150);
			 else
				 result.setStatusCode(200);
				result.setUsername(daouser.getName());
				return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
		 }
				
		 else
			    return new ResponseEntity<>("Please Login Again",HttpStatus.UNAUTHORIZED);
	}
}