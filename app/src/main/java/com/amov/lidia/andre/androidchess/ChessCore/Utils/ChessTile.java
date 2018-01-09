package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;

import java.io.Serializable;

public class ChessTile implements Serializable {
    public static final short BLACK_TILE = 1;
    public static final short WHITE_TILE = 0;

    Point CoordinatesInBoard;
    Board MyBoard;
    GamePiece PieceInTile;
    Short TileColor;

    public ChessTile(int Line, int Col, Board b){
        if(Line % 2 == 0){
            if(Col % 2 == 0)
                this.TileColor = WHITE_TILE;
            else
                this.TileColor = BLACK_TILE;
        }else{
            if(Col % 2 == 0){
                this.TileColor = BLACK_TILE;
            }else{
                this.TileColor = WHITE_TILE;
            }
        }
        MyBoard = b;
        CoordinatesInBoard = new Point(Line,Col);
    }

    public Short getTileColor() {
        return TileColor;
    }

    public GamePiece getPieceInTile() {
        return PieceInTile;
    }

    public void setPieceInTile(GamePiece pieceInTile) {
        this.PieceInTile = pieceInTile;
    }

    public Point getCoordinatesInBoard() {
        return CoordinatesInBoard;
    }
}
