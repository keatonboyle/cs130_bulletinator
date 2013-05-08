package com.keaton.Keaton_Bulletinator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.*;
import android.location.*;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

  public void getJson (View view)
  {
    TextView text = (TextView) findViewById(R.id.raw_ajson_result);
    String response = null;

    AJson aj = new AJson();
    aj.execute("http://linux.ucla.edu/~cs130s/test.php");
    try
    {
      response = aj.get();
    }
    catch (Exception e)
    {
      response = "error";
    }

    text.setText(response);
  }

  
  public void parseJson (View view)
  {
	  String unparsed = (String) ((TextView) findViewById(R.id.raw_ajson_result)).getText();
	  
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
   
}
