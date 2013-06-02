package com.example.bulletinator.server;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.helpers.CallbackListener;
import com.example.bulletinator.server.AJson;

public abstract class ServerRequest implements CallbackListener<String> {
    public ServerRequest(CallbackListener<ServerResponse> mainThread, String url) {
        this.url = url;
        this.mainThread = mainThread;
        ajsonTask = new AJson(this, this.url);
    }

    public void send() {
        ajsonTask.execute();
    }

    public abstract void callback(String result);

    private String url;
    protected CallbackListener<ServerResponse> mainThread;
    private AJson ajsonTask;
    private AppData appData;
}

