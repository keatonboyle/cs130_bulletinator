<?php

   $ERR_INVALID_REQUEST       = array(code => 101, msg => 'INVALID_REQUEST');
   $ERR_INVALID_BLDG_ID       = array(code => 102, msg => 'INVALID_BLDG_ID');
   $ERR_LAT_OUT_OF_BOUNDS     = array(code => 103, msg => 'LAT_OUT_OF_BOUNDS');
   $ERR_LONG_OUT_OF_BOUNDS    = array(code => 104, msg => 'LONG_OUT_OF_BOUNDS');
   $ERR_INVALID_FILE_ID       = array(code => 105, msg => 'INVALID_FILE_ID');
   $ERR_MISSING_PARAM         = array(code => 106, msg => 'MISSING_PARAM');
   $ERR_UNABLE_CONNECT_DB     = array(code => 201, msg => 'UNABLE_CONNECT_DB');
   $ERR_UNABLE_OPEN_DB        = array(code => 202, msg => 'UNABLE_OPEN_DB');
   $ERR_UNABLE_CLOSE_DB       = array(code => 203, msg => 'UNABLE_CLOSE_DB');
   $ERR_DB_QUERY_FAILURE      = array(code => 204, msg => 'DB_QUERY_FAILURE');
   $ERR_EMPTY_DB_RESPONSE     = array(code => 205, msg => 'ERR_EMPTY_DB_RESPONSE');
   $ERR_JSON_ENCODE_FAILURE   = array(code => 301, msg => 'JSON_ENCODE_FAILURE');

   function handleError($error)
   {
      echo "Error ".$error['code']." (".$error['msg']."): ";
      switch ($error['code'])
      {
         case 101:
            die("An invalid request was made.");
            break;
         case 102:
            die("An invalid building ID was given.");
            break;
         case 103:
            die("An invalid latitude value was given.");
            break;
         case 104:
            die("An invalid longitude value was given.");
            break;
         case 105:
            die("An invalid file ID was given.");
            break;
         case 106:
            die("A paramter is missing.");
            break;
         case 201:
            die("Failed to connect to the database server.");
            break;
         case 202:
            die("Failed to open database.");
            break;
         case 203;
            die("Failed to close database.");
            break;
         case 204:
            die("Database query failed.");
            break;
         case 205:
            die("Database query returned the empty set.");
         case 301:
            die("Error encoding JSON.");
            break;
         default:
            die("An unknown error occurred.");
            break;
      }
   }

?>