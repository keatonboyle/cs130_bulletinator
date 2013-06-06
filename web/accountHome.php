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
         <div class="wrap">
            <h1>Welcome, 
               <?php
                  $usr = $_POST["username"];
                  echo $usr;
               ?>
            </h1>
            <h2>Your bulletins</h2>
            <span id="control-buttons">
               <span class="button large green mr10" onclick="submitFormById('newbulletin')">New Bulletin</span>
               <form class="nodisp" action="form.php" method="post" id="newbulletin">
                  <input type="hidden" name="username" value="<?php echo $usr; ?>">
               </form>
               <span class="button large blue mr10" onclick="submitFormById('accountinfo')">Account</span>
               <form class="nodisp" action="accountInfo.php" method="post" id="accountinfo">
                  <input type="hidden" name="username" value="<?php echo $usr ?>">
               </form>
               <span class="button large red" onclick="goTo('.')">Log Out</span>
            </span>
         </div>
      </header>
      <main>
         <div class="wrap">
            <div class="bdr">
               <div class="ib" id="list-pane">
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
                        while($row = mysql_fetch_row($result))
                        {
                           echo "<div class='btn-tab' onclick='selectBtn(this)'>";
                           echo '<div class="btn-title">' . $row[0] . '</div>';
                           echo '<div class="btn-shortdesc">' . $row[1] . '</div>';
                           echo '<input type="hidden" name="username" value="' . $usr . '">';
                           echo '<input type="hidden" name="bulletin_id" value="' . $row[2] . '">';
                           echo '</div>';
                        }                        
                     }
                     
                     closeDB($db_handle);
                  ?>
               </div>
               <div class="ib bdr-l rel" id="btn-pane">
                  <div class="bulletin-wrap v-align">
                     <center>Please select a bulletin on the left.</center>
                  </div>
               </div>
            </div>
         </div>
      </main>
	</body>
   <script src="/~cs130s/jsFunctions.js"></script>
</html>