<%@page import="com.pharmacy.entities.Product"%>
<% Product p = (Product)request.getAttribute("product_modal"); %>
<!-- Modal Structure -->
<div id="addBill<%= p.getId() %>" class="modal modal-fixed-footer valign-wrapper">
  <div class="modal-content" id="modal-content<%= p.getId() %>">
    <blockquote  style="border-left-color: #558b2f !important"><h5><%= p.getName() %></h5></blockquote>
	<div class="row">
	
	<div class="row center-align" id="loader<%= p.getId() %>" style="display: none;">
		<div class="preloader-wrapper big active">
		    <div class="spinner-layer spinner-blue-only">
		      <div class="circle-clipper left">
		        <div class="circle"></div>
		      </div><div class="gap-patch">
		        <div class="circle"></div>
		      </div><div class="circle-clipper right">
		        <div class="circle"></div>
		      </div>
		    </div>
		  </div>
  </div>
  
  
  
	    <form method="POST" product="<%= p.getId() %>" url="<%= request.getContextPath() %>/bill" class="col s12 addbill_form_container">
	    <input type="hidden" name="type" value="add" />
	    <input type="hidden" name="product" value="<%= p.getId() %>" />
	      <div class="row">
	        <div class="input-field inline col s12">
	          <i class="material-icons prefix">mode_edit</i>
	          <input id="inputquantity<%= p.getId() %>" type="number" name="quantity" class="validate" required aria-required="true" />
	          <label for="icon_prefix2">Quantité</label>
	          <span class="helper-text" data-error="Quantité invalide" data-success="Ok"></span>
	        </div>
	      </div>
	      <div class="row">
	        <div class="input-field col s12 center-align">
		      <button class="waves-effect waves-light btn-small light-green darken-3" type="submit" id="subformbill<%= p.getId() %>" name="action">Valider la commande
			    <i class="material-icons right">send</i>
			  </button>
		  	</div>
		  </div>
    </form>
	</div>
  </div>
  <div class="modal-footer">
    <a href="#!" class="modal-close waves-effect waves-green btn-flat">Fermer</a>
  </div>
</div>