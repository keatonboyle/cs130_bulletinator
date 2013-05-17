package com.example.bulletinator;

import java.util.List;

public class Building {
    private String name;
    private List<Bulletin> bulletins;

    public Building(String n, List<Bulletin> b) {
        bulletins = b;
        name = n;
    }

    public String getName() {
        return name;
    }

    public List<Bulletin> getBulletins() {
        return bulletins;
    }
}
