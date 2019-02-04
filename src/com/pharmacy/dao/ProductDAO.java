package com.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pharmacy.business.ICategory;
import com.pharmacy.business.IProduct;
import com.pharmacy.entities.Category;
import com.pharmacy.entities.Product;

public class ProductDAO implements IProduct{

	private Connection connection;
	
	public ProductDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Product find(int id) {
		ICategory categoryBusiness = new CategoryDAO(connection);
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Product where id=?");
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();
				if( rs.next() ) {
					Category category = categoryBusiness.find(rs.getInt(5));
					return new Product(rs.getInt(1), rs.getLong(2), rs.getString(3), rs.getDouble(4), category);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public Product findByCode(int code) {
		ICategory categoryBusiness = new CategoryDAO(connection);
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Product where code=?");
				st.setInt(1, code);
				ResultSet rs = st.executeQuery();
				if( rs.next() ) {
					Category category = categoryBusiness.find(rs.getInt(5));
					return new Product(rs.getInt(1), rs.getLong(2), rs.getString(3), rs.getDouble(4), category);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Product add(Product product) {
		Product returned_product = null;
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("insert into Product values(NULL,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				st.setLong(1, product.getCode());
				st.setString(2, product.getName());
				st.setDouble(3, product.getPrice());
				st.setInt(4, product.getCategory().getId());
				if( st.executeUpdate() != -1 ) {
					System.out.println("Product added successflly");
					ResultSet rs = st.getGeneratedKeys();
					if( rs.next() ) {
						returned_product = this.find(rs.getInt(1));
						return returned_product;
					}
				}else
					System.err.println("Error occurred, product not added");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returned_product;
	}

	@Override
	public boolean update(Product product) {
		PreparedStatement st;
		try {
			st = connection.prepareStatement("update Product set code=?, name=?, price=?, category_id=? where id=?");
			st.setLong(1, product.getCode());
			st.setString(2, product.getName());
			st.setDouble(3, product.getPrice());
			st.setInt(4, product.getCategory().getId());
			st.setInt(5, product.getId());
			if( !st.execute() ) 
				return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		PreparedStatement st;
		try {
			st = connection.prepareStatement("delete from Product where id=?");
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
	public Collection<Product> findAll() {
		ICategory categoryBusiness = new CategoryDAO(connection);
		List<Product> products = new ArrayList<Product>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Product");
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					Category category = categoryBusiness.find(rs.getInt(5));
					products.add(new Product(rs.getInt(1), rs.getLong(2), rs.getString(3), rs.getDouble(4), category));
				}
				return products;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public Collection<Product> findRange(int limit_results, int start_index) {
		ICategory categoryBusiness = new CategoryDAO(connection);
		List<Product> products = new ArrayList<Product>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Product limit ? offset ?");
				st.setInt(1, limit_results);
				st.setInt(2, start_index);
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					Category category = categoryBusiness.find(rs.getInt(5));
					products.add(new Product(rs.getInt(1), rs.getLong(2), rs.getString(3), rs.getDouble(4), category));
				}
				return products;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
