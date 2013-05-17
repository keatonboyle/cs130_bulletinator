package com.example.bulletinator;

public class NearbyFragment extends ParentFragment {

    public int getTab() {
        return MainActivity.NEARBY;
    }

    public int[] getPos() {
        ScrollManager sm = ((MainActivity) getActivity()).getSM();
        return sm.getNearPos();
    }

    public void setPos(int index, int top) {
        ScrollManager sm = ((MainActivity) getActivity()).getSM();
        sm.setNearbyPos(index, top);
    }
}