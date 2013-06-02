package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;

public class EverythingRequest extends ServerRequest {

    public EverythingRequest(CallbackListener<ServerResponse> mainThread, String baseurl) {
        super(mainThread, baseurl + "?type=everything");
    }

    @Override
    public void callback(String result) {
        EverythingResponse dr = new EverythingResponse(result);
        AppData.update(dr);
        mainThread.callback(dr);
    }
}