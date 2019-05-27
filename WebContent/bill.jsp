<%@page import="com.pharmacy.business.IStock"%>
<%@page import="com.pharmacy.dao.*"%>
<%@ page import="com.pharmacy.entities.*" %>
<%@ page import="com.pharmacy.dao.*" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	
	<%@ include file="parts/search_form.jsp" %>
	
	
	<div class="row center-align">
		<a href="<%= request.getContextPath() %>/bill?add" class="waves-effect waves-light btn-small light-green darken-1"><i class="material-icons left">add_to_queue</i>Aouter Une Facture</a>
	</div>
	
	<% if( session.getAttribute("added_bill") != null || session.getAttribute("updated_bill") != null || session.getAttribute("deleted_bill") != null ){ %>
		<div class="card-panel white-text blue lighten-2"><i class="left material-icons"><%= session.getAttribute("icon") %></i><%= session.getAttribute("message") %></div>
	<%
		session.removeAttribute("updated_bill");
		session.removeAttribute("deleted_bill");
		session.removeAttribute("added_bill");
		session.removeAttribute("icon");
		session.removeAttribute("message");
	} %>
	
	
		<table class="centered striped responsive-table " style="margin-bottom: 100px;">
        <tbody>
		<tr class="z-depth-3">
			<th style="font-weight: bold" class="blue-text">#</th>
			<th>Medicament x Quantité</th>
			<th>Prix</th>
			<th colspan="3">Actions</th>
		</tr>
		<% if( request.getAttribute("search_bill") == null ){%>
		<% for(Bill bill : (List<Bill>)request.getAttribute("bills")){ %>
		
		<tr class="hoverable z-depth-1">
			<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= bill.getId() %></blockquote></td>
			<td><%= bill.getProduct().getName() %> x <%= bill.getQuantity() %></td>
			<td><%= bill.getPrice() %> Dhs</td>
			<td>
				<a href="<%= request.getContextPath() %>/bill?edit=<%= bill.getId() %>" class="btn-floating pulse waves-effect waves-light #ffab00 lime accent-4"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Modifier Facture">edit</i></a>
			</td>
			<td>
				<form id="removeBill<%= bill.getId() %>" action="<%= request.getContextPath() %>/bill" method="POST">
				<input type="hidden" name="id" value="<%= bill.getId() %>" />
				<input type="hidden" name="type" value="delete" />
				</form>
				<a onclick="deleteRow('removeBill<%= bill.getId() %>', 'Facture');" class="btn-floating pulse waves-effect waves-light orange darken-4 tooltipped" data-position="bottom" data-tooltip="Supprimer Facture"><i class="material-icons right">remove_circle</i></a>
			</td>
		</tr> 
  
		<% } %>
		
		  
		<%}else{ 
			Bill bill = (Bill)request.getAttribute("bill");
			if( bill != null){
		%>
		
		<tr class="hoverable z-depth-1">
			<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= bill.getId() %></blockquote></td>
			<td><%= bill.getProduct().getName() %> x <%= bill.getQuantity() %></td>
			<td><%= bill.getPrice() %> Dhs</td>
			<td>
				<a href="<%= request.getContextPath() %>/bill?edit=<%= bill.getId() %>" class="btn-floating pulse waves-effect waves-light #ffab00 lime accent-4"><i class="material-icons right lighten-2 tooltipped" data-position="bottom" data-tooltip="Modifier Facture">edit</i></a>
			</td>
			<td>
				<form id="removeBill<%= bill.getId() %>" action="<%= request.getContextPath() %>/bill" method="POST">
				<input type="hidden" name="id" value="<%= bill.getId() %>" />
				<input type="hidden" name="type" value="delete" />
				</form>
				<a onclick="deleteRow('removeBill<%= bill.getId() %>', 'Facture');" class="btn-floating pulse waves-effect waves-light orange darken-4 tooltipped" data-position="bottom" data-tooltip="Supprimer Facture"><i class="material-icons right">remove_circle</i></a>
			</td>
		</tr> 
		
		<% }else{%>
				<div class="card-panel white-text red darken-1"><i class="left material-icons">do_not_disturb</i>Aucune facture trouvée avec ID <%= request.getParameter("id") %></div>
		<% }} %>
		</tbody>
	</table>
	
	<% if( request.getAttribute("search_bill") == null ){ %>
	<% int noOfPages = (int)request.getAttribute("noOfPages");
	   int page_no = (int)request.getAttribute("page_no");
	%>
		<div class="row center-align">
			<ul class="pagination">
				<li><a href="<%= request.getContextPath() %>/bill"><i class="material-icons">chevron_left</i></a></li>
				<% for( int i=1; i <= noOfPages; i++ ){ %>
					<li class="<% if( i == page_no ){ %>active<% } %>"><a href="<%= request.getContextPath() %>/bill?page=<%= i %>"><%= i %></a></li>
				<% } %>
				<li class="waves-effect"><a href="<%= request.getContextPath() %>/bill?page=<%= noOfPages %>"><i class="material-icons">chevron_right</i></a></li>
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