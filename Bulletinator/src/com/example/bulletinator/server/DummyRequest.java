package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.FunctionObj;

public class DummyRequest extends ServerRequest {
    public DummyRequest(FunctionObj<ServerResponse> callback, String baseurl) {
        super(callback, baseurl + "?type=dummy");
    }

    public void callback(byte[] result) {
        DummyResponse dr = new DummyResponse(result.toString());
        AppData.update(dr);
        callWhenDone.call(dr);
    }
}
