package com.SL.SportyShoes.Entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int uID;
	private String name;
	private String country;
	private int zipCode;
	private long number;
	private Date dateOfBirth;
	private String password;
	private boolean role = false;
	
	//Constructors
	public User(String name, String country, int zipCode, long number, Date dateOfBirth, String password, String role) {
		super();
		this.name = name;
		this.country = country;
		this.zipCode = zipCode;
		this.number = number;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		if(role=="true") {this.role = true;};
	}
	public User(String name, String country, int zipCode, String number, Date dateOfBirth, String password, String role) {
		super();
		this.name = name;
		this.country = country;
		this.zipCode = zipCode;
		this.number = Long.parseLong(number);
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		if(role=="true") {this.role = true;};
	}
	public User() {}
	
	//Getters & Setters
	public int getuID() {
		return uID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public void setNumber(String number) {
		this.number = Long.parseLong(number);
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public void setRole(String role) {
		if(role=="true") {this.role = true;};
	}
	
	@Override
	public String toString() {
		return "User [uID=" + uID + ", name=" + name + ", country=" + country + ", zipCode=" + zipCode + ", number="
				+ number + ", dateOfBirth=" + dateOfBirth + ", password=" + password + ", role=" + role + "]";
	}	
}
