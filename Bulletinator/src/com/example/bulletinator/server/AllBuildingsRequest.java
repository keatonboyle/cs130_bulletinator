package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;

public class AllBuildingsRequest extends ServerRequest {
    public AllBuildingsRequest(CallbackListener<ServerResponse> mainThread, String baseurl) {
        super(mainThread, baseurl + "?type=all_buildings");
    }

    public void callback(String result) {
        AllBuildingsResponse abr = new AllBuildingsResponse(result);
        AppData.update(abr);
        mainThread.callback(abr);
    }
}
