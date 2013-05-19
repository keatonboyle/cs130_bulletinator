package com.keaton.Keaton_Bulletinator;

import org.json.*;

public class BuildingResponse extends ServerResponse
{
   public BuildingResponse(String raw)
   {
      super(raw);
      JSONObject json = null;
      try
      {
         json = new JSONObject(raw);

         type = json.getString("type");

         bld = new Building(json);
      }
      catch (JSONException e)
      {
         type = "error";
         bld = null;
      }
   }

   public Building bld;

}

