package com.vehicle.services.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import com.vehicle.services.entity.DAOUser;
import com.vehicle.services.helper.PasswordHelper;
import com.vehicle.services.helper.Result;
import com.vehicle.services.repository.UserRepo;
import com.vehicle.services.security.JwtTokenUtil;



@Service
public class DAOUserService {
	
	@Autowired
	private UserRepo DAOUserRepo;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public DAOUser checkDAOUser(String email)
	{
		
		try
		{
			DAOUser dAOUser=DAOUserRepo.findByEmail(email);
			return dAOUser;
		}
		catch (Exception e) {
		
			return null;
		}
		
	}
	
	
	
	public Result checklogin(String email,String password, boolean isPhonenumber, long phonenumber)
	{
		DAOUser user = null;
		Result result=new Result();
		try
		{
			if(isPhonenumber)
			 user = DAOUserRepo.findByPhonenumber(phonenumber);
			else
				user=DAOUserRepo.findByEmail(email);
			if(password.equals(PasswordHelper.passwordDecode(user.getPassword())))
			{
					final UserDetails userDetails =new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),new ArrayList<>());
                    String token=jwtTokenUtil.generateToken(userDetails);
					result.setStatusCode(200);
					result.setEmail(user.getEmail());
					result.setUsername(user.getName());
					result.setMessage("you logged-in successfully.");
					result.setJwstoken(token);
			}
			else
			{
				result.setStatusCode(400);
				result.setMessage("check your password.");

			}
		}
		catch (Exception e) {
			result.setStatusCode(400);
			result.setMessage("Please check your username.");
		}

		return result;
		
	
	}
	
	
	
	public Result checkadminlogin(String email,String password, boolean isPhonenumber, long phonenumber)
	{
		DAOUser user = null;
		Result result=new Result();
		try
		{
			if(isPhonenumber)
			 user = DAOUserRepo.findByPhonenumber(phonenumber);
			else
				user=DAOUserRepo.findByEmail(email);
			if(password.equals(PasswordHelper.passwordDecode(user.getPassword())) && user.getRole() == 1)
			{
					final UserDetails userDetails =new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),new ArrayList<>());
                    String token=jwtTokenUtil.generateToken(userDetails);
					result.setStatusCode(200);
					result.setEmail(user.getEmail());
					result.setUsername(user.getName());
					result.setMessage("you logged-in successfully.");
					result.setJwstoken(token);
			}
			else
			{
				result.setStatusCode(400);
				result.setMessage("check your password.");

			}
		}
		catch (Exception e) {
			result.setStatusCode(400);
			result.setMessage("Please check your username.");
		}

		return result;
		
	
	}
	
	
	

	
	
}
