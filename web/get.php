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
         getBuildingData($_GET['bid']);
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
         handleError($INVALID_REQUEST_TYPE);
   }

   // Close the DB
   closeDB($dbHandle);

// ****************************************************************************

// FUNCTION DEFINITIONS *******************************************************
   function gpsUpdate($lat, $long)
   {
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

   function getBuildingData($bid)
   {
      $responseType = 'BUILDING_RESPONSE';
   }

   function getAllBuildings()
   {
      $responseType = 'ALL_BUILDINGS_RESPONSE';
   }

   function getFile($fid)
   {
      ;
   }

   function dummyGet()
   {
      $responseType = 'DUMMY_RESPONSE';
      $result = queryDB("SELECT * FROM Bulletin");
      $payload = mysql_fetch_row($payload);

      $dummyResponse = array(type => $responseType,
                             payload => $payload);

      sendResponse($dummyResponse);
   }

   function sendResponse($response)
   {
      if(!($json = json_encode($response)))
         handleError($JSON_ENCODE_ERROR);
      echo $json;
   }

   function openDB()
   {
      if(!$dbHandle = mysql_connect("localhost", "root", "titanic"))
         handleError($CONNECT_DB_FAILURE);
      if(!mysql_select_db("VBB"))
         handleError($OPEN_DB_FAILURE);
      return $dbHandle;
   }

   function closeDB($dbHandle)
   {
      if(!mysql_close($dbHandle))
         handleError($CLOSE_DB_FAILURE);
   }

   function queryDB($query)
   {
      if(!($result = mysql_query($query)))
         handleError($DB_QUERY_ERROR);
      return $result;
   }
?>