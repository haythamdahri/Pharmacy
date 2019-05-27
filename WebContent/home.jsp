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
	<title>Acceuil : Pharmacy HAYTHAM</title>
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
	
	<%@ include file="parts/search_form_product.jsp" %>
	
	
	<div class="row center-align">
		<a href="<%= request.getContextPath() %>?add" class="waves-effect waves-light btn-small light-green darken-1"><i class="material-icons left">add_to_queue</i>Aouter Un Medicament</a>
	</div>
	
	<% if( session.getAttribute("added_product") != null || session.getAttribute("updated_product") != null || session.getAttribute("deleted_product") != null ){ %>
	<div class="card-panel white-text blue lighten-2"><i class="left material-icons"><%= session.getAttribute("icon") %></i><%= session.getAttribute("message") %></div>
	<%
		session.removeAttribute("updated_product");
		session.removeAttribute("deleted_product");
		session.removeAttribute("added_product");
		session.removeAttribute("icon");
		session.removeAttribute("message");
	} %>
	
	
		<table class="centered striped responsive-table " style="margin-bottom: 100px;">
        <tbody>
		<tr class="z-depth-3">
			<th style="font-weight: bold" class="blue-text">#</th>
			<th>Nom Medicament</th>
			<th>Prix</th>
			<th>Category</th>
			<th>Quantite valable</th>
			<th colspan="3">Actions</th>
		</tr>
		<% if( request.getAttribute("search_product") == null ){%>
		<% for(Product product : (List<Product>)request.getAttribute("products")){ %>
		<% request.setAttribute("product_modal", product);%>
		<%@include file="parts/add_bill_modal.jsp" %>
		
		<% Stock stock = stockBusiness.findByProduct(product.getId()); %>
		<tr class="hoverable z-depth-1 <% if( stock == null || stock.getQuantity() == 0){ %> text-darken-2  lime accent-3<%}%>">
			<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= product.getCode() %></blockquote></td>
			<td><%= product.getName() %></td>
			<td><%= product.getPrice() %> Dhs</td>
			<td><%= product.getCategory().getName() %></td>
			<td><% if( stock == null || stock.getQuantity() == 0){ %>0<%}else{ out.print(stock.getQuantity()); } %></td>
			<td>
				<a href="<%= request.getContextPath() %>/?edit=<%= product.getCode() %>" class="btn-floating pulse waves-effect waves-light #ffab00 lime accent-4"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Modifier Medicament">edit</i></a>
			</td>
			<td>
				<form id="removeProduct<%= product.getId() %>" action="<%= request.getContextPath() %>/" method="POST">
				<input type="hidden" name="id" value="<%= product.getId() %>" />
				<input type="hidden" name="type" value="delete" />
				</form>
				<a onclick="deleteRow('removeProduct<%= product.getId() %>', 'Medicament');" class="btn-floating pulse waves-effect waves-light orange darken-4 tooltipped" data-position="bottom" data-tooltip="Supprimer Medicament"><i class="material-icons right">remove_circle</i></a>
			</td>
			<td>
				<a href="#addBill<%= product.getId() %>" class="btn-floating pulse waves-effect waves-light #ffab00 light-green darken-2 modal-trigger"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Commander Meidicament">add_circle</i></a>
			</td>
		</tr> 
  
		<% } %>
		
		  
		<%}else{ 
			Product product = (Product)request.getAttribute("product");
			if( product != null){
		%>
		<% request.setAttribute("product_modal", product);%>
		<% Stock stock = stockBusiness.findByProduct(product.getId()); %>
		<%@include file="parts/add_bill_modal.jsp" %>
		<tr class="hoverable z-depth-1 <% if( stock == null || stock.getQuantity() == 0){ %> text-darken-2  lime accent-3<%}%>">
			<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= product.getCode() %></blockquote></td>
			<td><%= product.getName() %></td>
			<td><%= product.getPrice() %> Dhs</td>
			<td><%= product.getCategory().getName() %></td>
			<td><% if( stock == null || stock.getQuantity() == 0){ %>0<%}else{ out.print(stock.getQuantity()); } %></td>
			<td>
				<a href="<%= request.getContextPath() %>/?edit=<%= product.getCode() %>" class="btn-floating pulse waves-effect waves-light #ffab00 lime accent-4"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Modifier Medicament">edit</i></a>
			</td>
			<td>
				<form id="removeProduct<%= product.getId() %>" action="<%= request.getContextPath() %>/" method="POST">
				<input type="hidden" name="id" value="<%= product.getId() %>" />
				<input type="hidden" name="type" value="delete" />
				</form>
				<a onclick="deleteRow('removeProduct<%= product.getId() %>', 'Medicament');" class="btn-floating pulse waves-effect waves-light orange darken-4 tooltipped" data-position="bottom" data-tooltip="Supprimer Medicament"><i class="material-icons right">remove_circle</i></a>
			</td>
			<td>
				<a href="#addBill<%= product.getId() %>" class="btn-floating pulse waves-effect waves-light #ffab00 light-green darken-2 modal-trigger"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Commander Meidicament">add_circle</i></a>
			</td>
		</tr>
		<% }else{%>
				<div class="card-panel white-text red darken-1"><i class="left material-icons">do_not_disturb</i>Aucun medicament trouvé avec CODE <%= request.getParameter("code") %></div>
		<% }} %>
		</tbody>
	</table>
	
	<% if( request.getAttribute("search_product") == null ){ %>
	<% int noOfPages = (int)request.getAttribute("noOfPages");
	   int page_no = (int)request.getAttribute("page_no");
	%>
		<div class="row center-align">
			<ul class="pagination">
				<li><a href="<%= request.getContextPath() %>"><i class="material-icons">chevron_left</i></a></li>
				<% for( int i=1; i <= noOfPages; i++ ){ %>
					<li class="<% if( i == page_no ){ %>active<% } %>"><a href="<%= request.getContextPath() %>/?page=<%= i %>"><%= i %></a></li>
				<% } %>
				<li class="waves-effect"><a href="<%= request.getContextPath() %>/?page=<%= noOfPages %>"><i class="material-icons">chevron_right</i></a></li>
			</ul>
		</div>  
	
	<% } %>
		
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
	<script type="text/javascript" src="static/js/sweetalert2.all.min.js"></script>
	<script type="text/javascript" src="static/js/custom.js"></script>
	
</body>
</html>