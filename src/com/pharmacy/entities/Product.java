package com.pharmacy.entities;

public class Product {
	private int id;
	private long code;
	private String name;
	private double price;
	private Category category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return this.price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String toString() {
		return "ID: "+this.id+" | CODE: "+this.code+" | NAME: "+this.name+" | PRICE: "+this.price+" | CATEGORY: "+this.category;
	}
	
	public Product(int id, long code, String name, double price, Category category) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.price = price;
		this.category = category;
	}
}
