package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;
import com.example.bulletinator.helpers.FunctionObj;


public class GPSRequest extends ServerRequest {
    public GPSRequest(FunctionObj<ServerResponse> callback,
                      String baseurl, double lat, double lon) {
        super(callback, baseurl + "?type=gps_update&lat=" + Double.toString(lat) + "&long=" + Double.toString(lon));
        this.lat = lat;
        this.lon = lon;
    }

    public void callback(byte[] result) {
        GPSResponse gpsr = new GPSResponse(new String(result));
        AppData.update(gpsr);
        callWhenDone.call(gpsr);
    }

    private double lat;
    private double lon;
}
