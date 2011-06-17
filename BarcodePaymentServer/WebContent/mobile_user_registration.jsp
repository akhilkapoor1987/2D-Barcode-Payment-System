<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Member</title>
</head>
<body>

	<form method="POST" action="CreateMember">
		<table border="1">
			<thead>
				<tr>
					<th>Create a Member</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>MemberType (enter "Merchant" or "Mobile"):</td>
					<td><input type="text" name="memberType" value="" />
					</td>
				</tr>
				<tr>
					<td>First Name:</td>
					<td><input type="text" name="FirstName" value="" />
					</td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><input type="text" name="LastName" value="" />
					</td>
				</tr>
				<tr>
					<td>Address:</td>
					<td><input type="text" name="address" value="" />
					</td>
				</tr>
				<tr>
					<td>City:</td>
					<td><input type="text" name="city" value="" />
					</td>
				</tr>
				<tr>
					<td>State:</td>
					<td><input type="text" name="state" value="" />
					</td>
				</tr>
				<tr>
					<td>Zip:</td>
					<td><input type="text" name="zip" value="" />
					</td>
				</tr>
				<tr>
					<td>Login:</td>
					<td><input type="text" name="login" value="" />
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" value="" />
					</td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" value="" />
					</td>
				</tr>



				<td><input type="submit" value="Submit" />
				</td>
				</tr>
			</tbody>
		</table>

	</form>


</body>
</html>