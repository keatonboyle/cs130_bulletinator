<?php

   $INVALID_REQUEST_TYPE = -1001;
   $CONNECT_DB_FAIURE    = -1002;
   $OPEN_DB_FAILURE      = -1003;
   $CLOSE_DB_FAILURE     = -1004;
   $DB_QUERY_ERROR       = -1005;
   $JSON_ENCODE_ERROR    = -1006;

   function handleError($errCode)
   {
      echo "Error ".$errCode.": ";
      switch ($errCode)
      {
         case -1001:
            die("An invalid request was made.");
            break;
         case -1002:
            die("Failed to connect to the database server.");
            break;
         case -1003:
            die("Failed to open database.");
            break;
         case -1004;
            die("Failed to close database.");
            break;
         case -1005:
            die("Database query failed or returned the empty set.");
            break;
         case -1006:
            die("Error encoding JSON.");
            break;
         default:
            die("An unknown error occurred.");
            break;
      }
   }

?>