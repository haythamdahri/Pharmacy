<%@page import="com.pharmacy.entities.Category"%>
<%@page import="com.pharmacy.business.ICategory"%>
<%@page import="com.pharmacy.dao.CategoryDAO"%>
<%@page import="com.pharmacy.entities.Product"%>
<%@page import="com.pharmacy.business.IProduct"%>
<%@page import="com.pharmacy.dao.ProductDAO"%>
<%@page import="com.pharmacy.entities.Stock"%>

<%
IProduct productBusiness = (ProductDAO)request.getAttribute("productBusiness");
%>

<div class="row" style="margin-top: 20px;">

	<% if( session.getAttribute("bill_add_error") != null){ %>
		<div class="card-panel white-text red darken-1"><i class="left material-icons">do_not_disturb</i><%= session.getAttribute("bill_add_error_message") %></div>
	<%
		session.removeAttribute("bill_add_error");
		session.removeAttribute("bill_add_error_message");
	} %>
	
  <form class="col s12" method="POST">
      
      <input type="hidden" name="type" value="add_non_ajax" />
      
      <div class="row">
        <div class="input-field col s12">
          <input id="id" type="text" value="#" name="id" readonly>
          <label for="ID">ID</label>
        </div>
      </div>
      
      
      <div class="row">
	      <div class="input-field col s12">
		    <select name="product_id">
		      	<% for( Product product : productBusiness.findAll() ){ %>
		      	<option value="<%= product.getId() %>"><%= product.getName() %> | <%= product.getId() %></option>
		    	<% } %>
		    </select>
		    <label>Selectionner le produit</label>
		  </div>
      </div>
     
      
      <div class="row">
        <div class="input-field col s12">
          <input id="quantity"  required aria-required="true" type="number" name="quantity" class="validate">
          <label for="quantity">Quantite</label>
          <span class="helper-text" data-error="Remplir ce champ" data-success="OK"></span>
        </div>
      </div>
      
	<div class="row">
		<button class="btn waves-effect waves-light" type="submit" name="action">Ajouter
	    	<i class="material-icons right">send</i>
	  	</button>
	</div>
  </form>
</div>
      