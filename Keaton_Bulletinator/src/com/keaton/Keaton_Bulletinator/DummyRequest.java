package com.keaton.Keaton_Bulletinator;

public class DummyRequest extends ServerRequest 
{
   public DummyRequest(CallbackListener<ServerResponse> mainThread, String baseurl)
   {
      super(mainThread, baseurl + "?type=dummy");
   } 

   public void callback(String result)
   {
      DummyResponse dr = new DummyResponse(result);
      AppData.update(dr);
      mainThread.callback(dr);
   }
}
