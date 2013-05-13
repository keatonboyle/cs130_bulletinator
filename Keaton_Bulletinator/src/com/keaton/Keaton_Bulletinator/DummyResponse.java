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

         mType = (String) json.get("type");
//         mPayload = (String) json.get("payload");
      }
      catch (JSONException e)
      {
         mType = "problem";
         mPayload = "problem";
      }
   } 

   public String mType;
   public String mPayload;

}
