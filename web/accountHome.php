<!DOCTYPE html>
<html>
	<head>
      <?php
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
         //END FUNCTION DEFINITONS
      ?>
		<title>User home</title>
      <h1><center>Welcome <?php
         $usr = $_POST["username"];
         echo $usr;
      ?></center></h1>
		<h2><center>Your bulletins</center></h2>
	</head>
	<body>
      <?php
         if(isset($_POST["currentPassword"]) && isset($_POST["newPassword"]) && isset($_POST["passwordVerify"]))
         {
            $currPwd = $_POST["currentPassword"];
            $newPwd = $_POST["newPassword"];
            $verifyPwd = $_POST["passwordVerify"];
            
            $db_handle = openDB();
            
            if(!($result = mysql_query("SELECT password FROM Creator WHERE username = '" . $usr . "'")))
            {
               echo "<br>Query Database Error<br>";
            }
            else
            {
               $row = mysql_fetch_row($result);
               if($row[0] == $currPwd && $newPwd == $verifyPwd)
               {
                  if(!($result = mysql_query("UPDATE Creator SET password = '" .
                                             $newPwd . "' WHERE username = '" . $usr . "'")))
                  {
                     echo "<br>Update Error<br>";
                  }
                  else
                  {
                     echo "<br>Password Changed Successfully<br>";
                  }
               }
               else
               {
                  echo "<br>Password Mismatch<br>";
               }
            }
            
            closeDB($db_handle);
         }
      ?>
		<form action="/~cs130s/">
			<input type="submit" value="Logout">
		</form> <!--Do whatever-->
      <form action="form.php" method="post">
         <input type="hidden" name="username" value="<?php echo $usr; ?>">
         <input type="submit" value="Insert New Bulletin">
      </form>
		<!--Bulletin editing will be an option displayed for each bulletin.-->
      <form action="accountInfo.php" method="post">
         <input type="hidden" name="username" value="<?php echo $usr ?>">
         <input type="submit" value="Account info">
      </form>

		<!--Put all the bulletins here-->
		<!--We somehow need to be able to split the bulletins into multiple pages if there are too many.-->

	</body>
</html>