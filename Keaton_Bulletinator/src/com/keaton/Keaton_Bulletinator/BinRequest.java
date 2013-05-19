/*
package com.keaton.Keaton_Bulletinator;

public class BinRequest extends ServerRequest 
{
   public BinRequest(CallbackListener<ServerResponse> mainThread, String baseurl, int fid)
   {
      super(mainThread, baseurl + "?type=file&fid=" + Integer.toString(fid));
      this.fid = fid;
   } 

   public void callback(String result)
   {
      uiThread.callback(new DummyResponse(result));
   }
   private int fid;
}
*/
