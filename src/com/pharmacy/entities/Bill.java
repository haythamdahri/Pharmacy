package com.pharmacy.entities;

import java.util.ArrayList;
import java.util.List;

public class Bill {
	private int id;
	private Product product;
	private int quantity;
	private double price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Bill(int id, Product product, int quantity) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.price = this.quantity*this.product.getPrice();
	}
	
}
