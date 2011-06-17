<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ page session="true"
	import="java.util.*, java.util.Vector , com.mobilepayment.servlet.CD"%>

<%    @SuppressWarnings("unchecked")
Vector <CD> buylist = (Vector<CD>) session.getAttribute("com.mobilepayment.servlet");
 if (buylist != null && (buylist.size() > 0)) {
%>
<center>
	<table border="0" cellpadding="0" width="100%" bgcolor="#FFFFFF">
		<tr>
			<td><b>pdid</b>
			</td>
			<td><b>Pname</b>
			</td>
			<td><b>Description</b>
			</td>
			<td><b>PRICE</b>
			</td>
			<td><b>QUANTITY</b>
			</td>
			<td></td>
		</tr>
		<%
  for (int index=0; index < buylist.size();index++) {
  CD anOrder = (CD) buylist.elementAt(index);
 %>
		<tr>
			<td><b><%= anOrder.getpdId() %></b>
			</td>
			<td><b><%= anOrder.getpname() %></b>
			</td>
			<td><b><%= anOrder.getpdesc() %></b>
			</td>
			<td><b><%= anOrder.getprice() %></b>
			</td>
			<td><b><%= anOrder.getQuantity() %></b>
			</td>
			<td>
				<form name="deleteForm" action="ShoppingServlet" method="POST">
					<input type="submit" value="Delete"> <input type="hidden"
						name="delindex" value='<%= index %>'> <input type="hidden"
						name="action" value="DELETE">
				</form></td>
		</tr>
		<% } %>
	</table>
	<p>
	<form name="checkoutForm" action="ShoppingServlet" method="POST">
		<input type="hidden" name="action" value="CHECKOUT"> <input
			type="submit" name="Checkout" value="Checkout">
	</form>
	<form name="catalogForm" action="ProductCatalog" method="POST">
		<input type="hidden" name="action" value="CATALOG"> <input
			type="submit" name="Catalog" value="Catalog">
	</form>

</center>
<% } %>