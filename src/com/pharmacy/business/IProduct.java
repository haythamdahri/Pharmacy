package com.pharmacy.business;

import java.util.Collection;

import com.pharmacy.entities.Product;

public interface IProduct {

	public Product find(int id);
	
	public Product findByCode(int code);

	public Product add(Product product);

	public boolean update(Product product);

	public boolean delete(int id);

	public Collection<Product> findAll();
	
	public Collection<Product> findRange(int limit_results, int start_index);

}
