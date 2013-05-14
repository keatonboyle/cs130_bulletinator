package com.keaton.Keaton_Bulletinator;
import org.json.*;

public class DummyResponse
{
   public DummyResponse(Object raw)
   {
      JSONObject json = null;
      try
      {
         json = new JSONObject((String) raw);

         mType = json.getString("type");
         mTitle = json.getJSONObject("payload").getString("title");
      }
      catch (JSONException e)
      {
         mType = "problem";
         mTitle = "problem";
      }
   } 

   public String mType;
   public String mTitle;

}
