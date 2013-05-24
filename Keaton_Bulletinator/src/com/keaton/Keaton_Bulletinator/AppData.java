package com.keaton.Keaton_Bulletinator;

import java.util.HashMap;
import java.util.List;

public class AppData
{
   private static AppData instance = null;

   protected AppData() 
   {
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
      return instance;
   }
   
   public static AppData update(AllBuildingsResponse bldr)
   {
      return instance;
   }

   public static AppData update(BinResponse br)
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
   
   public static List<Bulletin> getBulletinsIn(Bulletin b)
   {
      return getBulletinsIn(b.getBulletinId());
   }

   public static List<Bulletin> getBulletinsIn(int bldid)
   {
      //TODO: Check if bulletins are missing
      return instance.buildings.get(bldid).getBulletins();
   }

   private String dummy;
   private double lat;
   private double lon;
   private Rectangle<Double> bound;
   private HashMap<Integer,Bulletin> bulletins;
   private HashMap<Integer,Building> buildings;
}

