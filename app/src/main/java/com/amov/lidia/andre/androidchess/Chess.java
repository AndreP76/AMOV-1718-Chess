package com.amov.lidia.andre.androidchess;

import android.app.Application;

import com.amov.lidia.andre.androidchess.ChessCore.Game;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;

/**
 * Created by andre on 12/15/17.
 */

public class Chess extends Application {
    private static Game currentGame = null;
    private static GamePiece currentSelectedPiece = null;

    public static GamePiece getCurrentSelectedPiece() {
        return currentSelectedPiece;
    }

    public static Game getCurrentGame(){
        return currentGame;
    }
}
