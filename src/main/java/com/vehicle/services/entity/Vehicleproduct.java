package com.vehicle.services.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_product")

public class Vehicleproduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	public String vehiclecomcode;
	@Column
	public String vehiclecomid;
	@Column
	public String vehiclegroup;
	@Column
	public String vehicleproductname;
	@Column
	public double vehicleproductprice;
	public String getVehiclecomcode() {
		return vehiclecomcode;
	}
	public void setVehiclecomcode(String vehiclecomcode) {
		this.vehiclecomcode = vehiclecomcode;
	}
	public String getVehiclecomid() {
		return vehiclecomid;
	}
	public void setVehiclecomid(String vehiclecomid) {
		this.vehiclecomid = vehiclecomid;
	}
	public String getVehiclegroup() {
		return vehiclegroup;
	}
	public void setVehiclegroup(String vehiclegroup) {
		this.vehiclegroup = vehiclegroup;
	}
	public String getVehicleproductname() {
		return vehicleproductname;
	}
	public void setVehicleproductname(String vehicleproductname) {
		this.vehicleproductname = vehicleproductname;
	}
	public double getVehicleproductprice() {
		return vehicleproductprice;
	}
	public void setVehicleproductprice(double vehicleproductprice) {
		this.vehicleproductprice = vehicleproductprice;
	}
	
	
}
