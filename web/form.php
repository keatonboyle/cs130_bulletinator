<!DOCTYPE html>
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="styles.css">
      <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
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
      <header>
         <div class="wrap">
            <center id="logo">Bulletinator</center>
         </div>
      </header>
      <main>
         <div class="wrap">
            <div class="block-center">
               <form action="insertBulletin.php" method="post" enctype="multipart/form-data">
                  <div class="label required" for="title">Title</div>
                     <input type="text" name="title">
                  <div class="label required" for="description">Description</div>
                     <input type="text" name="description">
                  <div class="label required" for="image">Image</div> 
                    <input type="file" name="image">
                  <div class="label required" for="category">Category</div> 
                     <select name="category">
                        <option value="job">Job</option>
                        <option value="event">Event</option>
                        <option value="scholarship">Scholarship</option>
                        <option value="propaganda">Propaganda</option>
                     </select><br>
                  <div class="label required" for="text">Body text</div> 
                     <textarea name="text" rows="20" cols="50"></textarea>
                  <div class="label required" for="expiration">Expiration date</div>
                     <input type="date" name="expiration">
                  <div class="label required" for="contact">Contact information</div>
                     <textarea name="contact" rows="5" cols="50"></textarea>
                  <div class="label required" for="buildings[]">Buildings</div>
                     <select size="8" multiple="multiple" name="buildings[]">
                        <?php 
                           $db_handle = openDB();
                           if(!($result = mysql_query("SELECT building_id, name FROM Building")))
                           {
                              echo "<br>Query Error<br>";
                           }
                           else
                           {
                              
                              while($row = mysql_fetch_row($result))
                              {
                                 echo '<option value="' . $row[0] . '">' . $row[1] . '</option>';
                              }
                           }
                           closeDB($db_handle);
                        ?>
                     </select>
                  <input type="hidden" name="username" value="<?php echo $usr; ?>">
                  <input type="Submit" value="submit">
               </form>
            </div>
         </div>
         <div id="button-spacing">
            <form action="accountHome.php" method="post">
               <input type="hidden" name="username" value="<?php echo $usr; ?>">
               <input type="submit" name="submit" value="Account Home">
            </form>
         </div>
      </main>
   </body>
</html>
