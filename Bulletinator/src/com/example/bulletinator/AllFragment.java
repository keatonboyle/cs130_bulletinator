package com.example.bulletinator;

public class AllFragment extends ParentFragment {

    @Override
    public int getTab() {
        return MainActivity.ALL;
    }

    @Override
    public int[] getPos() {
        ScrollManager sm = mainActivity.getSM();
        return sm.getAllPos();
    }

    @Override
    public void setPos(int index, int top) {
        ScrollManager sm = mainActivity.getSM();
        sm.setAllPos(index, top);
    }
}