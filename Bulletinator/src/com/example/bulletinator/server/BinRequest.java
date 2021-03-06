package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.FunctionObj;

public class BinRequest extends ServerRequest {
    public BinRequest(FunctionObj<ServerResponse> callback, String baseurl, int fid) {
        super(callback, baseurl + "?type=file&fid=" + Integer.toString(fid));
        this.fid = fid;
    }

    public void callback(byte[] result) {
        BinResponse br = new BinResponse(this.fid, result);
        AppData.update(br);
        callWhenDone.call(br);
    }

    private int fid;
}
