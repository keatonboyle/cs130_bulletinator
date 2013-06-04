<html>
<?php

   include("errors.php");

   function openDB()
   {
      if(!$dbHandle = mysql_connect("localhost", "root", "titanic"))
         handleError($GLOBALS['ERR_UNABLE_CONNECT_DB']);
      if(!mysql_select_db("VBB"))
         handleError($GLOBALS['ERR_UNABLE_OPEN_DB']);
      return $dbHandle;
   }

   function closeDB($dbHandle)
   {
      if(!mysql_close($dbHandle))
         handleError($GLOBALS['ERR_UNABLE_CLOSE_DB']);
   }

   function queryDB($query)
   {
      if(!($result = mysql_query($query)))
         handleError($GLOBALS['ERR_DB_QUERY_FAILURE']);
      return $result;
   }

   function fetchRowAssoc($resource)
   {
      if(!($row = mysql_fetch_assoc($resource)))
         handleError($GLOBALS['ERR_EMPTY_DB_RESPONSE']);
      return $row;
   }

   // Connect to DB
   $dbHandle = openDB();

   // Set values
   $title = $_POST['title'];
   $bodytext = $_POST['text'];
   $dscrp = $_POST['description'];
   $contact = $_POST['contact'];
   $category = $_POST['category'];
   $expiration = $_POST['expiration'];

   // Insert into DB
   $query = "INSERT INTO Bulletin (title, bodytext, shortdesc, contact, category, expiration) VALUES(".
           "'".mysql_real_escape_string($title)."', ".
           "'".mysql_real_escape_string($bodytext)."', ".
           "'".mysql_real_escape_string($dscrp)."', ".
           "'".mysql_real_escape_string($contact)."', ".
           "'".mysql_real_escape_string($category)."', ".
           "'".mysql_real_escape_string($expiration)."'".
               ")";

   queryDB($query);
   $result = queryDB("SELECT LAST_INSERT_ID()");
   $row = mysql_fetch_row($result);
   $bulletin_id = $row[0];
   for($ii = 0; $ii < sizeof($_POST['buildings']); $ii++)
   {
      queryDB("INSERT INTO Bulletin_Building VALUES(". $bulletin_id . ", " . $_POST['buildings'][$ii] . ")");
   }

   // Store image on filesystem
   $allowedExts = array("jpeg", "jpg", "png");
   $extension = end(explode(".", $_FILES["image"]["name"]));
   
   if (isset($_FILES['image']))
   {
      if ((($_FILES["image"]["type"] == "image/jpeg")
        || ($_FILES["image"]["type"] == "image/jpg")
        || ($_FILES["image"]["type"] == "image/pjpeg")
        || ($_FILES["image"]["type"] == "image/x-png")
        || ($_FILES["image"]["type"] == "image/png"))
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

            queryDB("INSERT INTO File (extension) VALUES('" . $extension . "')");
            $result = queryDB("SELECT LAST_INSERT_ID()");
            $row = mysql_fetch_row($result);
            $file_id = $row[0];

            queryDB("INSERT INTO File_Bulletin VALUES(" . $file_id . ", '" . $bulletin_id . "')");

            $filename = $file_id . "." . $extension;

            move_uploaded_file($_FILES["image"]["tmp_name"], "resources/bulletins/" . $filename);
            echo "Stored in: " . "resources/bulletins/" . $filename;
         }
      }
      else
      {
         echo "Invalid file.";
      }
   }

   queryDB("INSERT INTO Bulletin_Creator VALUES(" . $bulletin_id . ", '" . $_POST['username'] . "')");

   closeDB($dbHandle);
?>
</html>
