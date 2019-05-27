package com.pharmacy.business;

import java.util.Collection;

import com.pharmacy.entities.Bill;
import com.pharmacy.entities.Product;

public interface IBill {

	public Bill find(int id);

	public Bill add(Bill bill);

	public boolean update(Bill bill);

	public boolean delete(int id);

	public Collection<Bill> findAll();
	
	public Collection<Bill> findRange(int limit_results, int start_index);

}
