package com.example.bulletinator.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.bulletinator.data.AppData;

public class LocationModule {
    private static final int MIN_TIME_INTERVAL = 500; // milliseconds;
    private static final int MIN_DISTANCE_THRESHOLD = 3; // meters;

    public LocationModule(Activity main) {
        this.mainActivity = main;
    }

    public void run() {
        LocationManager lm = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        LocationListener ll = new LocationListener() {
            public void onLocationChanged(Location location) {
                AppData.update(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                MIN_TIME_INTERVAL,
                MIN_DISTANCE_THRESHOLD,
                ll);


        AppData.update(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER));


    }

    private Activity mainActivity;
}
