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
import com.pharmacy.business.ICategory;
import com.pharmacy.business.IProduct;
import com.pharmacy.business.IStock;
import com.pharmacy.dao.BillDAO;
import com.pharmacy.dao.CategoryDAO;
import com.pharmacy.dao.ConfigDB;
import com.pharmacy.dao.ProductDAO;
import com.pharmacy.dao.StockDAO;
import com.pharmacy.entities.Category;
import com.pharmacy.entities.Product;
import com.pharmacy.entities.Stock;

@WebServlet("/")
public class HomeController extends HttpServlet{
	
	private String driver;
	private String url;
	private String username;
	private String password;
	private IProduct productBusiness;
	private IBill billBusiness;
	private IStock stockBusiness;
	private ICategory categoryBusiness;
	
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
		this.categoryBusiness = new CategoryDAO(connection);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {	
		try {
	
		// SessionScope to retrieve registred attributes that depend on Client Entity
		HttpSession session = request.getSession();
		
			// Declaring new interface variable in order to concretize by implementers
			List<Product> products = (List<Product>)productBusiness.findAll();
			
			// Send data to view
			String code_param = request.getParameter("code");
			String page_param = request.getParameter("page");
			String edit_param = request.getParameter("edit");
			String add_param = request.getParameter("add");
			
			int recordsPerPage = 5;
			if( code_param == null && edit_param == null && add_param == null ) {
				if( page_param == null ){
					page_param = "1";
				}
				int nb_products = products.size();
				int	start_index = (Integer.parseInt(page_param)-1)*recordsPerPage;
				int limit_results = recordsPerPage;
	            int noOfPages = (int) Math.ceil(nb_products * 1.0 / recordsPerPage);
				products = (List<Product>)this.productBusiness.findRange(limit_results, start_index);
				request.setAttribute("products", products);
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("page_no", Integer.parseInt(page_param));
				request.setAttribute("stockBusiness", stockBusiness);
			}else if( code_param != null && !code_param.equals("") ) {
				Product product = productBusiness.findByCode(Integer.parseInt(code_param));
				request.setAttribute("product", product);
				request.setAttribute("search_product", true);
				request.setAttribute("stockBusiness", stockBusiness);
			}else if( edit_param != null && !edit_param.equals("") ) {
				Product product = productBusiness.findByCode(Integer.parseInt(edit_param));
				System.out.println(product);
				request.setAttribute("product", product);
				request.setAttribute("edit_product", true);
				request.setAttribute("categoryBusiness", categoryBusiness);
				request.getRequestDispatcher("product_edit.jsp").forward(request, response);
				return;	
			}else if( add_param != null ) {
				request.setAttribute("categoryBusiness", this.categoryBusiness);
				request.getRequestDispatcher("product_add.jsp").forward(request, response);
				return;
			}else{
				response.sendRedirect(request.getContextPath());
				return;
			}
			request.setAttribute("page_url", "home");
			
			request.getRequestDispatcher("home.jsp").forward(request, response);
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
			int code = Integer.parseInt(request.getParameter("code"));
			String name = request.getParameter("name");
			double price = Double.parseDouble(request.getParameter("price"));
			Category category = this.categoryBusiness.find(Integer.parseInt(request.getParameter("category_id")));
			
			if( productBusiness.findByCode(code) != null ) {
				session.setAttribute("product_add_error", true);
				session.setAttribute("product_add_error_message", "Le code du produit est déja utilisé!");
				response.sendRedirect(request.getContextPath()+"/?add");
				return;
			}
			
			Product product = new Product(0, code, name, price, category);
			Product added_product = this.productBusiness.add(product);
			
			if( added_product != null ) {
				session.setAttribute("is_added_stock", true);
				session.setAttribute("icon", "done_all");
				session.setAttribute("message", "Le médicament à été ajouté avec succé");
				session.setAttribute("added_product", product);
			}
			response.sendRedirect(request.getContextPath());
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
				Product product = productBusiness.find(Integer.parseInt(id_param));
				boolean is_deleted = this.productBusiness.delete(Integer.parseInt(id_param));
				if( is_deleted ) {
					session.setAttribute("is_updated_stock", true);
					session.setAttribute("icon", "done_all");
					session.setAttribute("message", "Le médicament à été supprimé avec succé");
					session.setAttribute("deleted_product", product);
				}
			}
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			int id = Integer.parseInt(request.getParameter("id"));
			int code = Integer.parseInt(request.getParameter("code"));
			String name = request.getParameter("name");
			double price = Double.parseDouble(request.getParameter("price"));
			Category category = this.categoryBusiness.find(Integer.parseInt(request.getParameter("category_id")));
			Product p = this.productBusiness.find(id);
			
			if( code != p.getCode() && productBusiness.findByCode(code) != null ) {
				session.setAttribute("product_update_error", true);
				session.setAttribute("product_update_error_message", "Le code du produit est déja utilisé!");
				response.sendRedirect(request.getContextPath()+"/?edit="+p.getCode());
				return;
			}
			
			Product product = new Product(p.getId(), code, name, price, category);
			boolean is_updated = this.productBusiness.update(product);
			
			if( is_updated ) {
				session.setAttribute("is_updated_stock", true);
				session.setAttribute("icon", "done_all");
				session.setAttribute("message", "Le médicament dont CODE="+product.getCode()+" à été modifié avec succé");
				session.setAttribute("updated_product", product);
			}
			response.sendRedirect(request.getContextPath());
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
