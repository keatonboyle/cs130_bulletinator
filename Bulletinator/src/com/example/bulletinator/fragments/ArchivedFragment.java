package com.example.bulletinator.fragments;

import com.example.bulletinator.MainActivity;
import com.example.bulletinator.helpers.ScrollManager;

public class ArchivedFragment extends ParentFragment {

    @Override
    public int getTab() {
        return 3;
    }

    @Override
    public int[] getPos() {
        ScrollManager sm = mainActivity.getSM();
        int[] blah = {0,0};
        return blah;
    }

    @Override
    public void setPos(int index, int top) {
        ScrollManager sm = mainActivity.getSM();
        //sm.setAllPos(index, top);
    }
}