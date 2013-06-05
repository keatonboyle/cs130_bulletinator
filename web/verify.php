<!doctype html>
<html>
   <head>
      <title>VBB: Login</title>
      <link rel="stylesheet" type="text/css" href="styles.css">
      <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
   </head>
   <body>
      <?php
         $usr = $_POST["username"]; 
         $pwd = $_POST["password"];
         
         $db_handle = openDB();
         $result = mysql_query("SELECT * FROM Creator WHERE username= '" . $usr . "' AND password= '" . $pwd ."'");
         
         if($row = mysql_fetch_row($result))
         {
            echo '<form action="accountHome.php" method="post">' .
                     '<input id="form" type="hidden" name="username" value="' . $usr . '">' . 
                  '</form>';
            echo '<script>' . 
                     '$("form").submit();' . 
                  '</script>';
            
         }
         else
         {
            echo '<header>' . 
                     '<div class="wrap">' . 
                        '<center id="logo">Bulletinator</center>' .
                     '</div>' . 
                  '</header>' .
                  '<main>' .
                     '<div class="wrap">' .
                        '<div class="block-center">' .
                           '<p>Username and password not found, please try again.</p>' .
                           '<div id="button-spacing">' .
                              '<span class="button large blue" onclick="goTo(\'.\')">Go Back</span>' .
                           '</div>' .
                        '</div>' .
                     '</div>' .
                  '</main>'; 
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
      <script src="/~cs130s/jsFunctions.js"></script>
   </body>
</html>