<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"
	import=" java.util.*, java.util.Vector,
javax.servlet.http.HttpServletRequest, com.mobilepayment.servlet.ShoppingServlet, 
com.mobilepayment.servlet.CD,java.util.StringTokenizer,java.util.Vector,com.mobilepayment.servlet.ProductDetailsse"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Details</title>
</head>
<body>

	<table border="1">
		<thead>
			<tr>
				<th>Product Details</th>
				<th></th>
			</tr>

		</thead>
		<tbody>
			<%
         String pId = (String)request.getAttribute("CD");
         System.out.println("here are the values:" + pId ); 
        StringTokenizer t = new StringTokenizer(pId,"|");
            String pdId= t.nextToken();
			String pname = t.nextToken();
		 	String pdesc = t.nextToken();
		 	String price = t.nextToken();
      %>

			<tr>
				<td>Product Id</td>
				<td><%= pdId%></td>
			</tr>
			<tr>
				<td>Product Name:</td>
				<td><%= pname %></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><%= pdesc %></td>
			</tr>
			<tr>
				<td>Price:</td>
				<td><%= price %></td>
			</tr>
			<tr>
				<%// } %>



			</tr>
		</tbody>

	</table>
	<%	 String memberId = (String) request.getAttribute("memberId");
 		 System.out.println("memberID from product details jsp page:" + memberId); %>

	<form action="ProductCatalog" method="POST">
		<input type="hidden" name="action" value="CATALOG"> <input
			type="submit" name="Catalog" value="Catalog">
	</form>


</body>
</html>
