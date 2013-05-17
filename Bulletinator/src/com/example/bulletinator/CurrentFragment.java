package com.example.bulletinator;

public class CurrentFragment extends ParentFragment {

    public int getTab() {
        return MainActivity.CURRENT;
    }

    public int[] getPos() {
        ScrollManager sm = ((MainActivity) getActivity()).getSM();
        return sm.getCurPos();
    }

    public void setPos(int index, int top) {
        ScrollManager sm = ((MainActivity) getActivity()).getSM();
        sm.setCurPos(index, top);
    }
}