package com.keaton.Keaton_Bulletinator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import org.json.*;
import android.location.*;

public class MainActivity extends Activity implements CallbackListener<ServerResponse> 
{
   public static final String baseurl = "http://linux.ucla.edu/~cs130s/get.php";
  
   public void callback(ServerResponse sr)
   {
      Context context = getApplicationContext();
      CharSequence toasttext = "Response recieved, notifying you";
      
      Toast toast = Toast.makeText(context, toasttext, Toast.LENGTH_SHORT);
      toast.show();

      TextView textview = (TextView) findViewById(R.id.result);
      textview.setText(sr.getRaw());

   }

   public void locationCallback(Location l)
   {
      toaster(Double.toString(l.getLatitude()));
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      AppData.getInstance();
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);


      LocationModule mlm = new LocationModule(
            new FunctionObj<Location>() {
               public void call(Location l)
               {
                  locationCallback(l);
               }
            },
            this);

      mlm.run();
   }

   public void toaster(String str)
   {
      Context context = getApplicationContext();

      Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
      toast.show();
   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   public void getAllBuildings(View view)
   {
      AllBuildingsRequest abr = new AllBuildingsRequest(this, baseurl);
      abr.send();
   }
   public void getBuilding(View view)
   {
      String id = ((EditText) findViewById(R.id.bldid_field)).getText().toString();
      BuildingRequest br = null;
      try
      {
         br = new BuildingRequest(this, baseurl, Integer.parseInt(id));
      }
      catch (NumberFormatException e)
      {
         Toast.makeText(getApplicationContext(), "Pleaese enter valid integer", Toast.LENGTH_SHORT)
              .show();
         return;
      }

      br.send();
   }
   public void printAppData(View view)
   {
      String dump = AppData.getSummaryString();

      TextView textview = (TextView) findViewById(R.id.result);
      textview.setText(dump);
   }
   

   /*

   public void printJson(String result)
   {
      TextView text = (TextView) findViewById(R.id.raw_ajson_result);
      text.setText(result);
   }


  
  public void parseJson (View view)
  {
     String unparsed = 
        (String) ((TextView) findViewById(R.id.raw_ajson_result)).getText();
     
     JSONObject json = null;
     
     String greeting = null;
     
     try
     {
        json = new JSONObject(unparsed);
        
        greeting = (String) json.get("greeting");  
        
     }
     catch (JSONException e)
     {
        greeting = "Exception!";
     }
     
     
     ((TextView) findViewById(R.id.parsed_json_result)).setText(greeting);
   }
  
   public void getGPS (View view)
   {
     LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
     
     LocationListener ll = new LocationListener() {
        public void onLocationChanged(Location location)
        {
           updateLocation(location);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) 
        {
           Toast.makeText(getApplicationContext(), 
             "provider disabled", Toast.LENGTH_SHORT).show();
        }
     };
     
     lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
     
     updateLocation(lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
     
   }
   
   public void updateLocation(Location location)
   {  
      ((TextView) findViewById(R.id.gps_result)).setText(
           Double.toString(location.getLatitude()));
   }
   */
   
}
