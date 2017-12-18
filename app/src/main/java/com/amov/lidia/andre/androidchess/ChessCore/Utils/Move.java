package com.amov.lidia.andre.androidchess.ChessCore.Utils;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

public class Move {
    Point Origin;
    Point Destination;
    String PieceName;
    String PieceSymbol;
    short Side;

    public Move(Point origin, Point destination, String pieceName, String pieceSymbol, short side) {
        Origin = origin;
        Destination = destination;
        PieceName = pieceName;
        PieceSymbol = pieceSymbol;
        Side = side;
    }

    public Move(GamePiece p,Point destination){
        this(p.getPositionInBoard(),destination,p.getName(),p.getLetter(),p.getSide());
    }

    public Point getDestination() {
        return Destination;
    }

    public Point getOrigin() {
        return Origin;
    }

    public String getPieceName() {
        return PieceName;
    }

    public short getSide() {
        return Side;
    }

    public String getPieceSymbol() {
        return PieceSymbol;
    }

    @Override
    public String toString() {
        String SideString = "";
        if(Side == Game.BLACK_SIDE)
            SideString = "Black ";
        else if(Side == Game.WHITE_SIDE){
            SideString = "White ";
        }
        return SideString + PieceName.toLowerCase() + " from " + Origin.toString() + " to " + Destination.toString();
    }
}
