package com.pharmacy.business;

import java.util.Collection;

import com.pharmacy.entities.Category;
import com.pharmacy.entities.Product;

public interface ICategory {

	public Category find(int id);

	public Category add(Category category);

	public boolean update(Category category);

	public boolean delete(int id);

	public Collection<Category> findAll();
	
	public Collection<Category> findRange(int limit_results, int start_index);

}
