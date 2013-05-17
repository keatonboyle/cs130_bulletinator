package com.example.bulletinator.fragments;

import com.example.bulletinator.MainActivity;
import com.example.bulletinator.helpers.ScrollManager;

public class CurrentFragment extends ParentFragment {

    @Override
    public int getTab() {
        return MainActivity.CURRENT;
    }

    @Override
    public int[] getPos() {
        ScrollManager sm = mainActivity.getSM();
        return sm.getCurPos();
    }

    @Override
    public void setPos(int index, int top) {
        ScrollManager sm = mainActivity.getSM();
        sm.setCurPos(index, top);
    }
}