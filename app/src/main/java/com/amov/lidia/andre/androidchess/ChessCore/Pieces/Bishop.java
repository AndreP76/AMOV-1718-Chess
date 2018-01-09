package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.ChessTile;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;
import com.amov.lidia.andre.androidchess.R;

import java.util.ArrayList;

public class Bishop extends GamePiece {
    public Bishop(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Bishop(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point pointInBoard = this.getPositionInBoard();
        for (int i = 1; i < 9; i += 2) {
            Point V = DirectionUtils.DirectionToVector(DirectionUtils.IndexToDir(i));
            Point p = pointInBoard;
            p = p.sum(V);
            ChessTile t;
            while ((t = this.getGameBoard().getTile(p)) != null) {
                if (t.getPieceInTile() == null) {//empty, valid tile
                    ALM.add(new Move(this, p, this.getName(), this.getLetter(), this.getSide()));
                } else break;
                p = p.sum(V);
            }
        }
        return ALM;
    }

    @Override
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        Point pointInBoard = this.getPositionInBoard();

        int LineIncrement = -1;
        int ColIncrement = -1;

        for (int i = 1; i < 9; i += 2) {
            Point V = DirectionUtils.DirectionToVector(DirectionUtils.IndexToDir(i));
            Point p = pointInBoard;
            p = p.sum(V);
            ChessTile t;
            while ((t = getGameBoard().getTile(p)) != null) {
                GamePiece gp = t.getPieceInTile();
                if (gp != null) {
                    if (gp.getSide() != this.getSide()) {//an enemy piece
                        ALA.add(new Attack(this, gp));
                    }
                    break;// whether we get an enemy or not, exit
                }//the tile is empty. No atack, but also don't exit
                p = p.sum(V);
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
        return "B";
    }

    @Override
    public String getUnicodeLetter() {
        return "\u265D";
    }

    @Override
    public String getName() {
        return Chess.resources.getString(R.string.bishop); //"Bishop";
    }
}
