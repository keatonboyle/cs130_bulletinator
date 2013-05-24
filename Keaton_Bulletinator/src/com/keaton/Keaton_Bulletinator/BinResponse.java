package com.keaton.Keaton_Bulletinator;

public class BinResponse extends ServerResponse
{
   public BinResponse(String raw)
   {
      super(raw);
      this.bytes = raw;
   }

   private String bytes;

}
