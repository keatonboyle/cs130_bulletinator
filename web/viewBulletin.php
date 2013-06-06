<?php
   //FUNCTION DEFINITIONS
   function openDB()
   {
      if(!$dbHandle = mysql_connect("localhost", "root", "titanic"))
      {
         $response = array('success' => false);
         echo json_encode($response);
      }
      if(!mysql_select_db("VBB"))
      {
         $response = array('success' => false);
         echo json_encode($response);
      }
      return $dbHandle;
   }

   function closeDB($dbHandle)
   {
      if(!mysql_close($dbHandle))
      {
         $response = array('success' => false);
         echo json_encode($response);
      }
   }
   //END FUNCTION DEFINITONS
   
   //POST retrieval
   $bulletin_id = $_POST["bulletin_id"];

   $db_handle = openDB();
   if(!($result = mysql_query("SELECT * FROM Bulletin WHERE bulletin_id = "
                              . $bulletin_id)))
   {
      $response = array('success' => false);
      echo json_encode($response);
   }
   else
   {
      if($row = mysql_fetch_row($result))
      {
         $response = array('success' => true,
                           'bulletin_id' => $row[0],
                           'title' => $row[1],
                           'shortdesc' => $row[3],
                           'bodytext' => $row[2],
                           'contact' => $row[4],
                           'category' => $row[5],
                           'expiration' => $row[6]);
         
         if($result = mysql_query("SELECT File.file_id, extension FROM File_Bulletin, File WHERE bulletin_id = " . $bulletin_id .
                                    " AND File.file_id = File_Bulletin.file_id"))
         {
            if($row = mysql_fetch_row($result))
            {
               $response['file_id'] = $row[0];
               $response['file_ext'] = $row[1];
            }
         }
         else
         {
            $response['file_id'] = null;
         }
         echo json_encode($response);
      }
      else
      {
         $response = array('success' => false);
         echo json_encode($response);
      }
   }
   
   closeDB($db_handle);
?>