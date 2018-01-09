package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;
import com.amov.lidia.andre.androidchess.R;

import java.io.Serializable;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

public class Attack extends Move implements Serializable {
    GamePiece AttackedPiece;
    String AttackedPieceName;
    String AttackedPieceSymbol;

    public Attack(GamePiece AttackerPiece, GamePiece AttackedPiece) {
        super(AttackerPiece, AttackedPiece.getPositionInBoard());
        this.AttackedPiece = AttackedPiece;
        AttackedPieceName = AttackedPiece.getName();
        AttackedPieceSymbol = AttackedPiece.getLetter();
    }

    public GamePiece getAttackedPiece() {
        return AttackedPiece;
    }

    @Override
    public String toString() {
        String white = Chess.resources.getString(R.string.white);
        String black = Chess.resources.getString(R.string.black);
        String attacked = Chess.resources.getString(R.string.piece_attack);
        return (super.getPiece().getSide() == WHITE_SIDE ? white + " "/*"White "*/ : black + " "/*"Black "*/) +
                super.getPieceName() +
                "(" + super.getPiece().getPositionInBoard().getLine() + "," +
                super.getPiece().getPositionInBoard().getCol() + ") " + attacked + " " +
                (AttackedPiece.getSide() == BLACK_SIDE ? black/*"Black"*/ : white/*"White"*/) + " " +
                AttackedPieceName + " (" + AttackedPiece.getPositionInBoard().getLine() + "," +
                AttackedPiece.getPositionInBoard().getCol() + ")";
    }
}
