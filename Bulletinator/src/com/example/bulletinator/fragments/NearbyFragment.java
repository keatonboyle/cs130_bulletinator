package com.example.bulletinator.fragments;

import com.example.bulletinator.MainActivity;
import com.example.bulletinator.helpers.ScrollManager;

public class NearbyFragment extends ParentFragment {

    @Override
    public int getTab() {
        return MainActivity.NEARBY;
    }

    @Override
    public int[] getPos() {
        ScrollManager sm = mainActivity.getSM();
        return sm.getNearPos();
    }

    @Override
    public void setPos(int index, int top) {
        ScrollManager sm = mainActivity.getSM();
        sm.setNearbyPos(index, top);
    }
}