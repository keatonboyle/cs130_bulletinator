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
         
         $usr = $_POST["username"];
      ?>
		<title>Form</title>
		<!--Decide title for whether you're adding a bulletin or editing one-->
	</head>
	<body>
		<form action="insertBulletin.php" method="post">
			Title: <input type="text" name="title"><br>
			Description: <input type="text" name="description"><br>
			Image: <input type="file" name="image"><br>
			Category: 
				<select name="category">
					<option value="job">Job</option>
					<option value="event">Event</option>
					<option value="scholarship">Scholarship</option>
					<option value="propaganda">Propaganda</option>
				</select><br>
			Body text:<br> <textarea name="text" rows="20" cols="50"></textarea><br>
			Expiration date: <input type="date" name="expiration"><br>
			Contact information:<br> <textarea name="contact" rows="5" cols="50"></textarea><br>
			Buildings:<br>
				<select size="8" multiple="multiple" name="buildings">
					<?php 
                  $db_handle = openDB();
                  if(!($result = mysql_query("SELECT name FROM Building")))
                  {
                     echo "<br>Query Error<br>";
                  }
                  else
                  {
                     
                     while($row = mysql_fetch_row($result))
                     {
                        echo '<option value="' . $row[0] . '">' . $row[0] . '</option>';
                     }
                  }
                  closeDB($db_handle);
               ?>
				</select><br>
         <input type="hidden" name="username" value="<?php echo $usr; ?>">
			<input type="Submit" value="submit">
		</form>
	</body>
</html>