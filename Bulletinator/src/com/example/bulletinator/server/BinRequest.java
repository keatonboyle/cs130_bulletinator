package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;

public class BinRequest extends ServerRequest {
    public BinRequest(CallbackListener<ServerResponse> mainThread, String baseurl, int fid) {
        super(mainThread, baseurl + "?type=file&fid=" + Integer.toString(fid));
        this.fid = fid;
    }

    public void callback(String result) {
        BinResponse br = new BinResponse(result);
        AppData.update(br);
        mainThread.callback(br);
    }

    private int fid;
}
