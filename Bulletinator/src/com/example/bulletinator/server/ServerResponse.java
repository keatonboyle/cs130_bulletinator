package com.example.bulletinator.server;

public abstract class ServerResponse {
    public ServerResponse(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }

    protected String raw;
    protected String type;
}
