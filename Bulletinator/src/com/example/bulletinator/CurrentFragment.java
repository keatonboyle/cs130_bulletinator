package com.example.bulletinator;

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