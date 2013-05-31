package com.keaton.Keaton_Bulletinator;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;

public class LocationModule
{
   public LocationModule(FunctionObj<Location> cb, Activity main)
   {
      this.callback = cb;
      this.mainActivity = main;
   }

   public void run()
   {
      LocationManager lm = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE); 
      
      LocationListener ll = new LocationListener() {
         public void onLocationChanged(Location location)
         {
            callback.call(location);
         }
         public void onStatusChanged(String provider, int status, Bundle extras) {}
         public void onProviderEnabled(String provider) {}
         public void onProviderDisabled(String provider) {}
      };
      
      lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
      
      callback.call(lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
     

   }

   private FunctionObj<Location> callback;
   private Activity mainActivity;
}
