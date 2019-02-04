package com.pharmacy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
import com.pharmacy.entities.Bill;
import com.pharmacy.entities.Category;
import com.pharmacy.entities.Product;
import com.pharmacy.entities.Stock;

@WebServlet("/bill")
public class BillController extends HttpServlet{
	
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
			List<Bill> bills = (List<Bill>)billBusiness.findAll();
			
			// Send data to view
			String id_param = request.getParameter("id");
			String page_param = request.getParameter("page");
			String edit_param = request.getParameter("edit");
			String add_param = request.getParameter("add");
			
			int recordsPerPage = 5;
			if( id_param == null && edit_param == null && add_param == null ) {
				System.out.println("ALL BILLS");
				if( page_param == null ){
					page_param = "1";
				}
				int nb_products = bills.size();
				int	start_index = (Integer.parseInt(page_param)-1)*recordsPerPage;
				int limit_results = recordsPerPage;
	            int noOfPages = (int) Math.ceil(nb_products * 1.0 / recordsPerPage);
				bills = (List<Bill>)this.billBusiness.findRange(limit_results, start_index);
				request.setAttribute("bills", bills);
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("page_no", Integer.parseInt(page_param));
				request.setAttribute("productBusiness", productBusiness);
			}else if( id_param != null && !id_param.equals("") ) {
				Bill bill = billBusiness.find(Integer.parseInt(id_param));
				request.setAttribute("bill", bill);
				request.setAttribute("search_bill", true);
				request.setAttribute("productBusiness", productBusiness);
			}else if( edit_param != null && !edit_param.equals("") ) {
				Bill bill = billBusiness.find(Integer.parseInt(edit_param));
				request.setAttribute("bill", bill);
				request.setAttribute("edit_bill", true);
				request.setAttribute("productBusiness", productBusiness);
				request.getRequestDispatcher("bill_edit.jsp").forward(request, response);
				return;	
			}else if( add_param != null ) {
				request.setAttribute("productBusiness", productBusiness);
				request.getRequestDispatcher("bill_add.jsp").forward(request, response);
				return;
			}else{
				response.sendRedirect(request.getContextPath());
				return;
			}
			request.setAttribute("page_url", "bill");
			
			request.getRequestDispatcher("bill.jsp").forward(request, response);
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
			case "add_non_ajax":
				addNonAjax(request, response);
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
		for( int i=0; i<250000; i++ )
			System.out.println(i);
		//Retrive passed params
		int product_id = Integer.parseInt(request.getParameter("product"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		Stock stock = this.stockBusiness.findByProduct(product_id);
		String message = "";
		if( stock != null ) {
			if( stock.getQuantity() > 0 ) {
				if( stock.getQuantity() >= quantity ) {
					Product product = this.productBusiness.find(product_id);
					Bill bill = new Bill(0, product, quantity);
					Bill returned_bill = this.billBusiness.add(bill);
					if( returned_bill != null ) {
						stock.setQuantity(stock.getQuantity()-returned_bill.getQuantity());
						stockBusiness.update(stock);
						message = "<div class=\"card-panel light-green darken-2\"> <span class=\"white-text\"><i class=\"left material-icons\">offline_pin</i> Facture effectuee du médicament "+stock.getProduct().getName()+".</span> </div>";
						request.setAttribute("bill", returned_bill);
						message += stringResponse(request, response, "parts/added_bill.jsp");
					}else {
						message = "<div class=\"card-panel white-text red darken-1\"><i class=\"left material-icons\">do_not_disturb</i> Une erreur est survenue, veuillez ressayer.</div>";
					}
				}else {
					message = "<div class=\"card-panel white-text red darken-1\"><i class=\"left material-icons\">do_not_disturb</i> La quantité disponible est insuffisante: "+stock.getQuantity()+" unités de "+stock.getProduct().getName()+" disponible pour l'instant</div>";
						
					}
			}else {
				message = "<div class=\"card-panel white-text red darken-1\"><i class=\"left material-icons\">do_not_disturb</i> Le médicament "+stock.getProduct().getName()+" est indisponible.</div>";
			}
		}else {
			message = "<div class=\"card-panel white-text red darken-1\"><i class=\"left material-icons\">do_not_disturb</i> Le médicament "+stock.getProduct().getName()+" est indisponible dans le stock.</div>";
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(message);
		}
		catch(Exception ex) {
			
		}
		
	}
	
	private void addNonAjax(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			Product product = this.productBusiness.find(Integer.parseInt(request.getParameter("product_id")));

			Bill bill = new Bill(0, product, quantity);
			
			Stock stock = stockBusiness.findByProduct(product.getId());
			if( stock.getQuantity() - quantity < 0 ) {
				session.setAttribute("bill_add_error", true);
				session.setAttribute("bill_add_error_message", "La quantité demandée du "+product.getName()+" n'est pas disponible, "+stock.getQuantity()+" unités disponible pour l'instant!");
				response.sendRedirect(request.getContextPath()+"/bill?add");
				return;
			}else {
				stock.setQuantity(stock.getQuantity() - quantity);
				stockBusiness.update(stock);
			}
			
			
			Bill added_bill = this.billBusiness.add(bill);
			
			if( added_bill != null ) {
				session.setAttribute("is_added_bill", true);
				session.setAttribute("icon", "done_all");
				session.setAttribute("message", "La facture à été ajoutée avec succé");
				session.setAttribute("added_bill", bill);
			}
			response.sendRedirect(request.getContextPath()+"/bill");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			System.out.println(request.getParameter("product_id"));
			int id = Integer.parseInt(request.getParameter("id"));
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			Bill bill = billBusiness.find(id);
			Product product = productBusiness.find(product_id);
			if( quantity != bill.getQuantity() ) {
				Stock stock = stockBusiness.findByProduct(product.getId());
				int quantity_difference = quantity - bill.getQuantity();
				if( stock.getQuantity() - quantity_difference < 0 ) {
					session.setAttribute("bill_update_error", true);
					session.setAttribute("bill_update_error_message", "La quantité demandée du "+product.getName()+" n'est pas disponible, "+stock.getQuantity()+" unités disponible pour l'instant!");
					response.sendRedirect(request.getContextPath()+"/bill?edit="+bill.getId());
					return;
				}else {
					stock.setQuantity(stock.getQuantity()-quantity_difference);
					stockBusiness.update(stock);
				}
			}
			
			Bill new_bill = new Bill(bill.getId(), product, quantity);
			boolean is_updated = billBusiness.update(new_bill);
			
			if( is_updated ) {
				session.setAttribute("is_updated_bill", true);
				session.setAttribute("icon", "done_all");
				session.setAttribute("message", "La facture dont ID="+new_bill.getId()+" à été modifiée avec succé");
				session.setAttribute("updated_bill", new_bill);
			}
			response.sendRedirect(request.getContextPath()+"/bill");
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
				Bill bill = billBusiness.find(Integer.parseInt(id_param));
				boolean is_deleted = this.billBusiness.delete(Integer.parseInt(id_param));
				if( is_deleted ) {
					Stock stock = stockBusiness.findByProduct(bill.getProduct().getId());
					stock.setQuantity(stock.getQuantity()+bill.getQuantity());
					stockBusiness.update(stock);
					
					session.setAttribute("is_deleted_bill", true);
					session.setAttribute("icon", "done_all");
					session.setAttribute("message", "La facture à été supprimée avec succé");
					session.setAttribute("deleted_bill", bill);
				}
			}
			response.sendRedirect(request.getContextPath()+"/bill");
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
