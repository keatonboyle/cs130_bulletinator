package com.example.bulletinator.server;

public class BinResponse extends ServerResponse {
    public BinResponse(String raw) {
        super(raw);
        this.bytes = raw;
    }

    private String bytes;

}
