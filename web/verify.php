<!DOCTYPE html>
<html>
	<head>
		<title>Verify</title>
      <h1><center>Account Verification</center></h1>
   </head>
   
   <body>
      <?php
         $usr = $_POST["username"]; 
         $pwd = $_POST["password"];
         
         $db_handle = openDB();
         $result = mysql_query("SELECT * FROM Creator WHERE username= '" . $usr . "' AND password= '" . $pwd ."'");
         
         if($row = mysql_fetch_row($result))
         {
            echo "You logged in successfully!<br>";
            echo '<form action="accountHome.php" method="post">' .
                  '<input type="hidden" name="username" value="' .
                  $usr . '"><input type="submit" value="Continue to account"></form>';
            
         }
         else
         {
            echo 'Username and Password not found, please <a href="/~cs130s/">try again.</a>'; 
         }
         
         closeDB($db_handle);
         
         //FUNCTION DEFS
         
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
   </body>
   
</html>