package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

public class Attack extends Move {
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
        return (super.getPiece().getSide() == WHITE_SIDE ? "White " : "Black ") + super.getPieceName() + "(" + super.getPiece().getPositionInBoard().getLine() + "," + super.getPiece().getPositionInBoard().getCol() + ") attacked " + (AttackedPiece.getSide() == BLACK_SIDE ? "Black" : "White") + " " + AttackedPieceName + " (" + AttackedPiece.getPositionInBoard().getLine() + "," + AttackedPiece.getPositionInBoard().getCol() + ")";
    }
}
