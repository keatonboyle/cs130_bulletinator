package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.FunctionObj;

public class BuildingRequest extends ServerRequest {
    public BuildingRequest(FunctionObj<ServerResponse> callback, String baseurl, int bldid) {
        super(callback, baseurl + "?type=building&bldid=" + Integer.toString(bldid));
        this.bldid = bldid;
    }

    public void callback(byte[] result) {
        BuildingResponse bldr = new BuildingResponse(result.toString());
        AppData.update(bldr);
        callWhenDone.call(bldr);
    }

    private int bldid;
}
