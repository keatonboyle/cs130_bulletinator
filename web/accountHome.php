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
		<title>VBB: User Home</title>
      <link rel="stylesheet" type="text/css" href="styles.css">
      <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	</head>
	<body>
      <header>
         <h1>
            <center>Welcome 
               <?php
                  $usr = $_POST["username"];
                  echo $usr;
               ?>
            </center>
         </h1>
         <h2>
            <center>Your bulletins</center>
         </h2>
      <header>
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
         
         $db_handle = openDB();
         
         if(!($result = mysql_query("SELECT title, shortdesc, Bulletin.bulletin_id FROM Bulletin, Bulletin_Creator WHERE username = '" . $usr .
                                    "' AND Bulletin_Creator.bulletin_id = Bulletin.bulletin_id")))
         {
            echo "<br>Bulletin Display Error<br>";
         }
         else
         {
            echo "<div class='list_display'>";
            while($row = mysql_fetch_row($result))
            {
               echo '<fieldset><legend>' . $row[0] . '</legend>';
               echo '<form action="viewBulletin.php" method="post">';
               echo '<table border="0">';
               echo '<tr><td>Title:</td><td>' . $row[0] . '</td></tr>';
               echo '<tr><td>Description:</td><td>' . $row[1] . '</td></tr>';
               echo '</table>';
               echo '<input type="hidden" name="username" value="' . $usr . '">';
               echo '<input type="hidden" name="bulletin_id" value="' . $row[2] . '">';
               echo '<input type="submit" name="submit" value="View Bulletin">';
               echo '</form></fieldset><br>';
            }
            echo "</div>";
            echo "<br>";
            
         }
         
         closeDB($db_handle);
         
      ?>

      <div class='navigation'>
		<form action="/~cs130s">
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
      </div>
		<!--Put all the bulletins here-->
		<!--We somehow need to be able to split the bulletins into multiple pages if there are too many.-->

	</body>
   <script src="/~cs130s/jsFunctions.js"></script>
</html>