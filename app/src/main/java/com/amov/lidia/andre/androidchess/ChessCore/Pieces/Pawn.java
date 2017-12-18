package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;

public class Pawn extends GamePiece {
    boolean firstMove = true;
    boolean firstMoveUsed = false;

    public Pawn(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Pawn(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> ALM = new ArrayList<>();
        Point pointInGame = this.getPositionInBoard();
        int dist = 2;
        if(firstMove) dist = 3;
        if(this.getSide() == BLACK_SIDE){//Black side goes north and only north
            for(int i = pointInGame.getLine()-1; i > pointInGame.getLine()-dist;--i){
                if(this.getGameBoard().getTile(new Point(i,pointInGame.getCol())).getPieceInTile() != null) break;
                else ALM.add(new Move(this.getPositionInBoard(),new Point(i,pointInGame.getCol()),"Pawn",this.getLetter(),this.getSide()));
            }
        }
        if(this.getSide() == Game.WHITE_SIDE){//White side goes south and only north
            for(int i = pointInGame.getLine()+1; i < pointInGame.getLine()+dist;++i){
                if(this.getGameBoard().getTile(new Point(i,pointInGame.getCol())).getPieceInTile() != null) break;
                else ALM.add(new Move(this.getPositionInBoard(),new Point(i,pointInGame.getCol()),"Pawn",this.getLetter(),this.getSide()));
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
        }
        return r;
    }

    @Override
    public ArrayList<Attack> getPossibleAttacks() {
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

    public boolean getFirstMoveUsed() {
        return firstMoveUsed;
    }

    @Override
    public String getLetter() {
        return "P";
    }

    @Override
    public String getName() {
        return "Pawn";
    }
}
