<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Form</title>
</head>
<body>

	<form method="POST" action="LoginServlet">
		<table border="1">
			<thead>
				<tr>
					<th>Login Form</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>LoginID:</td>
					<td><input type="text" name="login" value="Prabir" />
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" value="Rishi" />
					</td>
				</tr>


				<tr>

					<td><input type="submit" name="submit" value="Login" />
					</td>
					<INPUT TYPE="BUTTON" VALUE="Forgot Password?"
						ONCLICK="window.location.href='/ForgotPassword.jsp'">
					<!-- New HTML tag may be help full in future -->


				</tr>
			</tbody>
		</table>

	</form>



</body>
</html>