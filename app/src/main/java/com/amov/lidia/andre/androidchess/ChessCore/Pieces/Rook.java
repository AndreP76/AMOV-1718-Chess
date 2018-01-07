package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.ChessTile;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Direction;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils.NextDir;

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
        int LineIncrement = 0;
        int ColIncrement = 0;
        for(Direction i = Direction.NORTH; i != Direction.SOUTH; i = NextDir(NextDir(i))) {
            Point V = DirectionUtils.DirectionToVector(i);
            LineIncrement = V.getLine();
            ColIncrement = V.getCol();

            int Line = this.getPositionInBoard().getLine() + LineIncrement;
            int Col = this.getPositionInBoard().getCol() + ColIncrement;

            while (Line >= 0 && Line < this.getGameBoard().getBoardLines() && Col >= 0 && Col < this.getGameBoard().getBoardCols()){
                ChessTile t = this.getGameBoard().getTile(new Point(Line,Col));
                if(t != null){
                    GamePiece p = t.getPieceInTile();
                    if (p == null) {
                        ALM.add(new Move(this,new Point(Line,Col)));
                    }else break;
                } else break;

                Line += LineIncrement;
                Col += ColIncrement;
            }
        }

        return ALM;
    }

    @Override
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        int LineIncrement = 0;
        int ColIncrement = 0;
        for(Direction i = Direction.NORTH; i != Direction.SOUTH; i = NextDir(NextDir(i))) {
            Point V = DirectionUtils.DirectionToVector(i);
            LineIncrement = V.getLine();
            ColIncrement = V.getCol();

            int Line = this.getPositionInBoard().getLine() + LineIncrement;
            int Col = this.getPositionInBoard().getCol() + ColIncrement;

            while (Line >= 0 && Line < this.getGameBoard().getBoardLines() && Col >= 0 && Col < this.getGameBoard().getBoardCols()){
                ChessTile t = this.getGameBoard().getTile(new Point(Line,Col));
                if(t != null){
                    GamePiece p = t.getPieceInTile();
                    if(p != null){
                        if(p.getSide() != this.getSide())
                            ALA.add(new Attack(this,p));
                        break;
                    }
                } else break;
                Line += LineIncrement;
                Col += ColIncrement;
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
