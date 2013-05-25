package com.keaton.Keaton_Bulletinator;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import org.json.*;

public class Building {
   public Building(int id, String n, Set<Integer> b) {
       bldid = id;
       btnids = b;
       name = n;
   }


   public String getName() {
       return name;
   }

   public int getID() {
       return bldid;
   }

   public Set<Integer> getBtnIds() {
       return btnids;
   }

   private String name;
   private int bldid;
   private Set<Integer> btnids;
   

}
