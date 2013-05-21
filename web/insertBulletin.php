<html>
<?php

   class Bulletin
   {
      public $id;
      public $title;
      public $bodytext;
      public $dscrp;
      public $contact;
      public $imageid;
      public $category;
      public $expiration;

      public function __set($key, $value)
      {
         $this->$key = $value;
      }
   }

   $bulletin = new Bulletin();

   // Connect to DB
   if(!($db = mysql_connect("localhost", "root", "titanic")))
      echo "There was an error connecting to the database.";
   if (!mysql_select_db("VBB"))
      echo "There was an error opening the database.";

   // Generate Bulletin ID
   $query = "SELECT * FROM MaxID";
   if (!($result = mysql_query($query)))
      echo "There was an error querying the database.";

   $row = mysql_fetch_row($result);
   $cur_bid = intval($row[0]);

   $id = $cur_bid;

   $query = "UPDATE MaxID SET bulletinid = ".($cur_bid++);
   if (!($result = mysql_query($query)))
      echo "There was an error updating the bulletin ID.";

   // Generate Image ID
   $currentIMGID = intval($row[1]);

   $imageid = $currentIMGID;

   $query = "UPDATE MaxID SET imageid = ".($currentIMGID++);
   if (!($result = mysql_query($query)))
      echo "There was an error updating the image ID.";

   // Store image on filesystem
   $allowedExts = array("jpeg", "jpg", "png");
   $extension = end(explode(".", $_FILES["image"]["name"]));
   
   if ((($_FILES["image"]["type"] == "image/jpeg")
     || ($_FILES["image"]["type"] == "image/jpg")
     || ($_FILES["image"]["type"] == "image/pjpeg")
     || ($_FILES["image"]["type"] == "image/x-png")
     || ($_FILES["image"]["type"] == "image/png"))
     && ($_FILES["image"]["size"] < 200000)
     && in_array($extension, $allowedExts))
   {
      if ($_FILES["image"]["error"] > 0)
      {
         echo "Return Code: " . $_FILES["image"]["error"] . "<br>";
      }
      else
      {
         echo "Upload: " . $_FILES["image"]["name"] . "<br>";
         echo "Type: " . $_FILES["image"]["type"] . "<br>";
         echo "Size: " . ($_FILES["image"]["size"] / 1024) . " kB<br>";
         echo "Temp file: " . $_FILES["image"]["tmp_name"] . "<br>";

         $filename = $imageid . "." . $extension;

         move_uploaded_file($_FILES["image"]["tmp_name"], "resources/bulletins/" . $filename);
         echo "Stored in: " . "resources/bulletins/" . $filename;
      }
   }
   else
   {
      if ($_FILES["image"]["size"] >= 200000)
      {
         echo "File must be smaller than 200 KB.";
      }
      else
      {
         echo "Invalid file.  Please upload a JPEG or a PNG.";
      }
   }

   // Set values
   $title = $_POST['title'];
   $bodytext = $_POST['text'];
   $dscrp = $_POST['description'];
   $contact = $_POST['contact'];
   $category = $_POST['category'];
   $expiration = $_POST['expiration'];

   // Insert into DB
   $query = "INSERT INTO Bulletin VALUES(".
               $id.",".
           "'".$title."',".
           "'".$bodytext."',".
           "'".$dscrp."',".
           "'".$contact."',".
               $imageid.",".
           "'".$category."',".
           "'".$expiration."',".
               ")";

   if (!($result = mysql_query($query)))
      echo "There was an error inserting into the database.";


?>
</html>