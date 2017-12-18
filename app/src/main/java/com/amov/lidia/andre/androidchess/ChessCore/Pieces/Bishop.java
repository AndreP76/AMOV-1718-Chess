package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils.NextDir;

public class Bishop extends GamePiece{
    public Bishop(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Bishop(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point pointInBoard = this.getPositionInBoard();

        int LineIncrement = -1;
        int ColIncrement = -1;
        for(Direction i = Direction.NORTHEAST; i != Direction.NORTHWEST; i = NextDir(NextDir(i))) {
            Point V = DirectionUtils.DirectionToVector(i);
            LineIncrement = V.getLine();
            ColIncrement = V.getCol();
            int Line = pointInBoard.getLine() + LineIncrement;
            int Col = pointInBoard.getCol() + ColIncrement;
            while (Line >= 0 && Col >= 0) {
                if (this.getGameBoard().getTile(new Point(Line, Col)) != null && this.getGameBoard().getTile(new Point(Line, Col)) != null) {//empty, valid tile
                    ALM.add(new Move(pointInBoard, new Point(Line, Col), this.getName(), this.getLetter(), this.getSide()));
                } else break;
                Line += LineIncrement;
                Col += ColIncrement;
            }
        }
        return ALM;
    }

    @Override
    public ArrayList<Attack> getPossibleAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        Point pointInBoard = this.getPositionInBoard();

        int LineIncrement = -1;
        int ColIncrement = -1;

        for(Direction i = Direction.NORTHEAST; i != Direction.NORTHWEST; i = NextDir(NextDir(i))) {
            Point V = DirectionUtils.DirectionToVector(i);
            LineIncrement = V.getLine();
            ColIncrement = V.getCol();

            int Line = pointInBoard.getLine() + LineIncrement;
            int Col = pointInBoard.getCol() + ColIncrement;
            while (Line >= 0 && Col >= 0) {
                ChessTile t = this.getGameBoard().getTile(new Point(Line, Col));
                if (t != null) {
                    GamePiece p = t.getPieceInTile();
                    if (p != null) {
                        if (p.getSide() != this.getSide()) {//an enemy piece
                            ALA.add(new Attack(this, p));
                        }
                        break;// whether we get an enemy or not, exit
                    }//the tile is empty. No atack, but also don't exit
                } else break; // we reached the end of the board;
                Line += LineIncrement;
                Col += ColIncrement;
            }
        }

        return ALA;
    }

    @Override
    public String getLetter() {
        return "B";
    }

    @Override
    public String getName() {
        return "Bishop";
    }
}
