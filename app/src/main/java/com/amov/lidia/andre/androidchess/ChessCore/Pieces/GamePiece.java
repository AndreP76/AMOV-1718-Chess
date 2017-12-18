package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

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
    public abstract ArrayList<Move> getPossibleMoves();
    public abstract ArrayList<Attack> getPossibleAttacks();
    public boolean Move(Point newPos){ return this.setPositionInBoard(newPos); }
    public void Move(int newX, int newY){ this.setPositionInBoard(new Point(newX,newY)); }
    public abstract String getLetter();

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
}