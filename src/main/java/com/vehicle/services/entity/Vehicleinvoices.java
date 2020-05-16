package com.vehicle.services.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_invoices")

public class Vehicleinvoices {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	public int vehicleproductid;
	@Column
	public int vehiclecustomerid;
	@Column 
	public double invoice_total;
	@Column
	public double payment_total;
	@Column
	public Date invoice_date;
	@Column
	public Date due_date;
	@Column
	public int payment_date;
	public int getVehicleproductid() {
		return vehicleproductid;
	}
	public void setVehicleproductid(int vehicleproductid) {
		this.vehicleproductid = vehicleproductid;
	}
	public int getVehiclecustomerid() {
		return vehiclecustomerid;
	}
	public void setVehiclecustomerid(int vehiclecustomerid) {
		this.vehiclecustomerid = vehiclecustomerid;
	}
	public double getInvoice_total() {
		return invoice_total;
	}
	public void setInvoice_total(double invoice_total) {
		this.invoice_total = invoice_total;
	}
	public double getPayment_total() {
		return payment_total;
	}
	public void setPayment_total(double payment_total) {
		this.payment_total = payment_total;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public int getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(int payment_date) {
		this.payment_date = payment_date;
	}
	
	

}
