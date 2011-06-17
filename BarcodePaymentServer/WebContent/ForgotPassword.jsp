<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password Form</title>
</head>
<body>

	<form method="POST" action="ForgotPassword">
		<table border="1">
			<thead>
				<tr>
					<th>Forgot Password Form</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Enter Email:</td>
					<td><input type="text" name="email" value="" />
					</td>
				</tr>

				<tr>

					<td><input type="submit" value="Retrieve Password" />
					</td>


				</tr>
			</tbody>
		</table>

	</form>



</body>
</html>