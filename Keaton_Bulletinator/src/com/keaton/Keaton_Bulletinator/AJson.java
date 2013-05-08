package com.keaton.Keaton_Bulletinator;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;

public class AJson extends AsyncTask<String, Void, String> 
{
  protected String doInBackground(String... url)
  {
    String result = null;
    HttpClient httpclient = new DefaultHttpClient();
    HttpResponse response = null;
         
    HttpGet hget = null;
    try 
    {
      hget = new HttpGet(url[0]);
      response = httpclient.execute(hget);
        
      StatusLine statusLine = response.getStatusLine();

      if (statusLine.getStatusCode() == HttpStatus.SC_OK)
      {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        out.close();
        result = out.toString();
      }
      else
      {
        response.getEntity().getContent().close();
      }

    } 
    catch (ClientProtocolException e) 
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
    catch (IOException e) 
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    return result;
  }


}
