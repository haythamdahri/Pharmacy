package com.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pharmacy.business.IBill;
import com.pharmacy.business.ICategory;
import com.pharmacy.business.IProduct;
import com.pharmacy.entities.Bill;
import com.pharmacy.entities.Category;
import com.pharmacy.entities.Product;

public class BillDAO implements IBill{
	
	private Connection connection;
	
	public BillDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Bill find(int id) {
		IProduct productBusiness = new ProductDAO(connection);
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Bill where id=?");
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();
				if( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(3));
					return new Bill(rs.getInt(1), product, rs.getInt(4));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Bill add(Bill bill) {
		Bill returned_bill = null;
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("insert into Bill values(NULL,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				st.setDouble(1, bill.getPrice());
				st.setInt(2, bill.getProduct().getId());
				st.setInt(3, bill.getQuantity());
				if( st.executeUpdate() != -1 ) {
					System.out.println("Bill added successflly");
					ResultSet rs = st.getGeneratedKeys();
					if( rs.next() ) {
						returned_bill = this.find(rs.getInt(1));
						return returned_bill;
					}
				}else
					System.err.println("Error occurred, bill not added");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returned_bill;
	}

	@Override
	public boolean update(Bill bill) {
		Bill returned_bill = null;
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("update Bill set price=?, product_id=?,quantity=? where id=?");
				st.setDouble(1, bill.getPrice());
				st.setInt(2, bill.getProduct().getId());
				st.setInt(3, bill.getQuantity());
				st.setInt(4, bill.getId());
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
			st = connection.prepareStatement("delete from Bill where id=?");
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
	public Collection<Bill> findAll() {
		IProduct productBusiness = new ProductDAO(connection);
		List<Bill> bills = new ArrayList<Bill>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Bill");
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(3));
					bills.add(new Bill(rs.getInt(1), product, rs.getInt(4)));
				}
				return bills;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Collection<Bill> findRange(int limit_results, int start_index) {
		IProduct productBusiness = new ProductDAO(connection);
		List<Bill> bills = new ArrayList<Bill>();
		if( connection != null ) {
			PreparedStatement st;
			try {
				st = connection.prepareStatement("select * from Bill limit ? offset ?");
				st.setInt(1, limit_results);
				st.setInt(2, start_index);
				ResultSet rs = st.executeQuery();
				while( rs.next() ) {
					Product product = productBusiness.find(rs.getInt(3));
					bills.add(new Bill(rs.getInt(1), product, rs.getInt(4)));
				}
				return bills;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
