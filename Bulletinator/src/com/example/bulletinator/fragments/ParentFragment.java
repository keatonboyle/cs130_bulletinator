package com.example.bulletinator.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import com.example.bulletinator.MainActivity;
import com.example.bulletinator.R;
import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;
import com.example.bulletinator.helpers.ExpandableListAdapter;

import java.util.List;
import java.util.Set;

public abstract class ParentFragment extends Fragment {
    private List<Building> buildings;
    private ExpandableListView expandableListView;
    protected MainActivity mainActivity;
    private ExpandableListAdapter adapter;

    public abstract int getTab();

    public abstract int[] getPos();

    public abstract void setPos(int index, int top);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        // Inflate current buildings layout
        View view = inflater.inflate(R.layout.list_view, container, false);

        // Populate bulletins that MainActivity retrieved
        buildings = mainActivity.getBuildings();

        // Populate list with bulletins
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        adapter = new ExpandableListAdapter(
                mainActivity, buildings, getTab());

        // Set on click listener for viewing bulletins
        expandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // Start new bulletin activity
                Bulletin b = buildings.get(groupPosition).getBulletins()
                        .get(childPosition);
                mainActivity.selectBulletin(b);
                return true;
            }
        });
        // Set long click for archiving bulletins, adds checkmark and saves data
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.bulletinPreview);
                int pos[] = (int[]) textView.getTag();

                if (mainActivity.getArchiver().archiveBulletin(buildings.get(pos[0]).getBulletins().get(pos[1]))) {
                    Drawable icon = textView.getCompoundDrawables()[0];
                    Drawable checkmark = getResources().getDrawable(R.drawable.ic_launcher);
                    checkmark.setBounds(0, 0, 40, 40);
                    textView.setCompoundDrawables(icon, null, checkmark, null);
                    // TODO: If tags are used we will have to update tags when deleting bulletins!?
                } else {
                    // Create alert dialog to delete bulletin
                    AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
                    alert.setTitle("Delete bulletin?");
                    alert.setMessage("You have already archived this bulletin, " +
                            "would you like to delete it from memory?");
                    mainActivity.setAlertPos(pos);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // TODO: delete bulletin
                            int pos[] = mainActivity.getAlertPos();
                            mainActivity.getArchiver().deleteBulletin(((Bulletin) adapter.getChild(pos[0], pos[1])).getBulletinId());
                            adapter.notifyDataSetChanged();
                            //adapter.removeChild(pos[0], pos[1]);
                            dialog.cancel();
                        }
                    });
                    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
                return true;
            }
        });
        expandableListView.setAdapter(adapter);

        expandBuildings();

        // Re-instantiate scroll position
        int position[] = getPos();
        expandableListView.setSelectionFromTop(position[0], position[1]);

        return view;
    }

    // Save scroll position
    @Override
    public void onPause() {
        super.onPause();
        View v = expandableListView.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();
        setPos(expandableListView.getFirstVisiblePosition(), top);
    }

    private void expandBuildings() {
        if (getTab() == MainActivity.CURRENT) {
            // Do nothing on current or archived fragment when you click the only group
            expandableListView
                    .setOnGroupClickListener(new OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent,
                                                    View v, int groupPosition, long id) {
                            return true;
                        }
                    });
            expandableListView.setAdapter(adapter);
            expandableListView.expandGroup(0);
            expandableListView.setGroupIndicator(null);
        }
        // Re-expand previously expanded groups
        else {
            Set<String> bldgs = mainActivity.getCurExpandedBldgs(getTab());
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                String name = ((Building) adapter.getGroup(i)).getName();
                if (bldgs.contains(name))
                    expandableListView.expandGroup(i);
            }
            expandableListView.setIndicatorBounds(0, 50);
        }
    }
}
