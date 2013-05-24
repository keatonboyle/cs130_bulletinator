package com.keaton.Keaton_Bulletinator;

import java.util.List;
import java.util.ArrayList;
import org.json.*;

public class Building {
   private String name;
   private int bldid;
   // TODO: bltids vs bulletins?
   /*
   private List<Integer> bltids;
   */
   private List<Bulletin> bulletins;

   public Building(int id, String n, List<Bulletin> b) {
       bldid = id;
       bulletins = b;
       name = n;
   }

   public Building(JSONObject json)
   {
      try
      {
         bldid = json.getInt("bldid");
         name  = json.getString("name");

         JSONArray btnArray = json.optJSONArray("bulletins");

         if (btnArray != null)
         {
            bulletins = new ArrayList<Bulletin>();

            for (int ii = 0; ii < btnArray.length(); ii++)
            {
               bulletins.add(new Bulletin(btnArray.getJSONObject(ii)));
            }
         }
         else
         {
            bulletins = null;
         }
      }
      catch (JSONException e)
      {
         bldid = 0;
         name = "error";
      }
   }



   public String getName() {
       return name;
   }

   public int getID() {
       return bldid;
   }

   public List<Bulletin> getBulletins() {
       return bulletins;
   }

   /*
   public List<Integer> getBltIds() {
      return bltids;
   }
   */
}
