<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true" import="com.mobilepayment.servlet.BuyProduct"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Thank you for shopping</title>
</head>
<body>
	<bold>thanks you for shopping. 'hope to see you soon'</bold>
	<% String memberId = (String)request.getAttribute("memberId"); out.println("the memberId is" + memberId);
request.setAttribute("memberId",memberId);
%>
	<form name="shoppingForm" action="ShoppingServlet" method="POST">

		<input type="hidden" name="action" value="logout"> <input
			type="submit" name="Catalog" value="logout">


		<form name="catalogForm" action="ProductCatalog" method="POST">
			<input type="hidden" name="action" value="CATALOG"> <input
				type="submit" name="Catalog" value="Catalog">
		</form>
</body>
</html>