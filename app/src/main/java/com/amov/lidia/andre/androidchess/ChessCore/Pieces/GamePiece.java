package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;

import java.util.ArrayList;

public abstract class GamePiece {
    private Point PositionInBoard;
    private Board GameBoard;
    private short Side;
    private boolean IsCaptured;

    /*CONSTRUCTORS*/
    GamePiece(Board B, Point Position, short Side) throws AlreadyFilledException {
        this.GameBoard = B;
        B.setPiece(this,GameBoard.getTile(Position));
        this.PositionInBoard = Position;
        this.Side = Side;
        IsCaptured = false;
    }
    GamePiece(Board B, short Side){
        this.GameBoard = B;
        this.PositionInBoard = new Point(-1,-1);
        this.Side = Side;
        IsCaptured = false;
    }

    /*GAME METHODS*/
    public abstract ArrayList<Move> getMoves();

    public abstract ArrayList<Attack> getAttacks();

    //public abstract ArrayList<Point> getPossibleAttacks();
    public boolean Move(Point newPos){ return this.setPositionInBoard(newPos); }
    public void Move(int newX, int newY){ this.setPositionInBoard(new Point(newX,newY)); }
    public abstract String getLetter();
    public abstract String getUnicodeLetter();

    // branca       pretas
    // u/2654       u/265A  rei
    // u/2655       u/265B  dama
    // u/2656       u/265C  torre
    // u/2657       u/265D  bispo
    // u/2658       u/265E  cavalo
    // u/2659       u/265F  peao

    /*SETTERS*/
    public boolean setPositionInBoard(Point positionInBoard) {
        GameBoard.getTile(PositionInBoard).setPieceInTile(null);
        PositionInBoard = positionInBoard;
        GameBoard.getTile(positionInBoard).setPieceInTile(this);
        return true;
    }

    /*GETTERS*/
    public Point getPositionInBoard() {
        return PositionInBoard;
    }

    public short getSide() {
        return Side;
    }

    public Board getGameBoard() {
        return GameBoard;
    }

    public abstract String getName();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GamePiece) {
            GamePiece gp = (GamePiece) obj;
            return getPositionInBoard().equals(gp.getPositionInBoard()) && gp.getSide() == this.getSide() && this.getName().equals(gp.getName());
        } else return false;
    }
}
