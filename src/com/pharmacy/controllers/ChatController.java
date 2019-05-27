package com.pharmacy.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chat")
public class ChatController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// Set chat page as current one
			request.setAttribute("page_url", "chat");
			
			// Forward response to jsp page
			request.getRequestDispatcher("chat.jsp").forward(request, response);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
