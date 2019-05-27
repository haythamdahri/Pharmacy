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
import com.pharmacy.entities.Category;
import com.pharmacy.entities.Product;

public class CategoryDAO implements ICategory {
	
	private Connection connection;
	
	public CategoryDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Category find(int id) {
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Category where id=?");
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();
				if( rs.next() ) {
					return new Category(rs.getInt(1), rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Category add(Category category) {
		Category returned_category = null;
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("insert into Category values(NULL,?)", Statement.RETURN_GENERATED_KEYS);
				st.setString(1, category.getName());
				if( st.executeUpdate() != -1 ) {
					System.out.println("Category added successflly");
					ResultSet rs = st.getGeneratedKeys();
					if( rs.next() ) {
						returned_category = this.find(rs.getInt(1));
						return returned_category;
					}
				}else
					System.err.println("Error occurred, product not added");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returned_category;
	}

	@Override
	public boolean update(Category category) {
		PreparedStatement st;
		try {
			st = connection.prepareStatement("update Category set name=? where id=?");
			st.setString(1, category.getName());
			st.setInt(2, category.getId());
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
			st = connection.prepareStatement("delete from Category where id=?");
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
	public Collection<Category> findAll() {
		List<Category> categories = new ArrayList<Category>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Category");
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					categories.add(new Category(rs.getInt(1), rs.getString(2)));
				}
				return categories;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Collection<Category> findRange(int limit_results, int start_index) {
		List<Category> categories = new ArrayList<Category>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Category limit ? offset ?");
				st.setInt(1, limit_results);
				st.setInt(2, start_index);
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					categories.add(new Category(rs.getInt(1), rs.getString(2)));
				}
				return categories;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
