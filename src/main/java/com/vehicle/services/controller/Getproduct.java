package com.vehicle.services.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


import com.vehicle.services.entity.Vehicleproduct;
import com.vehicle.services.helper.Result;
import com.vehicle.services.repository.ProductRepository;

@RestController

public class Getproduct {
	
	@Autowired
	ProductRepository productrepository;
	
	@PostMapping(value="/createsingleproduct")
	public ResponseEntity<?>createSingelProduct(@RequestBody Vehicleproduct vehicleproduct){
		
		Result result = new Result();
		
		try {
			if(vehicleproduct.vehicleproductname != null && !vehicleproduct.vehicleproductname.isEmpty()
					&& vehicleproduct.vehicleproductprice != 0)
			{
				productrepository.save(vehicleproduct);
				result.setStatusCode(200);
				result.setMessage("vehicle product sucessfully uploaded");
			}
		}catch(Exception e) {
			result.setStatusCode(350);
			result.setMessage("please field all details");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value= "/uploadexcelproduct")
	public ResponseEntity<?>uploadExcelProduct(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
		List <Result> resultlist = new ArrayList<Result>();
		
	    XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
	    XSSFSheet worksheet = workbook.getSheetAt(0);

	    for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	    	Result result = new Result();
	    	Vehicleproduct vehicleexcelproduct = new Vehicleproduct();

	        XSSFRow row = worksheet.getRow(i);
	        
	        Vehicleproduct vehicleexcelproductcom = productrepository.findByVehiclecomcode(row.getCell(0).getStringCellValue());
	        Vehicleproduct vehiclecomid = productrepository.findByVehiclecomid(row.getCell(1).getStringCellValue());
	        if( vehicleexcelproductcom== null &&  vehiclecomid == null ) {
	        	try {
	        		vehicleexcelproduct.setVehiclecomcode(row.getCell(0).getStringCellValue()); 
	        		vehicleexcelproduct.setVehiclecomid(row.getCell(1).getStringCellValue());
	        		vehicleexcelproduct.setVehiclegroup(row.getCell(2).getStringCellValue());
			        vehicleexcelproduct.setVehicleproductname(row.getCell(3).getStringCellValue());
			        vehicleexcelproduct.setVehicleproductprice(row.getCell(4).getNumericCellValue());
			        productrepository.save(vehicleexcelproduct);
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
	        	result.setMessage("Company code or company id must be different");
	        }
	        resultlist.add(result);
	    }
	    return new ResponseEntity<>(resultlist,HttpStatus.ACCEPTED);
	}

	
	@GetMapping(value= "/deleteproduct/{id}")
	public ResponseEntity<?>deleteProduct(@PathVariable int id){
		
		Result result = new Result();
		
		try {
			Vehicleproduct vehicleprodudelete = productrepository.findById(id);
			productrepository.delete(vehicleprodudelete);
			result.setStatusCode(200);
			result.setMessage("product deleted sucessfully");
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("product not found");
		}
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/deleteallproduct")
	public ResponseEntity<?>deleteallProduct(){
		
		Result result = new Result();
		try {
			productrepository.deleteAll();
			result.setStatusCode(200);
			result.setMessage("All product deleted sucessfully");
		}catch(Exception e)
		{
			result.setStatusCode(300);
			result.setMessage("product not found");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}
}
