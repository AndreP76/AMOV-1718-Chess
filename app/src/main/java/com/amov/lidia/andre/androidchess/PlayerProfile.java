package com.amov.lidia.andre.androidchess;

import java.io.Serializable;

/**
 * Created by andre on 1/5/18.
 */

public class PlayerProfile implements Serializable {
    String playerName;
    String playerPicturePath;

    public PlayerProfile(String name, String picture) {
        this.playerName = name;
        this.playerPicturePath = picture;
    }

    public CharSequence getName() {
        return playerName;
    }

    public String getPictureFilePath() {
        return playerPicturePath;
    }
}
