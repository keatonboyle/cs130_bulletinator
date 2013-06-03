package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;
import com.example.bulletinator.helpers.FunctionObj;

public class EverythingRequest extends ServerRequest {

    public EverythingRequest(FunctionObj<ServerResponse> callback, String baseurl) {
        super(callback, baseurl + "?type=everything");
    }

    @Override
    public void callback(String result) {
        EverythingResponse dr = new EverythingResponse(result);
        AppData.update(dr);
        callWhenDone.call(dr);
    }
}