<!DOCTYPE html>
<html>
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
         
         //POST retrieval
         $usr = $_POST["username"];
         $bulletin_id = $_POST["bulletin_id"];
      ?>
   <head>
      <title>View Bulletin</title>
      <h1><center>View Bulletin</center></h1>
   </head>
   <body>
      <?php
         $db_handle = openDB();
         if(!($result = mysql_query("SELECT * FROM Bulletin WHERE bulletin_id = "
                                    . $bulletin_id)))
         {
            echo "<br>Query Database Failure<br>";
         }
         else
         {
            if($row = mysql_fetch_row($result))
            {
               echo '<table border="0">';
               echo '<tr><td>Bulletin ID:</td><td>' . $row[0] . '</td></tr>';
               echo '<tr><td>Title:</td><td>' . $row[1] . '</td></tr>';
               echo '<tr><td>Description:</td><td>' . $row[3] . '</td></tr>';
               echo '<tr><td>Body Text:</td><td>' . $row[2] . '</td></tr>';
               echo '<tr><td>Contact:</td><td>' . $row[4] . '</td></tr>';
               echo '<tr><td>Category:</td><td>' . $row[5] . '</td></tr>';
               echo '<tr><td>Expiration:</td><td>' . $row[6] . '</td></tr>';
               
               if($result = mysql_query("SELECT File.file_id, extension FROM File_Bulletin, File WHERE bulletin_id = " . $bulletin_id .
                                          " AND File.file_id = File_Bulletin.file_id"))
               {
                  $row = mysql_fetch_row($result);
                  echo '<tr><td>File:</td><td><img src="/~cs130s/public_html/resources/bulletins/' . $row[0] . '.' . $row[1] . '" alt="File: ' . $row[0] . '">';
                  echo '</td></tr>';
               }
               echo'</table>';
            }
            else
            {
               echo "<br>Fetch Row Error<br>";
            }
         }
         
         closeDB($db_handle);
      ?>
      <br><br>
      <form action="accountHome.php" method="post">
         <input type="hidden" name="username" value="<?php echo $usr; ?>">
         <input type="submit" name="submit" value="Account Home">
      </form>
      
   </body>
   
</html>