package com.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pharmacy.business.IProduct;
import com.pharmacy.business.IStock;
import com.pharmacy.entities.Bill;
import com.pharmacy.entities.Product;
import com.pharmacy.entities.Stock;

public class StockDAO implements IStock {
	
	private Connection connection;
	
	public StockDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Stock find(int id) {
		IProduct productBusiness = new ProductDAO(connection);
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Stock where id=?");
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();
				if( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(4));
					return new Stock(rs.getInt(1), rs.getLong(2), rs.getLong(3),product);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public Stock findByProduct(int product_id) {
		IProduct productBusiness = new ProductDAO(connection);
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Stock where product_id=?");
				st.setInt(1, product_id);
				ResultSet rs = st.executeQuery();
				if( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(4));
					System.out.println("STOCK FOUND");
					return new Stock(rs.getInt(1), rs.getLong(2), rs.getLong(3),product);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	@Override
	public Stock add(Stock stock) {
		Stock returned_stock = null;
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("insert into Stock values(NULL,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				st.setLong(1, stock.getFirst_quantity());
				st.setLong(2, stock.getQuantity());
				st.setInt(3, stock.getProduct().getId());
				if( st.executeUpdate() != -1 ) {
					System.out.println("Stock added successflly");
					ResultSet rs = st.getGeneratedKeys();
					if( rs.next() ) {
						returned_stock = this.find(rs.getInt(1));
						return returned_stock;
					}
				}else
					System.err.println("Error occurred, stock not added");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returned_stock;
	}

	@Override
	public boolean update(Stock stock) {
		Bill returned_bill = null;
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("update Stock set first_quantity=?, quantity=?,product_id=? where id=?");
				st.setLong(1, stock.getFirst_quantity());
				st.setLong(2, stock.getQuantity());
				st.setInt(3, stock.getProduct().getId());
				st.setInt(4, stock.getId());
				if( !st.execute() ) 
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		PreparedStatement st;
		try {
			st = connection.prepareStatement("delete from Stock where id=?");
			st.setInt(1, id);
			if( !st.execute() ) 
				return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<Stock> findAll() {
		IProduct productBusiness = new ProductDAO(connection);
		List<Stock> stocks = new ArrayList<Stock>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Stock");
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(4));
					stocks.add(new Stock(rs.getInt(1), rs.getLong(2), rs.getLong(3), product));
				}
				return stocks;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Collection<Stock> findRange(int limit_results, int start_index) {
		IProduct productBusiness = new ProductDAO(connection);
		List<Stock> stocks = new ArrayList<Stock>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Stock limit ? offset ?");
				st.setInt(1, limit_results);
				st.setInt(2, start_index);
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(4));
					stocks.add(new Stock(rs.getInt(1), rs.getLong(2), rs.getLong(3), product));
				}
				return stocks;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
