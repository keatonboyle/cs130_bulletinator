package com.example.bulletinator.data;

import java.util.List;
import java.util.Set;

public class Building {
    private String name;
    private Set<Integer> btnids;
    private int id;

    public Building(int i, String n, Set<Integer> b) {
        btnids = b;
        name = n;
        id = i;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getBtnIds() {
        return btnids;
    }

    public int getId() {
        return id;
    }
}
