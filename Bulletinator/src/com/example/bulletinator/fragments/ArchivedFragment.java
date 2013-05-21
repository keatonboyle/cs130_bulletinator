package com.example.bulletinator.fragments;

import com.example.bulletinator.MainActivity;
import com.example.bulletinator.helpers.ScrollManager;

public class ArchivedFragment extends ParentFragment {

    @Override
    public int getTab() {
        return MainActivity.ARCHIVED;
    }

    @Override
    public int[] getPos() {
        ScrollManager sm = mainActivity.getSM();
        return sm.getArchivedPos();
    }

    @Override
    public void setPos(int index, int top) {
        ScrollManager sm = mainActivity.getSM();
        sm.setArchivedPos(index, top);
    }
}