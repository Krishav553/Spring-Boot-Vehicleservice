package com.vehicle.services.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.vehicle.services.entity.DAOUser;
import com.vehicle.services.entity.Vechiclecustomerinfo;
import com.vehicle.services.helper.PasswordHelper;
import com.vehicle.services.helper.Result;
import com.vehicle.services.repository.CustomerRepository;
import com.vehicle.services.repository.UserRepo;
import com.vehicle.services.security.JwtUserDetailsService;
import com.vehicle.services.twilio.SMSnotification;

@RestController

public class Getcustomer {
	
	@Autowired
	CustomerRepository customerrepository;
	
	@Autowired
	UserRepo userrepo;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	SMSnotification smsnotification;
	
	@PostMapping(value="/createcustomer")
	public ResponseEntity<?>createCustomer(HttpServletRequest request, @RequestBody Vechiclecustomerinfo customerinfo){
		
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
        com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser == null || daouser.getRole() != 1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			if(customerinfo.name != null && !customerinfo.name.isEmpty() && customerinfo.email != null
					&& !customerinfo.email.isEmpty() && customerinfo.phonenumber != 0
					&& customerinfo.city != null && ! customerinfo.city.isEmpty()
					&& customerinfo.address != null && !customerinfo.address.isEmpty() 
					&& customerinfo.radius !=0 && customerinfo.latitude != 0 && customerinfo.longitude !=0
					&& customerinfo.zipCode !=0)
			{
				customerrepository.save(customerinfo);
				result.setStatusCode(200);
				result.setMessage("Scustomer Sucessfully created");
			}else
			{
				result.setStatusCode(300);
				result.setMessage("Please field all the details");
			}
			
		}catch(Exception e)
		{
			result.setStatusCode(350);
			result.setMessage("code error");
		}	
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping(value= "/uploadexcelcustomer")
	public ResponseEntity<?>uploadExcelCustomer( HttpServletRequest request, @RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
		
		List <Result> resultlist = new ArrayList<Result>();
		 final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		
	    XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
	    XSSFSheet worksheet = workbook.getSheetAt(0);

	    for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	    	Result result = new Result();
	    	Vechiclecustomerinfo vechcustomer = new Vechiclecustomerinfo();

	        XSSFRow row = worksheet.getRow(i);
	        long phonenumber = (long) row.getCell(2).getNumericCellValue();
	        
	        Vechiclecustomerinfo vehicleexcelcustomer = customerrepository.findByPhonenumber(phonenumber);
	        if( vehicleexcelcustomer == null) {
	        	try {
	        		vechcustomer.setName(row.getCell(0).getStringCellValue()); 
	        		vechcustomer.setEmail(row.getCell(1).getStringCellValue());
	        		vechcustomer.setPhonenumber((long)row.getCell(2).getNumericCellValue());
	        		vechcustomer.setAddress(row.getCell(3).getStringCellValue());
	        		vechcustomer.setCity(row.getCell(4).getStringCellValue());
	        		vechcustomer.setZipCode((int) row.getCell(5).getNumericCellValue());
	        		vechcustomer.setRadius((int) row.getCell(6).getNumericCellValue());
	        		vechcustomer.setLatitude(row.getCell(7).getNumericCellValue());
	        		vechcustomer.setLongitude(row.getCell(8).getNumericCellValue());
			        customerrepository.save(vechcustomer);
			        result.setStatusCode(200);
			        result.setMessage("Sucessfully uploaded");
	        	}catch(Exception e)
	        	{
	        		result.setStatusCode(350);
		        	result.setMessage("Please fill all details");
	        	}
	        }else
	        {
	        	result.setStatusCode(300);
	        	result.setMessage("Customer allready exist");
	        }
	        resultlist.add(result);
	    }
	    return new ResponseEntity<>(resultlist,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/deletecustomer/{id}")
	public ResponseEntity<?>daleteCustomer(HttpServletRequest request,@PathVariable int id){
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			customerrepository.deleteById(id);
			result.setStatusCode(200);
			result.setMessage("Customer daleted sucessfully");
		}catch(Exception e)
		{
			result.setStatusCode(350);
			result.setMessage("Customer id not found");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping(value="/deleteallcustomer")
	public ResponseEntity<?>deleteallCustomer(HttpServletRequest request){
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			customerrepository.deleteAll();
			result.setStatusCode(200);
			result.setMessage("Customer daleted sucessfully");
		}catch(Exception e)
		{
			result.setStatusCode(350);
			result.setMessage("code Error");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping(value="/sendOTP/{phonenumber}")
	public ResponseEntity<?>sendOtp(HttpServletRequest request,@PathVariable long phonenumber) {
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			DAOUser user = userrepo.findByPhonenumber(phonenumber);
			if(user != null) {
				String otp = smsnotification.sendSMS(user);
				user.setOtp(otp);
				userrepo.save(user);
				result.setStatusCode(200);
				result.setMessage("OTP send to your registerd number");
			}
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("Phone number is not regestired");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/resendotp/{phonenumber}")
	public ResponseEntity<?>resendOtp(HttpServletRequest request,@PathVariable long phonenumber){
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			DAOUser user = userrepo.findByPhonenumber(phonenumber);
			if(user != null) {
				String otp = smsnotification.sendSMS(user);
				user.setOtp(otp);
				userrepo.save(user);
				result.setStatusCode(200);
				result.setMessage("OTP send to your registerd number");
			}
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("Phone number is not regestired");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping(value="/verifyotp/{phonenumber}/{otp}")
	public ResponseEntity<?>verifyOtp(HttpServletRequest request,@PathVariable String otp, @PathVariable long phonenumber){
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			DAOUser userotp = userrepo.findByOtp(otp);
			DAOUser phoneuser = userrepo.findByPhonenumber(phonenumber);
			if(userotp.getOtp().equals(otp) && phoneuser.getPhonenumber() == phonenumber)
			{
				userotp.setOtp(null);
				userrepo.save(userotp);
				result.setStatusCode(200);
				result.setMessage("Entered Otp is valid");
			}else {
				result.setStatusCode(300);
				result.setMessage("Entered Otp is NOT valid. Please Retry!");
			}
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("please Entered Otp");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/resetpassword/{phonenumber}/{password}")
	public ResponseEntity<?>resetPassword(HttpServletRequest request,@PathVariable long phonenumber, @PathVariable String password){
		Result result = new Result();
		final String requestTokenHeader = request.getHeader("Authorization");
		 com.vehicle.services.entity.DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser==null || daouser.getRole()!=1)
		 {
			 return new ResponseEntity<>("you are not authorized or your token code expired",HttpStatus.ACCEPTED);
		 }
		try {
			DAOUser phoneuser = userrepo.findByPhonenumber(phonenumber);
			if(phoneuser.getPhonenumber() == phonenumber)
			{
				phoneuser.setPassword(PasswordHelper.passwordEncode(phoneuser.getPassword()));
				userrepo.save(phoneuser);
				result.setStatusCode(200);
				result.setMessage("Password sucessfully changed");
			}
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("Entered phone number is not correct");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}

}

