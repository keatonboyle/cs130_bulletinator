package com.example.bulletinator.server;

import android.os.AsyncTask;

import com.example.bulletinator.helpers.CallbackListener;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AJson extends AsyncTask<Void, Void, byte[]> {
    public AJson(CallbackListener requestObj, String url) {
        this.url = url;
        this.cbl = requestObj;
    }

    @Override
    protected byte[] doInBackground(Void... params) {
        byte[] result = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;

        HttpGet hget = null;
        try {
            hget = new HttpGet(url);
            response = httpclient.execute(hget);

            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                result = out.toByteArray();
            } else {
                response.getEntity().getContent().close();
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(byte[] result) {
        cbl.callback(result);
    }

    private CallbackListener<byte[]> cbl;
    protected String url;


}
