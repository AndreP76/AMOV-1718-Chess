package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Direction;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;
import com.amov.lidia.andre.androidchess.R;

import java.util.ArrayList;

public class Queen extends GamePiece {
    public Queen(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Queen(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getMoves() {
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
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        Point queenPosition = this.getPositionInBoard();
        for(int i = 0; i < 9;++i){
            Direction d = DirectionUtils.IndexToDir(i);
            Point offset = DirectionUtils.DirectionToVector(d);
            for (Point currentPoint = queenPosition.sum(offset); this.getGameBoard().getTile(currentPoint) != null; currentPoint = currentPoint.sum(offset)) {
                if (this.getGameBoard().getTile(currentPoint).getPieceInTile() != null) {//a piece
                    if (this.getGameBoard().getTile(currentPoint).getPieceInTile().getSide() != this.getSide()) {// an enemy
                        ALA.add(new Attack(this, this.getGameBoard().getTile(currentPoint).getPieceInTile()));
                    }
                    break;//break when we find a piece, enemy or not
                }
            }
        }
        return ALA;
    }

    public ArrayList<Point> getPossibleAttacks() {
        ArrayList<Point> ALP = new ArrayList<>();
        ArrayList<Move> ALM = getMoves();
        for (Move m : ALM) {
            ALP.add(m.getDestination());
        }
        return ALP;
    }

    @Override
    public String getLetter() {
        return "Q";
    }

    @Override
    public String getUnicodeLetter() {
        return "\u265B";
    }

    @Override
    public String getName() {
        return Chess.resources.getString(R.string.queen); //"Queen";
    }
}
