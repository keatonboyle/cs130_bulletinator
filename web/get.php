<?php

   include("errors.php");

   // Open the DB
   $dbHandle = openDB();

   // Handle the request
   $requestType = $_GET['type'];

   switch ($requestType)
   {
      case "gps_update":
         gpsUpdate($_GET['lat'], $_GET['long']);
         break;
      case "building":
         getBuildingData($_GET['bldid']);
         break;
      case "all_buildings":
         getAllBuildings();
         break;
      case "file":
         getFile($_GET['fid']);
         break;
      case "dummy":
         dummyGet();
         break;
      default:
         handleError($GLOBALS['ERR_INVALID_REQUEST']);
   }

   // Close the DB
   closeDB($dbHandle);

// ****************************************************************************

// FUNCTION DEFINITIONS *******************************************************

   // 
   function gpsUpdate($lat, $long)
   {
      if(!isset($lat) || !isset($long))
         handleError($GLOBALS['ERR_MISSING_PARAM']);
      if($lat < -90 || $lat > 90)
         handleError($GLOBALS['ERR_LAT_OUT_OF_BOUNDS']);
      if($long < -180 || $long > 180)
         handleError($GLOBALS['ERR_LONG_OUT_OF_BOUNDS']);

      $responseType = 'GPS_RESPONSE';

      $curbuilds = array();
      $nearbuilds = array();
      $rect = array();

      $gpsResponse = array(type => $responseType,
                           hash => $hash,
                           curbuild => $curbuilds,
                           nearbuild => $nearbuilds,
                           rect => $rect);
      sendResponse($gpsResponse);
   }

   function getBuildingData($bldid)
   {
      if(!isset($bldid))
         handleError($GLOBALS['ERR_MISSING_PARAM']);
      if($bldid <= 0 || !(intval($bldid)))
         handleError($GLOBALS['ERR_INVALID_BLDG_ID']);

      $responseType = 'BUILDING_RESPONSE';

      $result = queryDB("SELECT * FROM Building WHERE bid = " . $bldid);

      // generate array according to spec
   }

   function getAllBuildings()
   {
      $responseType = 'ALL_BUILDINGS_RESPONSE';
   }

   function getFile($fid)
   {
      if(!isset($fid))
         handleError($GLOBALS['ERR_MISSING_PARAM']);
      if($fid < 0 || !(intval($fid)))
         handleError($GLOBALS['ERR_INVALID_FILE_ID']);

      $result = queryDB("SELECT ext FROM File WHERE fid=".$fid);
      $row = fetchRowAssoc($result);
      $ext = $row['ext'];

      header("Location: ./resources/bulletins/".$fid.".".$ext);
   }

   function dummyGet()
   {
      $responseType = 'DUMMY_RESPONSE';

      $result = queryDB("SELECT * FROM Bulletin LIMIT 1");
      $payload = fetchRowAssoc($result);
      $dummyResponse = array(type => $responseType,
                             payload => $payload);

      sendResponse($dummyResponse);
   }

   function sendResponse($response)
   {
      if(!($json = json_encode($response)))
         handleError($GLOBALS['ERR_JSON_ENCODE_FAILURE']);
      echo $json;
   }

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
?>