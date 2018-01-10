package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.ChessTile;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Direction;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.DirectionUtils;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;
import com.amov.lidia.andre.androidchess.R;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

public class King extends GamePiece {
    public King(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    King(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point p = this.getPositionInBoard();

        for(int i = 0; i < 8;++i){
            Direction d = DirectionUtils.IndexToDir(i);
            Point v = DirectionUtils.DirectionToVector(d);
            ChessTile T = this.getGameBoard().getTile(new Point(p.getLine() + v.getLine(),p.getCol() + v.getCol()));
            if (T != null && T.getPieceInTile() == null && !this.getGameBoard().TileIsAttacked(T, this.getSide() == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE)) {
                ALM.add(new Move(this,T.getCoordinatesInBoard()));
            }
        }

        return ALM;
    }

    @Override
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        Point p = this.getPositionInBoard();

        for(int i = 0; i < 8;++i){
            Direction d = DirectionUtils.IndexToDir(i);
            Point v = DirectionUtils.DirectionToVector(d);
            ChessTile T = this.getGameBoard().getTile(new Point(p.getLine() + v.getLine(),p.getCol() + v.getCol()));
            if (T != null && T.getPieceInTile() != null && T.getPieceInTile().getSide() != this.getSide() && !this.getGameBoard().TileCanBeAttackedBySide(T.getCoordinatesInBoard(), this.getSide() == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE)) {
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
        return Chess.resources.getString(R.string.king); //"King";
    }

    public ArrayList<Point> getPossibleAttacks() {
        ArrayList<Point> ALP = new ArrayList<>();
        ArrayList<Move> ALM = getMoves();
        for (Move m : ALM) {
            ALP.add(m.getDestination());
        }
        return ALP;
    }
}
