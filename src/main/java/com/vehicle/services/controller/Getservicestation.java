package com.vehicle.services.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.vehicle.services.entity.Vehiclestationinfo;
import com.vehicle.services.helper.Result;
import com.vehicle.services.repository.VehicleRepository;
import com.vehicle.services.security.JwtUserDetailsService;

@RestController
public class Getservicestation {
	
	
	@Autowired
	VehicleRepository vehiclerepository;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	
	@PostMapping(value="/creatstation")
	public ResponseEntity<?>createStation(HttpServletRequest request, @RequestBody Vehiclestationinfo vehicleststioninfo){
		
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
        com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		
		try {
			if(vehicleststioninfo.name != null && !vehicleststioninfo.name.isEmpty() && vehicleststioninfo.email != null
					&& !vehicleststioninfo.email.isEmpty() && vehicleststioninfo.phonenumber != 0
					&& vehicleststioninfo.merchantId != null && !vehicleststioninfo.merchantId.isEmpty()
					&& vehicleststioninfo.city != null && ! vehicleststioninfo.city.isEmpty()
					&& vehicleststioninfo.address != null && !vehicleststioninfo.address.isEmpty() 
					&& vehicleststioninfo.radius !=0 && vehicleststioninfo.latitude != 0 && vehicleststioninfo.longitude !=0
					&& vehicleststioninfo.zipCode !=null && !vehicleststioninfo.zipCode.isEmpty()
					&& vehicleststioninfo.isOpen)
			{
				vehiclerepository.save(vehicleststioninfo);
				result.setStatusCode(200);
				result.setMessage("Station Sucessfully created");
//				return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
			}else
			{
//				return new ResponseEntity<>("Please field all the details",HttpStatus.ACCEPTED);
				result.setStatusCode(300);
				result.setMessage("Please field all the details");
			}
			
		}catch(Exception e)
		{
//			return new ResponseEntity<>("code error",HttpStatus.ACCEPTED);
			result.setStatusCode(350);
			result.setMessage("code error");
		}	
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping(value="/listofAllStation")
	public ResponseEntity<?>listofallStation(HttpServletRequest request){
		
		final String requestTokenHeader = request.getHeader("Authorization");
        com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		
		try {
			List<Vehiclestationinfo> list= (List<Vehiclestationinfo>) vehiclerepository.findAll();
			if(list.size() == 0)
			{
				return new ResponseEntity<>("No Service Station",HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
		}catch(Exception e)
		{
			return new ResponseEntity<>("Code error",HttpStatus.ACCEPTED);
		}	
	}
	
	@GetMapping(value="/listofsinglestation/{id}")
	public ResponseEntity<?>singleStationlist(HttpServletRequest request, @PathVariable int id){
		
		final String requestTokenHeader = request.getHeader("Authorization");
        com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		
		try {
			Vehiclestationinfo vehicelsingelstation = vehiclerepository.findById(id);
			if(vehicelsingelstation == null) {
				return new ResponseEntity<>("Station not found",HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(vehicelsingelstation,HttpStatus.ACCEPTED);
		}catch(Exception e)
		{
			return new ResponseEntity<>("Some error",HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping(value="/deletestation/{id}")
	public ResponseEntity<?>deleteStation(HttpServletRequest request,@PathVariable int id){
		
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
        com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		
		try {
			Vehiclestationinfo vehiclstationdelete = vehiclerepository.findById(id);
			vehiclerepository.delete(vehiclstationdelete);
			result.setStatusCode(200);
			result.setMessage("Vehicle Station sucessfully deleted");
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("Vehicle station not found");
		}
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping(value="/editstationsnfo/{id}")
	public ResponseEntity<?>editStationInfo(HttpServletRequest request,@RequestBody Vehiclestationinfo vehicleinfo, @PathVariable int id){
		
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
        com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		
		try {
			Vehiclestationinfo vehiclestationedit = vehiclerepository.findById(id);
			if(vehicleinfo.name != null && !vehicleinfo.name.isEmpty())
				vehiclestationedit.setName(vehicleinfo.name);
			if(vehicleinfo.address !=null && !vehicleinfo.address.isEmpty())
				vehiclestationedit.setAddress(vehicleinfo.address);
			if(vehicleinfo.phonenumber != 0)
				vehiclestationedit.setPhonenumber(vehicleinfo.phonenumber);
			if(vehicleinfo.city != null && !vehicleinfo.city.isEmpty())
				vehiclestationedit.setCity(vehicleinfo.city);
			vehiclerepository.save(vehiclestationedit);
			result.setStatusCode(200);
			result.setMessage("Station sucessfully updated");
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("Station not found");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
}
