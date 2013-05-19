package com.keaton.Keaton_Bulletinator;

public abstract class ServerRequest implements CallbackListener<String>
{
   public ServerRequest(CallbackListener<ServerResponse> mainThread, String url)
   {
      this.url = url;
      this.mainThread = mainThread;
      ajsonTask = new AJson(this, this.url);
   }

   public void send()
   {
      ajsonTask.execute();
   }

   public abstract void callback(String result);

   private String url;
   protected CallbackListener<ServerResponse> mainThread;
   private AJson ajsonTask;
   private AppData appData;
}

