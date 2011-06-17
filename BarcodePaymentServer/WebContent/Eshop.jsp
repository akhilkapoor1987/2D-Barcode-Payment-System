<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<%@ page session="true"
	import="java.util.*,java.util.ArrayList,java.util.LinkedList,java.util.Iterator,
	com.mobilepayment.servlet.ProductCatalog"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>PayByBarcode</title>
</head>
<body bgcolor="#33CCFF">
	<font face="Times New Roman,Times" size="+3"> Music Without
		Borders </font>
	<hr>
	<p>
	<center>
		<form name="shoppingForm" action="ShoppingServlet" method="POST">

			<b>CD:</b> <select name=CD>
				<%
			ArrayList<LinkedList<String>> data = new ArrayList<LinkedList<String>>(); 
				data = (ArrayList<LinkedList<String>>) request.getAttribute("data");
				 String memberId = (String) request.getAttribute("memberId");
				 
				 System.out.println("Eshop memberID:"+memberId);
				
				session.setAttribute("memberId", memberId);
				//request.setAttribute("memberId", memberId);
					
				for(LinkedList<String> list : data)
				{
				
				while (!list.isEmpty()) {
				%>
				<option>

					<%=list.poll()%>

					<%=list.poll()%>

					<%=list.poll()%>

					<%=list.poll()%>



				</option>
				<%
					}}
				%>
			</select> <b>Quantity: </b><input type="text" name="qty" SIZE="3" value=1>



			<p>






				<!--<input type="hidden" name="action" value="ADD">-->
				<input type="submit" name="Submit1" value="Add to Cart">

				<!--  <input type="hidden" name="action1" value="DETAILS">  -->
				<input type="submit" name="Submit2" value="Product Details">
		</form>

	</center>

	<jsp:include page="Cart.jsp" flush="true" />

</body>
</html>