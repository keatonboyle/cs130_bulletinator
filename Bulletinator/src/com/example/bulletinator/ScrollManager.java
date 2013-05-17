package com.example.bulletinator;

/**
 * Manages the scroll positions of each tab in the action bar
 * Used by main activity to save preferences
 */
public class ScrollManager {
    private int curPos[], nearPos[], allPos[];

    public ScrollManager() {
        curPos = new int[3];
        nearPos = new int[3];
        allPos = new int[3];
    }

    public void setCurPos(int index, int top) {
        curPos[0] = index;
        curPos[1] = top;
    }

    public void setNearbyPos(int index, int top) {
        nearPos[0] = index;
        nearPos[1] = top;
    }

    public void setAllPos(int index, int top) {
        allPos[0] = index;
        allPos[1] = top;
    }

    public int[] getCurPos() {
        return curPos;
    }

    public int[] getNearPos() {
        return nearPos;
    }

    public int[] getAllPos() {
        return allPos;
    }

}
