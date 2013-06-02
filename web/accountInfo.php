<!DOCTYPE html>
<html>
	<head>
		<title>Account info</title>
		<h1><center>Account info</center></h1>
	</head>
	<body>
      <?php
         $usr = $_POST["username"];
         
         $db_handle = openDB();
         
         if(!($result = mysql_query("SELECT email, password FROM Creator WHERE username = '" . $usr . "'")))
         {
            echo "<br>QUERY ERROR<br>";
         }
         else
         {
            $row = mysql_fetch_row($result);
            $email = $row[0];
            $pwd = $row[1];
         }
         
         closeDB($db_handle);
         
         //FUNCTION DEFINITIONS
         function openDB()
         {
            if(!$dbHandle = mysql_connect("localhost", "root", "titanic"))
               echo "DB connection failure";
            if(!mysql_select_db("VBB"))
               echo "DB open failure";
            return $dbHandle;
         }

         function closeDB($dbHandle)
         {
            if(!mysql_close($dbHandle))
               echo "DB close failure";
         }
      ?>
   
		Username: <?php echo $usr; ?><br>
		E-mail: <?php echo $email; ?><br>
		<br>
		<fieldset>
			<legend>Change password</legend>
			<form action="accountHome.php" method="post">
				<table border="0">
					<tr>
						<td>Current password:</td>
						<td><input type="password" name="currentPassword"></td>
					</tr>
					<tr>
						<td>New Password:</td>
						<td><input type="password" name="newPassword"></td>
					</tr>
					<tr>
						<td>Retype new password:</td>
						<td><input type="password" name="passwordVerify"></td>
					</tr>
				</table>
            <input type="hidden" name="username" value="<?php echo $usr; ?>">
            <input type="submit" name="submit" value="submit">
			</form>
		</fieldset>
      
	</body>
</html>