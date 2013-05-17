<?php

   include("errorswolf.php");

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
      
      //select building name
      $result = queryDB("SELECT name FROM Building WHERE bldid = " . $bldid)
      
      while($row = mysql_fetch_row($result))
      {
         $name = $row[0];
      }
      
      $result = queryDB("SELECT Bulletin.btnid, title, bodytext, shortdesc
                                contact, category
                         FROM Bulletin, BulletinToBuilding
                         WHERE bldid = " . $bldid . " AND Bulletin.btnid = BulletinToBuilding.btnid");
      
      $bulletinArr = array();
      while($row = mysql_fetch_row($result))
      {
         //query
         $result2 = queryDB("SELECT fid FROM FileToBulletin WHERE btnid = " . $row[0]);
         
         //only fills in one bulletin
         $bulletin = array(btnid => $row[0],
                           title => $row[1],
                           bodytext => $row[2],
                           shortdesc => $row[3],
                           contact => $row[4],
                           category => $row[5],
                           fid => $fid);
         array_push($bulletinArr, $bulletin);
      }
      // generate array according to spec
      $buildingDataResponse = array(type => $responseType,
                                    hash => $hash,
                                    bldid => $bldid,
                                    name => $name,
                                    bulletins => $bulletinArr);
      sendResponse($buildingDataResponse);
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
      $result = queryDB("SELECT * FROM Bulletin LIMIT 1");
      $payload = mysql_fetch_assoc($result);
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
?>