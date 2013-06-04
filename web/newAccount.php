<!DOCTYPE html>
<html>
	<head>
		<title>Create user</title>
		<h1><center>Create user</center></h1>
	</head>
	<body>
		<form action="new_account_success.php" method="post">
			<table border="0">
				<tr>
					<!--We need to make sure the username is unique.-->
					<td>Username:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>E-mail:</td>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>Retype password:</td>
					<td><input type="password" name="passwordVerify"></td>
				</tr>
				<tr>
					<td><input type="Submit" value="submit"></td>
				</tr>
			</table>
		</form>
	</body>
</html>