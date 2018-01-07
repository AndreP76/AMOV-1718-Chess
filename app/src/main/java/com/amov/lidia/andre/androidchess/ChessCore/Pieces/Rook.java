package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.ChessTile;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;

import java.util.ArrayList;

public class Rook extends GamePiece {
    public Rook(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Rook(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point thisPosition = this.getPositionInBoard();
        for (int i = 0; i <= 7; ++i) {
            Point V = DirectionUtils.DirectionToVector(DirectionUtils.IndexToDir(i));
            Point p = thisPosition.sum(V);

            ChessTile t;
            while ((t = this.getGameBoard().getTile(p)) != null) {
                GamePiece gp = t.getPieceInTile();
                if (gp == null) {
                    ALM.add(new Move(this, p));
                } else break;
                p = p.sum(V);
            }
        }

        return ALM;
    }

    @Override
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        int LineIncrement = 0;
        int ColIncrement = 0;
        Point thisPosition = this.getPositionInBoard();
        for (int i = 0; i <= 7; ++i) {
            Point V = DirectionUtils.DirectionToVector(DirectionUtils.IndexToDir(i));
            Point p = thisPosition.sum(V);

            ChessTile t;
            while ((t = this.getGameBoard().getTile(p)) != null) {
                GamePiece gp = t.getPieceInTile();
                if (gp != null) {
                    if (gp.getSide() != this.getSide())
                        ALA.add(new Attack(this, gp));
                    break;
                }
                p = p.sum(V);
            }
        }

        return ALA;
    }

    public ArrayList<Point> getPossibleAttacks() {
        return null;
    }

    @Override
    public String getLetter() {
        return "R";
    }

    @Override
    public String getUnicodeLetter() {
        return "\u265C";
    }

    @Override
    public String getName() {
        return "Rook";
    }
}
