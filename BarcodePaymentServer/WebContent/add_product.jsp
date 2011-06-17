<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a Product</title>
</head>
<body>

	<form method="POST" action="AddProduct">
		<table border="1">
			<thead>
				<tr>
					<th>Product Details</th>
					<th></th>
				</tr>

			</thead>
			<tbody>


				<tr>
					<td>Name:</td>

					<td><input type="text" name="pname" value="" />
					</td>

				</tr>


				<tr>
					<td>Description:</td>

					<td><input type="text" name="pdesc" value="" />
					</td>
				</tr>

				<tr>
					<td>QTY:</td>
					<td><input type="text" name="qty" value="" />
					</td>
				</tr>
				<tr>
					<td>Price:</td>
					<td><input type="text" name="price" value="" />
					</td>
				</tr>
				<tr>
					<td>MemberId:</td>
					<td><input type="text" name="memberId"
						value="<%= request.getAttribute("memberId").toString()%>" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Submit" />
					</td>
				</tr>
			</tbody>
		</table>


	</form>



</body>
</html>