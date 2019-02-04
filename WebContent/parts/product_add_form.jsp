<%@page import="com.pharmacy.entities.Category"%>
<%@page import="com.pharmacy.business.ICategory"%>
<%@page import="com.pharmacy.dao.CategoryDAO"%>
<%@page import="com.pharmacy.entities.Product"%>
<%@page import="com.pharmacy.business.IProduct"%>
<%@page import="com.pharmacy.dao.ProductDAO"%>
<%@page import="com.pharmacy.entities.Stock"%>

<%
ICategory categoryBusiness = (CategoryDAO)request.getAttribute("categoryBusiness");
%>

<div class="row" style="margin-top: 20px;">

	<% if( session.getAttribute("product_add_error") != null){ %>
		<div class="card-panel white-text red darken-1"><i class="left material-icons">do_not_disturb</i><%= session.getAttribute("product_add_error_message") %></div>
	<%
		session.removeAttribute("product_add_error");
		session.removeAttribute("product_add_error_message");
	} %>
	
	
  <form class="col s12" method="POST">
      
      <input type="hidden" name="type" value="add" />
      
      <div class="row">
        <div class="input-field col s12">
          <input id="id" type="text" value="#" name="id" readonly>
          <label for="ID">ID</label>
        </div>
      </div>
      
      <div class="row">
        <div class="input-field col s12">
          <input id="code" required aria-required="true" type="text" name="code" class="validate">
          <label for="code">Code</label>
          <span class="helper-text" data-error="Remplir ce champ" data-success="OK"></span>
        </div>
      </div>
      
      
      <div class="row">
        <div class="input-field col s12">
          <input id="name" required aria-required="true" type="text" name="name" class="validate">
          <label for="name">Nom</label>
          <span class="helper-text" data-error="Remplir ce champ" data-success="OK"></span>
        </div>
      </div>
      
      
      <div class="row">
        <div class="input-field col s12">
          <input id="price"  required aria-required="true" type="text" name="price" class="validate">
          <label for="price">Prix</label>
          <span class="helper-text" data-error="Remplir ce champ" data-success="OK"></span>
        </div>
      </div>
      
      
      <div class="row">
        <div class="input-field col s12">
		    <select name="category_id">
		      	<% for( Category category : categoryBusiness.findAll() ){ %>
		      	<option value="<%= category.getId() %>"><%= category.getName() %> | <%= category.getId() %></option>
		    	<% } %>
		    </select>
		    <label>Selectionner la categorie</label>
	  	</div>
      </div>
      
	<div class="row">
		<button class="btn waves-effect waves-light" type="submit" name="action">Ajouter
	    	<i class="material-icons right">send</i>
	  	</button>
	</div>
  </form>
</div>
      