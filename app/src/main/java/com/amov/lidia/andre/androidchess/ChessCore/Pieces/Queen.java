package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

import java.util.ArrayList;

public class Queen extends GamePiece {
    public Queen(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Queen(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            Direction d = DirectionUtils.IndexToDir(i);
            Point offset = DirectionUtils.DirectionToVector(d);
            for (Point currentPoint = this.getPositionInBoard().sum(offset); this.getGameBoard().getTile(currentPoint) != null; currentPoint = currentPoint.sum(offset)) {
                if (this.getGameBoard().getTile(currentPoint).getPieceInTile() == null) {//an empty tile
                    ALM.add(new Move(this,currentPoint));
                }else break;
            }
        }
        return ALM;
    }

    @Override
    public ArrayList<Attack> getPossibleAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        for(int i = 0; i < 9;++i){
            Direction d = DirectionUtils.IndexToDir(i);
            Point offset = DirectionUtils.DirectionToVector(d);
            for(Point currentPoint = this.getPositionInBoard().sum(offset); this.getGameBoard().getTile(currentPoint) != null; currentPoint = currentPoint.sum(offset)){
                if(this.getGameBoard().getTile(currentPoint).getPieceInTile() != null && this.getGameBoard().getTile(currentPoint).getPieceInTile().getSide() != this.getSide()){//an enemy
                    ALA.add(new Attack(this,this.getGameBoard().getTile(currentPoint).getPieceInTile()));
                    break;
                }
            }
        }
        return ALA;
    }

    @Override
    public String getLetter() {
        return "Q";
    }

    @Override
    public String getName() {
        return "Queen";
    }
}
