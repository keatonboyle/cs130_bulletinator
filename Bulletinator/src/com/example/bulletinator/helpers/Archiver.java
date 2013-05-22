package com.example.bulletinator.helpers;

import com.example.bulletinator.MainActivity;
import com.example.bulletinator.data.Bulletin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Archiver {
    private MainActivity mainActivity;

    public Archiver(MainActivity activity) {
        mainActivity = activity;
    }

    // For archiving bulletins (per device)
    public boolean archiveBulletin(Bulletin b) {
        // TODO: archive a copy of the pictures associated with bulletin as well
        List<Bulletin> archivedBulletins = getArchivedBulletins();

        for (int i = 0; i < archivedBulletins.size(); i++) {
            if (archivedBulletins.get(i).getBulletinId() == b.getBulletinId()) {
                return false;
            }
        }
        archivedBulletins.add(b);
        saveArchivedBulletins(archivedBulletins);
        mainActivity.toast("Bulletin saved");

        return true;
    }

    public void deleteBulletin(int id) {
        List<Bulletin> archivedBulletins = getArchivedBulletins();
        for (int i = 0; i < archivedBulletins.size(); i++) {
            if (archivedBulletins.get(i).getBulletinId() == id) {
                archivedBulletins.remove(i);
                saveArchivedBulletins(archivedBulletins);
                mainActivity.toast("Bulletin deleted.");
                return;
            }
        }
    }

    public List<Bulletin> getArchivedBulletins() {
        File bulletins = mainActivity.getFileStreamPath("archived_bulletins");

        List<Bulletin> archivedBulletins = new ArrayList<Bulletin>();
        try {
            if (bulletins.exists()) {
                FileInputStream fis = mainActivity.openFileInput("archived_bulletins");
                ObjectInputStream ois = new ObjectInputStream(fis);
                archivedBulletins = (ArrayList<Bulletin>) ois.readObject();
                fis.close();
            } else {
                archivedBulletins = new ArrayList<Bulletin>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return archivedBulletins;
    }

    public void saveArchivedBulletins(List<Bulletin> archivedBulletins) {
        File myfile = mainActivity.getFileStreamPath("archived_bulletins");
        try {
            if (myfile.exists() || myfile.createNewFile()) {
                FileOutputStream fos = mainActivity.openFileOutput("archived_bulletins", MainActivity.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(archivedBulletins);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
