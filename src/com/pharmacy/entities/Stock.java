package com.pharmacy.entities;

public class Stock {
	private int id;
	private long first_quantity;
	private long quantity;
	private Product product;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getFirst_quantity() {
		return this.first_quantity;
	}
	public void setFirst_quantity(long first_quantity) {
		this.first_quantity = first_quantity;
	}
	public long getQuantity() {
		return this.quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return this.product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String toString() {
		return "ID: "+this.id+" | PRODUCT: "+this.product+" | FIRST QUANTITY: "+this.first_quantity+" | QUANTITY: "+this.quantity;
	}
	
	public Stock(int id, long first_quantity, long quantity, Product product) {
		this.id = id;
		this.first_quantity = first_quantity;
		this.quantity = quantity;
		this.product = product;
	}

}
