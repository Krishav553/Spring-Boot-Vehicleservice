package com.vehicle.services.helper;

public class Result {
	
	private int statusCode;
	private String message;
	public String email;
	public String username;
	public String jwstoken;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJwstoken() {
		return jwstoken;
	}
	public void setJwstoken(String jwstoken) {
		this.jwstoken = jwstoken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
