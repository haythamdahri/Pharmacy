package com.pharmacy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.pharmacy.business.IBill;
import com.pharmacy.business.IProduct;
import com.pharmacy.business.IStock;
import com.pharmacy.dao.BillDAO;
import com.pharmacy.dao.ConfigDB;
import com.pharmacy.dao.ProductDAO;
import com.pharmacy.dao.StockDAO;
import com.pharmacy.entities.Bill;
import com.pharmacy.entities.Product;
import com.pharmacy.entities.Stock;

@WebServlet("/stock")
public class StockController extends HttpServlet{
	
	private String driver;
	private String url;
	private String username;
	private String password;
	private IProduct productBusiness;
	private IBill billBusiness;
	private IStock stockBusiness;
	
	public void init() {
		// Servlet Params registered under <servlet> tag
		ServletContext context = this.getServletContext();
		
		// Retrieving saved parameters with their values
		this.driver = context.getInitParameter("driver");
		this.url = context.getInitParameter("url");
		this.username = context.getInitParameter("db_user");
		this.password = context.getInitParameter("db_password");
		
		// Declaring new interface variable in order to concretize by implementers
		Connection connection = ConfigDB.getInstance().getConnection(driver, url, username, password);
		this.productBusiness = new ProductDAO(connection);
		this.billBusiness = new BillDAO(connection);
		this.stockBusiness = new StockDAO(connection);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {	
		try {
	
		// SessionScope to retrieve registred attributes that depend on Client Entity
		HttpSession session = request.getSession();
		
			// Declaring new interface variable in order to concretize by implementers
			List<Stock> stocks = (List<Stock>)stockBusiness.findAll();
			
			// Send data to view
			String id_param = request.getParameter("id");
			String page_param = request.getParameter("page");
			String edit_param = request.getParameter("edit");
			String add_param = request.getParameter("add");
			
			int recordsPerPage = 5;
			if( id_param == null && edit_param == null && add_param == null ) {
				if( page_param == null ){
					page_param = "1";
				}
				int nb_products = stocks.size();
				int	start_index = (Integer.parseInt(page_param)-1)*recordsPerPage;
				int limit_results = recordsPerPage;
	            int noOfPages = (int) Math.ceil(nb_products * 1.0 / recordsPerPage);
				stocks = (List<Stock>)this.stockBusiness.findRange(limit_results, start_index);
				request.setAttribute("stocks", stocks);
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("page_no", Integer.parseInt(page_param));
				request.setAttribute("stockBusiness", stockBusiness);
			}else if( id_param != null && !id_param.equals("") ) {
				Stock stock = stockBusiness.find(Integer.parseInt(id_param));
				request.setAttribute("stock", stock);
				request.setAttribute("search_stock", true);
				request.setAttribute("stockBusiness", stockBusiness);
			}else if( edit_param != null && !edit_param.equals("") ) {
				Stock stock = stockBusiness.find(Integer.parseInt(edit_param));
				request.setAttribute("stock", stock);
				request.setAttribute("edit_stock", true);
				request.setAttribute("productBusiness", productBusiness);
				request.getRequestDispatcher("stock_edit.jsp").forward(request, response);
				return;	
			}else if( add_param != null ) {
				request.setAttribute("productBusiness", productBusiness);
				request.getRequestDispatcher("stock_add.jsp").forward(request, response);
				return;
			}else{
				response.sendRedirect(request.getContextPath());
				return;
			}
			request.setAttribute("page_url", "stock");
			
			request.getRequestDispatcher("stock.jsp").forward(request, response);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		// SessionScope to retrieve registred attributes that depend on Client Entity
		HttpSession session = request.getSession();
		
		switch( request.getParameter("type") ) {
			case "add":
				add(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
			case "update":
				update(request, response);
				break;
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			long first_quantity = Integer.parseInt(request.getParameter("first_quantity"));
			long quantity = Integer.parseInt(request.getParameter("quantity"));
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			
			if( stockBusiness.findByProduct(product_id) != null ) {
				session.setAttribute("stock_add_error", true);
				session.setAttribute("stock_add_error_message", "Le produit existe déja dans le stock!");
				response.sendRedirect(request.getContextPath()+"/stock?add");
				return;
			}
			
			Product product = this.productBusiness.find(product_id);
			Stock stock = new Stock(0, first_quantity, quantity, product);
			Stock added_stock = this.stockBusiness.add(stock);
			
			if( added_stock != null ) {
				session.setAttribute("is_added_stock", true);
				session.setAttribute("icon", "done_all");
				session.setAttribute("message", "Le stock à été ajouté avec succé");
				session.setAttribute("added_stock", stock);
			}
			response.sendRedirect(request.getContextPath()+"/stock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String id_param = request.getParameter("id");
			if( !id_param.equals("") ) {
				Stock stock = stockBusiness.find(Integer.parseInt(id_param));
				boolean is_deleted = this.stockBusiness.delete(Integer.parseInt(id_param));
				if( is_deleted ) {
					
					session.setAttribute("is_deleted_stock", true);
					session.setAttribute("icon", "done_all");
					session.setAttribute("message", "Le stock à été supprimé avec succé");
					session.setAttribute("deleted_stock", stock);
				}
			}
			response.sendRedirect(request.getContextPath()+"/stock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			int id = Integer.parseInt(request.getParameter("id"));
			long first_quantity = Integer.parseInt(request.getParameter("first_quantity"));
			long quantity = Integer.parseInt(request.getParameter("quantity"));
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			
			Product product = this.productBusiness.find(product_id);
			Stock stock = stockBusiness.find(id);
			
			if( stock.getProduct().getId() != product_id && stockBusiness.findByProduct(product_id) != null ) {
				session.setAttribute("stock_update_error", true);
				session.setAttribute("stock_update_error_message", "Le stock du médicament "+product.getName()+" existe déja dans le stock!");
				response.sendRedirect(request.getContextPath()+"/stock?edit="+stock.getId());
				return;
			}

			Stock new_stock = new Stock(id, first_quantity, quantity, product);
			boolean is_updated =this.stockBusiness.update(new_stock);
			
			if( is_updated ) {
				session.setAttribute("is_updated_stock", true);
				session.setAttribute("icon", "done_all");
				session.setAttribute("message", "Le stock dont ID="+stock.getId()+" à été modifié avec succé");
				session.setAttribute("updated_stock", stock);
			}
			response.sendRedirect(request.getContextPath()+"/stock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String stringResponse(HttpServletRequest request, HttpServletResponse response, String jsp_page) {
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
			
            private final StringWriter sw = new StringWriter();

            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(sw);
            }

            @Override
            public String toString() {
                return sw.toString();
            }
        };
        try {
			request.getRequestDispatcher(jsp_page).include(request, responseWrapper);
	        String content = responseWrapper.toString();
	        return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
        return null;
	}
	

}
