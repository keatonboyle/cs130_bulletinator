package com.example.bulletinator.helpers;

/**
 * Manages the scroll positions of each tab in the action bar
 * Used by main activity to save preferences
 */
public class ScrollManager {
    private int curPos[], nearPos[], allPos[], archPos[];

    public ScrollManager() {
        curPos = new int[2];
        nearPos = new int[2];
        allPos = new int[2];
        archPos = new int[2];
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

    public void setArchivedPos(int index, int top) {
        archPos[0] = index;
        archPos[1] = top;
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

    public int[] getArchivedPos() {
        return archPos;
    }

}
