package com.example.bulletinator.data;

import java.util.List;

public class Building {
    private String name;
    private List<Bulletin> bulletins;
    private int id;

    public Building(String n, List<Bulletin> b, int i) {
        bulletins = b;
        name = n;
        id = i;
    }

    public String getName() {
        return name;
    }

    public List<Bulletin> getBulletins() {
        return bulletins;
    }

    public int getId() {
        return id;
    }
}
