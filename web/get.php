<?php

   error_reporting(E_ERROR | E_WARNING | E_PARSE);

   include("errors.php");

   // Open the DB
   $dbHandle = openDB();

   // Handle the request
   $requestType = $_GET['type'];

   switch ($requestType)
   {
      case "everything":
         getEverything();
         break;
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

   function getEverything()
   {
      $responseType = 'EVERYTHING_RESPONSE';
      $result = queryDB("SELECT * FROM Building");
      $buildingArr = array();
      
      while($row = mysql_fetch_row($result))
      {
         $result2 = queryDB("SELECT bulletin_id FROM Bulletin_Building WHERE building_id = " . $row[0]);

         $bulletinIDs = array();
         while($row2 = mysql_fetch_row($result2))
         {
            array_push($bulletinIDs, $row2[0]);
         }

         $building = array('bldid' => $row[0],
                           'name' => $row[1],
                           'bulletins' => $bulletinIDs);
         array_push($buildingArr, $building);
      }

      $result = queryDB("SELECT * FROM Bulletin");
      $bulletinArr = array();
      while($row = mysql_fetch_row($result))
      {
         $result2 = queryDB("SELECT file_id FROM File_Bulletin WHERE bulletin_id = " . $row[0]);
         $row2 = mysql_fetch_assoc($result2);
         if (isset($row2['file_id']))
            $fid = $row2['file_id'];
         else
            $fid = null;
         
         //only fills in one bulletin
         $bulletin = array('btnid' => $row[0],
                           'title' => $row[1],
                           'bodytext' => $row[2],
                           'shortdesc' => $row[3],
                           'contact' => $row[4],
                           'category' => $row[5],
                           'fid' => $fid);
         array_push($bulletinArr, $bulletin);
      }

      $arrayToHash = array('type' => $responseType,
                           'buildings' => $buildingArr,
                           'bulletins' => $bulletinArr);
      $hash = md5(serialize($arrayToHash));

      $everythingResponse = array('type' => $responseType,
                                   'hash' => $hash,
                                   'buildings' => $buildingArr,
                                   'bulletins' => $bulletinArr);
      sendResponse($everythingResponse);
   }

   function gpsUpdate($lat, $long)
   {
      if(!isset($lat) || !isset($long))
         handleError($GLOBALS['ERR_MISSING_PARAM']);
      if($lat < -90 || $lat > 90)
         handleError($GLOBALS['ERR_LAT_OUT_OF_BOUNDS']);
      if($long < -180 || $long > 180)
         handleError($GLOBALS['ERR_LONG_OUT_OF_BOUNDS']);

      $responseType = 'GPS_RESPONSE';

      // The current building
      $result = queryDB("SELECT rectangle_id FROM Rectangle " . 
                        "WHERE north >= " . $lat . " AND " .
                              "south <= " . $lat . " AND " . 
                              "west <= " . $long . " AND " .
                              "east >= " . $long);
      $row = mysql_fetch_assoc($result);
      if (!isset($row['rectangle_id']))
         $curbuilds = null;
      else
      {
         $cur_bldid = $row['rectangle_id'];

         $result = queryDB("SELECT name FROM Building WHERE building_id = " . $cur_bldid);
         $row = fetchRowAssoc($result);
         $name = $row['name'];

         $result = queryDB("SELECT Bulletin.bulletin_id, title, bodytext, shortdesc, ".
                                   "contact, category ".
                           "FROM Bulletin, Bulletin_Building ".
                           "WHERE building_id = " . $cur_bldid . " AND Bulletin.bulletin_id = Bulletin_Building.bulletin_id");
         
         $bulletinArr = array();
         while($row = mysql_fetch_row($result))
         {
            $result2 = queryDB("SELECT file_id FROM File_Bulletin WHERE bulletin_id = " . $row[0]);
            $row2 = mysql_fetch_assoc($result2);
            if (isset($row2['file_id']))
               $fid = $row2['file_id'];
            else
               $fid = null;
            
            //only fills in one bulletin
            $bulletin = array('btnid' => $row[0],
                              'title' => $row[1],
                              'bodytext' => $row[2],
                              'shortdesc' => $row[3],
                              'contact' => $row[4],
                              'category' => $row[5],
                              'fid' => $fid);
            array_push($bulletinArr, $bulletin);
         }

         $curbuilds = array('bldid' => $cur_bldid,
                         'name' => $name,
                         'bulletins' => $bulletinArr);
      }

      // nearby buildings
      $nearbuilds = array();
      $newlats = array(($lat - 0.01), ($lat + 0.01));
      $newlongs = array(($long - 0.01), ($long + 0.01));
      $result = queryDB("SELECT rectangle_id FROM Rectangle " . 
                        "WHERE (north >= " . $newlats[0] . " AND " .
                              "south <= " . $newlats[0] . " AND " . 
                              "west <= " . $newlongs[0] . " AND " .
                              "east >= " . $newlongs[0] . ")" . 
                              " OR " . 
                              "(north >= " . $newlats[0] . " AND " .
                              "south <= " . $newlats[0] . " AND " . 
                              "west <= " . $newlongs[1] . " AND " .
                              "east >= " . $newlongs[1] . ")" . 
                              " OR " . 
                              "(north >= " . $newlats[1] . " AND " .
                              "south <= " . $newlats[1] . " AND " . 
                              "west <= " . $newlongs[0] . " AND " .
                              "east >= " . $newlongs[0] . ")" . 
                              " OR " . 
                              "(north >= " . $newlats[1] . " AND " .
                              "south <= " . $newlats[1] . " AND " . 
                              "west <= " . $newlongs[1] . " AND " .
                              "east >= " . $newlongs[1] . ")");
      while($row = mysql_fetch_row($result))
      {
         $result2 = queryDB("SELECT name FROM Building WHERE building_id = " . $row[0]);
         $row2 = mysql_fetch_row($result2);
         $building = array('bldid' => $row[0],
                           'name' => $row2[0]);
         array_push($nearbuilds, $building);
      }

      // rectangle
      $rect = array('north' => $lat,
                    'south' => $lat,
                    'east' => $long,
                    'west' => $long);

      // hash & send
      $arrayToHash = array('type' => $responseType,
                           'curbuild' => $curbuilds,
                           'nearbuild' => $nearbuilds,
                           'rect' => $rect);
      $hash = md5(serialize($arrayToHash));

      $gpsResponse = array('type' => $responseType,
                           'hash' => $hash,
                           'curbuild' => $curbuilds,
                           'nearbuild' => $nearbuilds,
                           'rect' => $rect);
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
      $result = queryDB("SELECT name FROM Building WHERE building_id = " . $bldid);
      
      if($row = mysql_fetch_row($result))
      {
         $name = $row[0];
      }
      else
      {
         handleError($GLOBALS['ERR_EMPTY_DB_RESPONSE']);
      }
      
      $result = queryDB("SELECT Bulletin.bulletin_id, title, bodytext, shortdesc, ".
                                "contact, category ".
                        "FROM Bulletin, Bulletin_Building ".
                        "WHERE building_id = " . $bldid . " AND Bulletin.bulletin_id = Bulletin_Building.bulletin_id");
      
      $bulletinArr = array();
      while($row = mysql_fetch_row($result))
      {
         //query
         $result2 = queryDB("SELECT file_id FROM File_Bulletin WHERE bulletin_id = " . $row[0]);
         $row2 = mysql_fetch_assoc($result2);
         if (isset($row2['file_id']))
            $fid = $row2['file_id'];
         else
            $fid = null;
         
         //only fills in one bulletin
         $bulletin = array('btnid' => $row[0],
                           'title' => $row[1],
                           'bodytext' => $row[2],
                           'shortdesc' => $row[3],
                           'contact' => $row[4],
                           'category' => $row[5],
                           'fid' => $fid);
         array_push($bulletinArr, $bulletin);
      }
      
      $arrayToHash = array('type' => $responseType,
                           'bldid' => $bldid,
                           'name' => $name,
                           'bulletins' => $bulletinArr);
      
      $hash = md5(serialize($arrayToHash));
      $buildingDataResponse = array('type' => $responseType,
                                    'hash' => $hash,
                                    'bldid' => $bldid,
                                    'name' => $name,
                                    'bulletins' => $bulletinArr);
      sendResponse($buildingDataResponse);
   }

   function getAllBuildings()
   {
      $responseType = 'ALL_BUILDINGS_RESPONSE';
      $result = queryDB("SELECT * FROM Building");
      $buildingArr = array();
      
      while($row = mysql_fetch_row($result))
      {
         $building = array('bldid' => $row[0],
                           'name' => $row[1]);
         array_push($buildingArr, $building);
      }

      $hash = md5(serialize($buildingArr));
      $allBuildingResponse = array('type' => $responseType,
                                   'hash' => $hash,
                                   'buildings' => $buildingArr);
      sendResponse($allBuildingResponse);
   }

   function getFile($fid)
   {
      if(!isset($fid))
         handleError($GLOBALS['ERR_MISSING_PARAM']);
      if($fid < 0 || !(intval($fid)))
         handleError($GLOBALS['ERR_INVALID_FILE_ID']);

      $result = queryDB("SELECT extension FROM File WHERE file_id=".$fid);
      $row = fetchRowAssoc($result);
      $ext = $row['extension'];

      header("Location: ./resources/bulletins/".$fid.".".$ext);
   }

   function dummyGet()
   {
      $responseType = 'DUMMY_RESPONSE';

      $result = queryDB("SELECT * FROM Bulletin LIMIT 1");
      $payload = fetchRowAssoc($result);
      $dummyResponse = array('type' => $responseType,
                             'payload' => $payload);

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