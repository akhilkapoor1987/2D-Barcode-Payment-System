<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"
	import="java.util.*, com.mobilepayment.servlet.CD, com.mobilepayment.servlet.ShoppingServlet"%>

<html>
<head>
<title>Music Without Borders Checkout</title>
</head>
<body bgcolor="#33CCFF">
	<font face="Times New Roman,Times" size=+3> PayByBarcode
		Checkout </font>
	<hr>
	<p>
	<center>
		<table border="0" cellpadding="0" width="100%" bgcolor="#FFFFFF">
			<tr>
				<td><b>PDID</b>
				</td>
				<td><b>Pname</b>
				</td>
				<td><b>Pdesc</b>
				</td>
				<td><b>Price</b>
				</td>
				<td><b>QUANTITY</b>
				</td>
				<td></td>
			</tr>
			<%
  Vector buylist = (Vector) session.getAttribute("com.mobilepayment.servlet");
  String amount = (String) request.getAttribute("amount");
  System.out.println("the amount is "+amount);
  session.setAttribute("amount1",amount);
  for (int i=0; i < buylist.size();i++) {
   CD anOrder = (CD) buylist.elementAt(i);
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
				<td><b><%= anOrder.getMemberId() %></b>
				</td>

			</tr>
			<%
  }
 // session.invalidate();
 %>
			<tr>
				<td></td>
				<td></td>
				<td><b>TOTAL</b>
				</td>
				<td><b>$<%= amount %></b>
				</td>
				<td></td>
			</tr>
			<%%>

		</table>
		<p></p>
		<form name="catalogForm" action="ProductCatalog" method="POST">
			<input type="hidden" name="action" value="CATALOG"> <input
				type="submit" name="Catalog" value="Catalog">
		</form>
		<form name="BuyForm" action="BuyProduct" method="POST">
			<input type="hidden" name="action" value="BUY"> <input
				type="submit" name="Buy" value="Buy">
		</form>
	</center>
</body>
</html>