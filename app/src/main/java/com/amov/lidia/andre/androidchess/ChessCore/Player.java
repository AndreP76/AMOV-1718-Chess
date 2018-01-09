package com.amov.lidia.andre.androidchess.ChessCore;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;
import com.amov.lidia.andre.androidchess.PlayerProfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andre on 12/18/17.
 */

public class Player implements Serializable {
    private int side;
    private long remainingMilliseconds;
    private ArrayList<GamePiece> Pieces;
    private String name;
    private BitmapDrawable picture;
    private String picturePath;
    private boolean isAI;

    //TODO : rever estes construtores no futuro. Agora sao so para teste e assim
    //LOOOOOOL, keep fooling yourself.
    // FIXME: 1/7/18 Above todo
    public Player(int side) {
        this(-1, new ArrayList<GamePiece>(), "HAL 9000", null, true, side);
    }

    public Player(String name, boolean isAI, int side) {
        this(-1, new ArrayList<GamePiece>(), name, null, isAI, side);
    }

    public Player(ArrayList<GamePiece> Pieces, String Name, String PicurePath, int side) {
        this(-1, Pieces, Name, PicurePath, false, side);
    }

    Player(int remainingMilliseconds, ArrayList<GamePiece> Pieces, String name, String picturePath, boolean isAI, int side) {
        this.Pieces = Pieces;
        this.remainingMilliseconds = remainingMilliseconds;
        this.isAI = isAI;
        this.name = name;
        this.picturePath = picturePath;
        this.picture = null;
        this.side = side;
        //this.picture = LoadPicture();
    }

    public Player(PlayerProfile playerProfile, int side, boolean isAI) {
        this(-1, new ArrayList<GamePiece>(), playerProfile.getName().toString(), playerProfile.getPictureFilePath(), isAI, side);
    }


    public void addPieces(Collection<GamePiece> sidesPiece) {
        if (this.Pieces == null) {
            this.Pieces = new ArrayList<>();
            Log.w("[PLAYER] :: ", "Player pieces list was null");
        }
        this.Pieces.addAll(sidesPiece);
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void removePiece(GamePiece attackedPiece) {
        this.Pieces.remove(attackedPiece);
    }

    public String getName() {
        return name;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        this.isAI = AI;
    }

    public ArrayList<GamePiece> getPieces() {
        return Pieces;
    }

    public void setPieces(ArrayList<GamePiece> pieces) {
        Pieces = pieces;
    }
}
