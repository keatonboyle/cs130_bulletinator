package com.example.bulletinator.helpers;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.bulletinator.MainActivity;

//Class from http://developer.android.com/guide/topics/ui/actionbar.html#Tabs
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /**
     * Constructor used each time a new tab is created.
     *
     * @param activity The host Activity, used to instantiate the fragment
     * @param tag      The identifier tag for the fragment
     * @param clz      The fragment's Class, used to instantiate the fragment
     */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        FragmentManager manager = mActivity.getFragmentManager();
        Fragment pFragment = manager.findFragmentByTag(mTag);

        // Prevent overlapping fragments on rotate
        if (pFragment != null) {
            mFragment = pFragment;
        }
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
        ((MainActivity) mActivity).setCurTab(tab.getPosition());
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}