package com.example.bulletinator.server;

public class BinResponse extends ServerResponse {
    public BinResponse(int fid, byte[] raw) {
        super(raw.toString());
        this.fid = fid;
        this.bytes = raw;
    }

    public int fid;
    public byte[] bytes;

}
