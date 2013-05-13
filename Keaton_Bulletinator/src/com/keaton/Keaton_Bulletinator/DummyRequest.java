package com.keaton.Keaton_Bulletinator;

public class DummyRequest extends AJson
{
   public static final String dummyQuery = "?type=dummy";
   public DummyRequest(CallbackListener mainThread, String baseurl)
   {
      super(mainThread, baseurl + dummyQuery);
   } 

   @Override
   protected void onPostExecute(Object result)
   {
      DummyResponse dr = new DummyResponse(result);
      super.onPostExecute(dr);
   }
}
