<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Api</title>
</head>
<body>

	<center>
		<table border=1 cellpadding=5>
			<tr>
				<th>User Id</th>
				<th>"${userId}"</th>
			</tr>
			<tr>
				<th>User Name</th>
				<th>"${userName}"</th>
			</tr>
			<tr>
				<th>Name</th>
				<th>"${fullName}"</th>
			</tr>
			<tr>
				<th>Profile Picture</th>
				<th><img src="${profilePic}" height="42" width="42"></th>
			</tr>

		</table>
	</center>
</body>
</html>