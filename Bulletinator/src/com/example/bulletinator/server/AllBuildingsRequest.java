package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.FunctionObj;

public class AllBuildingsRequest extends ServerRequest {
    public AllBuildingsRequest(FunctionObj<ServerResponse> callback, String baseurl) {
        super(callback, baseurl + "?type=all_buildings");
    }

    public void callback(byte[] result) {
        AllBuildingsResponse abr = new AllBuildingsResponse(result.toString());
        AppData.update(abr);
        callWhenDone.call(abr);
    }
}
