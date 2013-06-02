package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;


public class GPSRequest extends ServerRequest {
    public GPSRequest(CallbackListener<ServerResponse> mainThread,
                      String baseurl, double lat, double lon) {
        super(mainThread, baseurl + "?type=gps_update&lat=" + Double.toString(lat) + "&long=" + Double.toString(lon));
        this.lat = lat;
        this.lon = lon;
    }

    public void callback(String result) {
        GPSResponse gpsr = new GPSResponse(result);
        AppData.update(gpsr);
        mainThread.callback(gpsr);
    }

    private double lat;
    private double lon;
}
