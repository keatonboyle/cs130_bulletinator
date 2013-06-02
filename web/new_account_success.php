<!DOCTYPE html>
<html>
   <head>
      <title>Create user</title>
   </head>
   <body>
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
               echo 'Success. Click <a href="home.html">here</a> to login to your account.';
            }
            else
            {
               echo 'Username already exists. Click <a href="newAccount.html">here</a> to try again.';
            }
            
            // Close the DB
            closeDB($dbHandle);
         }
         else
         {
            echo "Password mismatch.";
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
   </body>
</html>