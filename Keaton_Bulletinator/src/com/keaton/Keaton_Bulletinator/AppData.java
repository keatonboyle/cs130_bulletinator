package com.keaton.Keaton_Bulletinator;

import java.util.*;
import android.widget.Toast;
import android.content.Context;

public class AppData
{
   private static AppData instance = null;

   protected AppData() 
   {
      bulletins = new HashMap<Integer,Bulletin>();
      buildings = new HashMap<Integer,Building>();
   }


   public static AppData getInstance()
   {
      if (instance == null)
      {
         instance = new AppData();
      }
      return instance;
   }

   public static AppData update(DummyResponse dr)
   {
      instance.dummy = dr.title;
      return instance;
   }

   public static AppData update(BuildingResponse bldr)
   {
      if (bldr.bld != null)
      {
         instance.buildings.put(bldr.bld.getID(), bldr.bld);

         instance.bulletins.putAll(bldr.bulletins);
      }

      return instance;
   }
   
   public static AppData update(AllBuildingsResponse bldr)
   {
      for (Map.Entry<Integer,Building> bldEntry : bldr.buildings.entrySet())
      {
         if (!instance.buildings.containsKey(bldEntry.getKey()))
         {
            instance.buildings.put(bldEntry.getKey(), bldEntry.getValue());
         }
      }

      return instance;
   }

   public static AppData update(BinResponse br)
   {
      return instance;
   }

   public static AppData update(Building bld)
   {
      return instance;
   }

   /* Accessor functions */
   public static Bulletin getBulletin(int btnid)
   {
      return instance.bulletins.get(btnid);
   }
      
   public static Building getBuilding(int bldid)
   {
      return instance.buildings.get(bldid);
   }
   
   public static List<Bulletin> getBulletinsIn(Building bld)
   {
      List<Bulletin> btnList = new ArrayList<Bulletin>();

      for (Integer ii : bld.getBtnIds())
      {
         Bulletin btn = instance.bulletins.get(ii);
         if (btn != null)
         {
            btnList.add(btn);
         }
      }
         
      return btnList;
   }

   public static List<Bulletin> getBulletinsIn(int bldid)
   {
      //TODO: Check if bulletins are missing
      Building bld = instance.buildings.get(bldid);

      if (bld == null) return null;

      return getBulletinsIn(bld);
   }

   public static String getSummaryString()
   {
      String running = "";

      for (Building bld : buildings.values())
      {
         running += (bld.getName() + "\n");
         for (Bulletin btn : instance.getBulletinsIn(bld))
         {
            running += ("   [" + btn.getBulletinId() + "] " +
                        btn.getTitle() + "\n");
         }
      }

      return running;
   }

   private static String dummy;
   private static double lat;
   private static double lon;
   private static Rectangle<Double> bound;
   private static Map<Integer,Bulletin> bulletins;
   private static Map<Integer,Building> buildings;
}

