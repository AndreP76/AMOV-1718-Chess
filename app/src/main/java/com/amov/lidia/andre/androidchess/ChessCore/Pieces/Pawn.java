package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Game;
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

public class Pawn extends GamePiece {
    boolean firstMove = true;
    boolean firstMoveUsed = false;

    boolean promotionFlag = false;

    public Pawn(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Pawn(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point pointInGame = this.getPositionInBoard();
        int dist = 2;
        if(firstMove) dist = 3;
        if (this.getSide() == BLACK_SIDE) {//Black side goes north and only north
            for(int i = pointInGame.getLine()-1; i > pointInGame.getLine()-dist;--i){
                if(this.getGameBoard().getTile(new Point(i,pointInGame.getCol())).getPieceInTile() != null) break;
                else ALM.add(new Move(this,new Point(i,pointInGame.getCol()),Chess.resources.getString(R.string.pawn)/*"Pawn"*/,this.getLetter(),this.getSide()));
            }
        } else if (this.getSide() == Game.WHITE_SIDE) {//White side goes south
            for(int i = pointInGame.getLine()+1; i < pointInGame.getLine()+dist;++i){
                if(this.getGameBoard().getTile(new Point(i,pointInGame.getCol())).getPieceInTile() != null) break;
                else ALM.add(new Move(this,new Point(i,pointInGame.getCol()), Chess.resources.getString(R.string.pawn)/*"Pawn"*/,this.getLetter(),this.getSide()));
            }
        }
        return ALM;
    }

    @Override
    public boolean Move(Point newPos) {
        Point op = this.getPositionInBoard();
        boolean r = super.Move(newPos);
        if(r) {
            if(firstMoveUsed) firstMoveUsed = false;
            if(firstMove) {
                firstMove = false;
                Point np = this.getPositionInBoard();
                if (Math.abs(np.getLine() - op.getLine()) >= 2) {
                    firstMoveUsed = true;
                }
            }
            if (this.getPositionInBoard().getLine() == this.getGameBoard().getBoardLines() - 1 || this.getPositionInBoard().getLine() == 0)
                promotionFlag = true;
            // TODO: 1/10/18 : check for promotions in the board view, and create the apropriate dialog
        }
        return r;
    }

    @Override
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> ALA = new ArrayList<>();
        ChessTile[] PassantTiles = new ChessTile[2];
        ChessTile[] StandardTiles = new ChessTile[2];
        Point p = this.getPositionInBoard();
        PassantTiles[0] = this.getGameBoard().getTile(p.sum(DirectionUtils.DirectionToVector(Direction.EAST)));
        PassantTiles[1] = this.getGameBoard().getTile(p.sum(DirectionUtils.DirectionToVector(Direction.WEST)));

        if(this.getSide() == BLACK_SIDE) {
            StandardTiles[0] = this.getGameBoard().getTile(p.sum(DirectionUtils.DirectionToVector(Direction.NORTHWEST)));
            StandardTiles[1] = this.getGameBoard().getTile(p.sum(DirectionUtils.DirectionToVector(Direction.NORTHEAST)));
        }else{
            StandardTiles[0] = this.getGameBoard().getTile(p.sum(DirectionUtils.DirectionToVector(Direction.SOUTHWEST)));
            StandardTiles[1] = this.getGameBoard().getTile(p.sum(DirectionUtils.DirectionToVector(Direction.SOUTHEAST)));
        }

        for(ChessTile t : PassantTiles){
            if(t != null && t.getPieceInTile() != null && t.getPieceInTile().getSide() != this.getSide() && t.getPieceInTile() instanceof Pawn && ((Pawn)(t.getPieceInTile())).getFirstMoveUsed()){//passant target
                ALA.add(new Attack(this,t.getPieceInTile()));
            }
        }
        for(ChessTile t : StandardTiles){
            if(t != null && t.getPieceInTile() != null && t.getPieceInTile().getSide() != this.getSide()){
                ALA.add(new Attack(this,t.getPieceInTile()));
            }
        }
        return ALA;
    }

    public ArrayList<Point> getPossibleAttacks() {
        ArrayList<Point> ALP = new ArrayList<>();
        Point p = this.getPositionInBoard();

        Point p1 = p.sum(DirectionUtils.DirectionToVector(Direction.SOUTHEAST));
        Point p2 = p.sum(DirectionUtils.DirectionToVector(Direction.SOUTHWEST));
        if (this.getSide() == WHITE_SIDE) {//goes south

        } else {//goes north

        }

        if (this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null) {
            ALP.add(p1);
        }

        if (this.getGameBoard().getTile(p2) != null && this.getGameBoard().getTile(p2).getPieceInTile() != null) {
            ALP.add(p2);
        }

        return ALP;
    }

    public boolean getFirstMoveUsed() {
        return firstMoveUsed;
    }

    public void setFirstMoveUsed(boolean firstMoveUsed) {
        this.firstMoveUsed = firstMoveUsed;
    }

    @Override
    public String getLetter() {
        return "P";
    }

    @Override
    public String getUnicodeLetter() {
        return "\u265F";
    }

    @Override
    public String getName() {
        return Chess.resources.getString(R.string.pawn); //"Pawn";
    }
}
