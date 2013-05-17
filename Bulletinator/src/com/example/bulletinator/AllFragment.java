package com.example.bulletinator;

public class AllFragment extends ParentFragment {

    public int getTab() {
        return MainActivity.ALL;
    }

    public int[] getPos() {
        ScrollManager sm = ((MainActivity) getActivity()).getSM();
        return sm.getAllPos();
    }

    public void setPos(int index, int top) {
        ScrollManager sm = ((MainActivity) getActivity()).getSM();
        sm.setAllPos(index, top);
    }
}