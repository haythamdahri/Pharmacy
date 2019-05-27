<%@page import="com.pharmacy.business.IStock"%>
<%@page import="com.pharmacy.dao.*"%>
<%@ page import="com.pharmacy.entities.*" %>
<%@ page import="com.pharmacy.dao.*" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
IStock stockBusiness = (StockDAO)request.getAttribute("stockBusiness");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Categories : Pharmacy HAYTHAM</title>
	<link rel="icon" href="static/images/logo.png" />
	<link rel="stylesheet" type="text/css"  href="static/css/materialize.css"/>
	<link rel="stylesheet" type="text/css"  href="static/css/style.css" />
	<link rel="stylesheet" type="text/css"  href="static/css/all.min.css" />
	<link rel="stylesheet" type="text/css" href="static/css/material_icons.css" />

</head>
<body>


<main>
<%@ include file="parts/navbar.jsp" %>

	
	<div class="container">	
	
	<%@ include file="parts/search_form.jsp" %>
	
		<table class="centered striped responsive-table " style="margin-bottom: 100px;">
        <tbody>
		<tr class="z-depth-3">
			<th style="font-weight: bold" class="blue-text">#</th>
			<th>Medicament</th>
			<th>Première quantité</th>
			<th>Quantité actuelle</th>
		</tr>
		<% if( request.getAttribute("search_product") == null ){%>
		<% for(Product product : (List<Product>)request.getAttribute("products")){ %>
		<% request.setAttribute("product_modal", product);%>
		<%@include file="parts/add_bill_modal.jsp" %>
		
		<% Stock stock = stockBusiness.findByProduct(product.getId()); %>
		<tr class="hoverable z-depth-1 <% if( stock == null || stock.getQuantity() == 0){ %> white-text light-green darken-1<%}%>">
			<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= product.getCode() %></blockquote></td>
			<td><%= product.getName() %></td>
			<td><%= product.getPrice() %> Dhs</td>
			<td><%= product.getCategory().getName() %></td>
			<td><% if( stock == null || stock.getQuantity() == 0){ %>0<%}else{ out.print(stock.getQuantity()); } %></td>
			<td>
				<a href="#addBill<%= product.getId() %>" class="btn-floating pulse waves-effect waves-light #ffab00 light-green darken-2 modal-trigger"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Commander Meidicament">add_circle</i></a>
			</td>
		</tr>
		<% }
		}else{ 
			Product product = (Product)request.getAttribute("product");
			if( product != null){
		%>
		<% request.setAttribute("product_modal", product);%>
		<% Stock stock = stockBusiness.findByProduct(product.getId()); %>
		<%@include file="parts/add_bill_modal.jsp" %>
		<tr class="hoverable z-depth-1 <% if( stock == null || stock.getQuantity() == 0){ %> white-text light-green darken-1<%}%>">
			<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= product.getCode() %></blockquote></td>
			<td><%= product.getName() %></td>
			<td><%= product.getPrice() %> Dhs</td>
			<td><%= product.getCategory().getName() %></td>
			<td><% if( stock == null || stock.getQuantity() == 0){ %>0<%}else{ out.print(stock.getQuantity()); } %></td>
			<td>
				<a href="#addBill<%= product.getId() %>" class="btn-floating pulse waves-effect waves-light #ffab00 light-green darken-2 modal-trigger"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Commander Meidicament">add_circle</i></a>
			</td>
		</tr>
		<% }else{%>
				<div class="card-panel white-text light-green darken-1"><i class="left material-icons">do_not_disturb</i>Aucun medicament trouvé avec ID <%= request.getParameter("code") %></div>
		<% }} %>
		</tbody>
	</table>
	</div>
  		
  		
  		
  		
	</div>
	
	
</main>

<!-- Modal Structure -->
  <div id="" class="modal">
    <div class="modal-content">
      <h4>Modal Header</h4>
      <p>A bunch of text</p>
    </div>
    <div class="modal-footer">
      <a href="#!" class="modal-close waves-effect waves-green btn-flat">Agree</a>
    </div>
  </div>
          


	<%@ include file="parts/footer.jsp" %>
	
	
	<script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="static/js/materialize.min.js"></script>
	<script type="text/javascript" src="static/js/custom.js"></script>
	
</body>
</html>