  <nav>
    <div class="nav-wrapper light-green darken-1">
    <a href="<%= request.getContextPath() %>" class="brand-logo"><i class="material-icons">local_pharmacy</i>Pharmacie Haytham</a>
      <a href="#" data-target="pharmacy_manager" class="sidenav-trigger"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down">
        <li class="<% if( request.getAttribute("page_url") != null && request.getAttribute("page_url").equals("home") ){ %>active<% } %>"><a href="<%= request.getContextPath() %>"><i class="material-icons left lighten-2">book</i>Medicament</a></li>
        <li class="<% if( request.getAttribute("page_url") != null && request.getAttribute("page_url").equals("bill") ){ %>active<% } %>"><a href="<%= request.getContextPath() %>/bill"><i class="material-icons left lighten-2">list</i>Factures</a></li>
        <li class="<% if( request.getAttribute("page_url") != null && request.getAttribute("page_url").equals("stock") ){ %>active<% } %>"><a href="<%= request.getContextPath() %>/stock"><i class="material-icons left lighten-2">store</i>Stock</a></li>
      </ul>
    </div>
  </nav>

  <ul class="sidenav" id="pharmacy_manager">
        <li class="<% if( request.getAttribute("page_url") != null && request.getAttribute("page_url").equals("products") ){ %>active<% } %>"><a href="<%= request.getContextPath() %>"><i class="material-icons left lighten-2">book</i>Medicament</a></li>
        <li class="<% if( request.getAttribute("page_url") != null && request.getAttribute("page_url").equals("bill") ){ %>active<% } %>"><a href="<%= request.getContextPath() %>/bill"><i class="material-icons left lighten-2">list</i>Factures</a></li>
        <li class="<% if( request.getAttribute("page_url") != null && request.getAttribute("page_url").equals("stock") ){ %>active<% } %>"><a href="<%= request.getContextPath() %>/stock"><i class="material-icons left lighten-2">store</i>Stock</a></li>
  </ul>
  