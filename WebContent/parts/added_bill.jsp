<%@page import="com.pharmacy.entities.Bill"%>
<% Bill bill = (Bill)request.getAttribute("bill"); %>
<!-- Modal Structure -->

<table class="centered striped responsive-table " style="margin-bottom: 100px;">
       <tbody>
	<tr class="z-depth-3">
		<th style="font-weight: bold" class="blue-text">#</th>
		<th>Medicament x Quantité</th>
		<th>Prix</th>
	</tr>
	<tr class="hoverable z-depth-1">
		<td class="blue-text" style="font-weight: bold"><blockquote style="border-left-color: #2196F3 !important"><%= bill.getId() %></blockquote></td>
		<td><%= bill.getProduct().getName() %> x <%= bill.getQuantity() %></td>
		<td><%= bill.getPrice() %> Dhs</td>
	</tr>
	</tbody>
	</table>