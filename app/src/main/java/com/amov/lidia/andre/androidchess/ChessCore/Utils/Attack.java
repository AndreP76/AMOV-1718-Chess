package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

public class Attack extends Move {
    GamePiece AttackedPiece;
    String AttackedPieceName;
    String AttackedPieceSymbol;
    public Attack(GamePiece AttackerPiece, GamePiece AttackedPiece) {
        super(AttackedPiece.getPositionInBoard(), AttackedPiece.getPositionInBoard(), AttackerPiece.getName(), AttackerPiece.getLetter(), AttackedPiece.getSide());
        this.AttackedPiece = AttackerPiece;
        AttackedPieceName = AttackedPiece.getName();
        AttackedPieceSymbol = AttackedPiece.getLetter();
    }
}
