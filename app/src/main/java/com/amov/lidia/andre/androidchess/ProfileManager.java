package com.amov.lidia.andre.androidchess;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by andre on 1/5/18.
 */

public class ProfileManager {
    private static final String PROFILES_FILE = "profiles.bin";
    private static ArrayList<PlayerProfile> LoadedProfiles = null;

    public static void DestroyProfilesAndPictures(Context ctx) {
        CheckNullList(ctx);
        for (int i = 0; i < LoadedProfiles.size(); ++i) {
            PlayerProfile p = LoadedProfiles.get(i);
            ctx.deleteFile(p.getPictureFilePath());
        }
        ctx.deleteFile(PROFILES_FILE);
    }

    private static ArrayList<PlayerProfile> ReadFromFile(Context ctx) {
        ArrayList<PlayerProfile> pfs = null;
        try {
            ObjectInputStream FIS = new ObjectInputStream(ctx.openFileInput(PROFILES_FILE));
            pfs = (ArrayList<PlayerProfile>) FIS.readObject();
            return pfs;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static boolean WriteProfilesToFile(Context ctx) {
        try {
            ObjectOutputStream OOS = new ObjectOutputStream(ctx.openFileOutput(PROFILES_FILE, Context.MODE_PRIVATE));
            OOS.writeObject(LoadedProfiles);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void ReloadProfiles(Context ctx) {
        if (WriteProfilesToFile(ctx))
            ReadFromFile(ctx);
    }

    public static PlayerProfile addNewProfile(String name, String Picture, Context ctx) {
        CheckNullList(ctx);
        PlayerProfile P = new PlayerProfile(name, Picture);
        LoadedProfiles.add(P);
        ReloadProfiles(ctx);
        return P;
    }

    public static ArrayList<PlayerProfile> getAllProfiles(Context ctx) {
        CheckNullList(ctx);
        return LoadedProfiles;
    }

    public static PlayerProfile getProfile(int index, Context ctx) {
        CheckNullList(ctx);
        return LoadedProfiles.get(index);
    }

    private static boolean CheckNullList(Context ctx) {
        if (LoadedProfiles == null)
            LoadedProfiles = ReadFromFile(ctx);
        return true;
    }
}
