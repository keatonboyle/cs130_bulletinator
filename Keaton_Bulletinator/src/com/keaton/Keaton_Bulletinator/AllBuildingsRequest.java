package com.keaton.Keaton_Bulletinator;

public class AllBuildingsRequest extends ServerRequest 
{
   public AllBuildingsRequest(CallbackListener<ServerResponse> mainThread, String baseurl)
   {
      super(mainThread, baseurl + "?type=all_buildings");
   } 

   public void callback(String result)
   {
      AllBuildingsResponse abr = new AllBuildingsResponse(result);
      AppData.update(abr);
      mainThread.callback(abr);
   }
}
