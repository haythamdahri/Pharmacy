package com.pharmacy.business;

import java.util.Collection;

import com.pharmacy.entities.Category;
import com.pharmacy.entities.Stock;

public interface IStock {

	public Stock find(int id);
	
	public Stock findByProduct(int product_id);

	public Stock add(Stock stock);

	public boolean update(Stock stock);

	public boolean delete(int id);

	public Collection<Stock> findAll();
	
	public Collection<Stock> findRange(int limit_results, int start_index);

}
