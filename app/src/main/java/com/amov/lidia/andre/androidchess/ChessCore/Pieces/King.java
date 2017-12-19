package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;

public class King extends GamePiece {
    public King(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    King(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point p = this.getPositionInBoard();

        for(int i = 0; i < 8;++i){
            Direction d = DirectionUtils.IndexToDir(i);
            Point v = DirectionUtils.DirectionToVector(d);
            ChessTile T = this.getGameBoard().getTile(new Point(p.getLine() + v.getLine(),p.getCol() + v.getCol()));
            if(T != null && T.getPieceInTile() == null){
                ALM.add(new Move(this,T.getCoordinatesInBoard()));
            }
        }

        return ALM;
    }

    @Override
    public ArrayList<Attack> getPossibleAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        Point p = this.getPositionInBoard();

        for(int i = 0; i < 8;++i){
            Direction d = DirectionUtils.IndexToDir(i);
            Point v = DirectionUtils.DirectionToVector(d);
            ChessTile T = this.getGameBoard().getTile(new Point(p.getLine() + v.getLine(),p.getCol() + v.getCol()));
            if(T != null && T.getPieceInTile() != null && T.getPieceInTile().getSide() != this.getSide()){
                ALA.add(new Attack(this,T.getPieceInTile()));
            }
        }

        return ALA;
    }

    @Override
    public String getLetter() {
        return "K";
    }

    @Override
    public String getUnicodeLetter() {
        return "\u265A";
    }

    @Override
    public String getName() {
        return "King";
    }
}
