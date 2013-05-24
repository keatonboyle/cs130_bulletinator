package com.keaton.Keaton_Bulletinator;

import org.json.*;
import java.util.ArrayList;
import java.util.List;

public class AllBuildingsResponse extends ServerResponse
{
   public AllBuildingsResponse(String raw)
   {
      super(raw);

      buildings = new ArrayList<Building>();

      JSONObject json = null;
      try
      {
         json = new JSONObject(raw);

         type = json.getString("type");

         JSONArray bldArray = json.getJSONArray("buildings");

         for (int ii = 0; ii < bldArray.length(); ii++)
         {
            buildings.add(new Building(bldArray.getJSONObject(ii)));
         }
      }
      catch (JSONException e)
      {
         type = "error";
         buildings = null;
      }
   }

   public List<Building> buildings;

}
