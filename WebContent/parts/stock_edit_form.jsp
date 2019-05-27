<%@page import="com.pharmacy.entities.Product"%>
<%@page import="com.pharmacy.business.IProduct"%>
<%@page import="com.pharmacy.dao.ProductDAO"%>
<%@page import="com.pharmacy.entities.Stock"%>

<%
Stock stock = (Stock)request.getAttribute("stock"); 
IProduct productBusiness = (ProductDAO)request.getAttribute("productBusiness");
%>

<div class="row" style="margin-top: 20px;">

	<% if( session.getAttribute("stock_update_error") != null){ %>
		<div class="card-panel white-text red darken-1"><i class="left material-icons">do_not_disturb</i><%= session.getAttribute("stock_update_error_message") %></div>
	<%
		session.removeAttribute("stock_update_error");
		session.removeAttribute("stock_update_error_message");
	} %>
	
  <form class="col s12" method="POST">
      
      <input type="hidden" name="type" value="update" />
      
      <div class="row">
        <div class="input-field col s12">
          <input id="id" value="<%= stock.getId() %>" type="number" name="id" readonly>
          <label for="ID">ID</label>
        </div>
      </div>
      
      
      <div class="row">
        <div class="input-field col s12">
          <input id="FirstQuantity" value="<%= stock.getFirst_quantity() %>" required aria-required="true" type="number" name="first_quantity" class="validate">
          <label for="FirstQuantity">Quantity Initiale</label>
          <span class="helper-text" data-error="Remplir ce champ" data-success="OK"></span>
        </div>
      </div>
      
      
      <div class="row">
        <div class="input-field col s12">
          <input id="quantity" value="<%= stock.getQuantity() %>" required aria-required="true" type="number" name="quantity" class="validate">
          <label for="quantity">Quantité</label>
          <span class="helper-text" data-error="Remplir ce champ" data-success="OK"></span>
        </div>
      </div>
      
      
      <div class="row">
        <div class="input-field col s12">
		    <select name="product_id">
		      	<% for( Product p : productBusiness.findAll() ){ %>
		      	<option <% if( p.getId() == stock.getProduct().getId() ){ %>selected<% } %> value="<%= p.getId() %>"><%= p.getName() %> | <%= p.getCode() %></option>
		    	<% } %>
		    </select>
		    <label>Selectionner le médicament</label>
	  	</div>
      </div>
      
	<div class="row">
		<button class="btn waves-effect waves-light" type="submit" name="action">Modifier
	    	<i class="material-icons right">mode_edit</i>
	  	</button>
	</div>
  </form>
</div>
      