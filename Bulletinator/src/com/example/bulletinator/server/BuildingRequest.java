package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;

public class BuildingRequest extends ServerRequest {
    public BuildingRequest(CallbackListener<ServerResponse> mainThread, String baseurl, int bldid) {
        super(mainThread, baseurl + "?type=building&bldid=" + Integer.toString(bldid));
        this.bldid = bldid;
    }

    public void callback(String result) {
        BuildingResponse bldr = new BuildingResponse(result);
        AppData.update(bldr);
        mainThread.callback(bldr);
    }

    private int bldid;
}
