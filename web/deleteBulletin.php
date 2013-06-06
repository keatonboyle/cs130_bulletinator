<!doctype html>
<html>
   <head>
      <title>VBB: Delete Bulletin</title>
      <link rel="stylesheet" type="text/css" href="styles.css">
      <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
   </head>
   <body>
      <header>
         <div class="wrap">
            <center id="logo">Bulletinator</center>
         </div>
      </header>
      <main>
         <div class="wrap">
            <div class="block-center">
               <?php
                  $usr = $_POST["username"]; 
                  $bulletin_id = $_POST["bulletin_id"];
                  
                  $db_handle = openDB();
                  if(!mysql_query("DELETE FROM Bulletin WHERE bulletin_id=" . $bulletin_id))
                     echo '<p>There was an error deleting the bulletin.</p>';
                  if(!mysql_query("DELETE FROM Bulletin_Building WHERE bulletin_id=" . $bulletin_id))
                     echo '<p>There was an error deleting the bulletin.</p>';
                  if(!mysql_query("DELETE FROM Bulletin_Creator WHERE bulletin_id=" . $bulletin_id))
                     echo '<p>There was an error deleting the bulletin.</p>';
                  if(!($result = mysql_query("SELECT * FROM File_Bulletin WHERE bulletin_id=" . $bulletin_id)))
                     echo '<p>There was an error deleting the bulletin.</p>';
                  else if ($row = mysql_fetch_row($result)) 
                  {
                     if(!mysql_query("DELETE FROM File WHERE file_id=" . $row[0]))
                        echo '<p>There was an error deleting the bulletin.</p>';
                     if(!mysql_query("DELETE FROM File_Bulletin WHERE bulletin_id=" . $bulletin_id))
                        echo '<p>There was an error deleting the bulletin.</p>';
                     echo '<p>The bulletin was deleted.</p>';
                  }
                  else
                  {
                     echo '<p>The bulletin was deleted.</p>';
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

                  echo '<div id="button-spacing">' . 
                           '<span class="button large blue" onclick="submitForm()">Go Back</span>' . 
                           '<form class="nodisp" action="accountHome.php" method="post">' . 
                              '<input type="hidden" name="username" value="' . $usr . '">' .
                           '</form>' .
                        '</div>';
               ?>
            </div>
         </div>
      </main> 
      <script src="/~cs130s/jsFunctions.js"></script>
   </body>
</html>