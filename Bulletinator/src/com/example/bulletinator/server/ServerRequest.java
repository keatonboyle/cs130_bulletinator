package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;
import com.example.bulletinator.helpers.FunctionObj;

public abstract class ServerRequest implements CallbackListener<byte[]> {
    public ServerRequest(FunctionObj<ServerResponse> callback, String url) {
        this.url = url;
        this.callWhenDone = callback;
        ajsonTask = new AJson(this, this.url);
    }

    public void send() {
        ajsonTask.execute();
    }

    public abstract void callback(byte[] result);

    private String url;
    protected FunctionObj<ServerResponse> callWhenDone;
    private AJson ajsonTask;
    private AppData appData;
}

