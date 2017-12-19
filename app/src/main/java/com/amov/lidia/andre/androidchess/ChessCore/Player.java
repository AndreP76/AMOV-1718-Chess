package com.amov.lidia.andre.androidchess.ChessCore;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andre on 12/18/17.
 */

public class Player {
    private long remainingMilliseconds;
    private ArrayList<GamePiece> Pieces;
    private String name;
    private BitmapDrawable picture;
    private String picturePath;
    private boolean isAI;

    //TODO : rever estes construtores no futuro. Agora sao so para teste e assim
    public Player(){
        this(-1,new ArrayList<GamePiece>(),"HAL 9000",null,true);
    }

    public Player(String name, boolean isAI){
        this(-1,new ArrayList<GamePiece>(),name,null,isAI);
    }

    Player(int remainingMilliseconds, ArrayList<GamePiece> Pieces, String name, String picturePath, boolean isAI){
        this.Pieces = Pieces;
        this.remainingMilliseconds = remainingMilliseconds;
        this.isAI = isAI;
        this.name = name;
        this.picturePath = picturePath;
        this.picture = null;
        //this.picture = LoadPicture();
    }

    public void addPieces(Collection<GamePiece> sidesPiece) {
        this.Pieces.addAll(sidesPiece);
    }


    public void removePiece(GamePiece attackedPiece) {
        this.Pieces.remove(attackedPiece);
    }
}
