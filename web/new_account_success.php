<!DOCTYPE html>
<html>
   <head>
      <title>VBB: Signup</title>
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
                  $pwd = $_POST["password"];
                  $pwdVer = $_POST["passwordVerify"];
                  $email = $_POST["email"];
                  
                  if($pwd == $pwdVer)
                  {
                     // Open the DB
                     $dbHandle = openDB();
                     
                     //search database for existing username
                     if(mysql_query("INSERT INTO Creator VALUES('" . $usr . "', '" . $email . "', '" . $pwd . "')"))
                     {
                        echo '<p>Success. You may now log in.</p>' .
                              '<div id="button-spacing">' .
                                 '<span class="button large blue" onclick="goTo(\'.\')">Log In</span>' .
                              '</div>';
                     }
                     else
                     {
                        echo '<p>Username already exists, please try again.</p>' .
                              '<div id="button-spacing">' .
                                 '<span class="button large blue" onclick="goTo(\'newAccount.php\')">Go Back</span>' .
                              '</div>';
                     }
                     
                     // Close the DB
                     closeDB($dbHandle);
                  }
                  else
                  {
                     echo '<p>Password mismatch, please try again.</p>' .
                              '<div id="button-spacing">' .
                                 '<span class="button large blue" onclick="goTo(\'newAccount.php\')">Go Back</span>' .
                              '</div>';
                  }
                  
                  // ****************************************************************************

                  // FUNCTION DEFINITIONS *******************************************************
                  
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

                     function insertDB($query)
                     {
                        if(!($result = mysql_query($query)))
                           echo "Insert DB failure: username already exists";
                     }
               ?>
            </div>
         </div>
      </main>
   </body>
   <script src="/~cs130s/jsFunctions.js"></script>
</html>