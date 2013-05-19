/*
package com.keaton.Keaton_Bulletinator;

public class GPSRequest extends ServerRequest 
{
   public GPSRequest(CallbackListener<ServerResponse> mainThread, String baseurl, double lat, double lon)
   {
      super(mainThread, baseurl + "?type=gps_update&lat=" + Double.toString(lat) + "&long=" + Double.toString(lon));
      this.lat = lat;
      this.lon = lon;
   } 

   public void callback(String result)
   {
      uiThread.callback(new DummyResponse(result));
   }

   private double lat;
   private double lon;
}
*/
