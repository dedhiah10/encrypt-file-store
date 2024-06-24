package com.SL.SportyShoes.Entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PRODUCTS")
public class Products {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pID;
	private String name;
	private String types;
	private String company;
	private int price;
	@Column(columnDefinition = "varchar(1000)")
	private String info;
	@Column(columnDefinition = "varchar(255)")
	private String sizesDB = "";
	private transient int[] sizes;
	
	//Constructors
	public Products(String name, String company, String type, int[]sizes, int price) {
		super();
		this.name = name;
		this.company = company;
		this.types = type;
		this.sizes = sizes;
		this.price = price;
		for(int i = 0; i < sizes.length; i++) {sizesDB += (sizes[i] + "_");}
	}
	public Products(String name, String company, String type, String sizesDB, int price) {
		super();
		this.name = name;
		this.price = price;
		this.company = company;
		this.sizesDB = sizesDB;
		this.types = type;
		String[] x = sizesDB.split("_");
		sizes = new int[sizesDB.length()];
		for(int i = 0; i< x.length; i++) {sizes[i] = Integer.parseInt(x[i]);}
	}
	public Products() {}
	
	//Setters & Getters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getpID() {
		return pID;
	}
	public String getType() {
		return types;
	}
	public void setType(String type) {
		this.types = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	public String toString() {
		return "Products [pID=" + pID + ", name=" + name + ", types=" + types + ", company=" + company + ", price="
				+ price + ", info=" + info + ", sizesDB=" + sizesDB + "]";
	}
}
