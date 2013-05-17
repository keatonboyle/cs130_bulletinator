package com.example.bulletinator;

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