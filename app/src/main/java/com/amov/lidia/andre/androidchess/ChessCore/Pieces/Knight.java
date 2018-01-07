package com.amov.lidia.andre.androidchess.ChessCore.Pieces;

import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;

import java.util.ArrayList;

public class Knight extends GamePiece {
    public Knight(Board B, Point Position, short Side) throws AlreadyFilledException {
        super(B, Position, Side);
    }

    Knight(Board B, short Side) {
        super(B, Side);
    }

    @Override
    public ArrayList<Move> getMoves() {
        Point p = this.getPositionInBoard();
        ArrayList<Move> ALM = new ArrayList<>();
        if (this.getGameBoard().getTile(new Point(p.getLine() - 2, p.getCol() - 1)) != null && this.getGameBoard().getTile(new Point(p.getLine() - 2, p.getCol() - 1)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() - 2, p.getCol() - 1)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() - 2, p.getCol() + 1)) != null && this.getGameBoard().getTile(new Point(p.getLine() - 2, p.getCol() + 1)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() - 2, p.getCol() + 1)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() + 2, p.getCol() - 1)) != null && this.getGameBoard().getTile(new Point(p.getLine() + 2, p.getCol() - 1)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() + 2, p.getCol() - 1)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() + 2, p.getCol() + 1)) != null && this.getGameBoard().getTile(new Point(p.getLine() + 2, p.getCol() + 1)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() + 2, p.getCol() + 1)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() - 1, p.getCol() - 2)) != null && this.getGameBoard().getTile(new Point(p.getLine() - 1, p.getCol() - 2)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() - 1, p.getCol() - 2)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() + 1, p.getCol() - 2)) != null && this.getGameBoard().getTile(new Point(p.getLine() + 1, p.getCol() - 2)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() + 1, p.getCol() - 2)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() - 1, p.getCol() + 2)) != null && this.getGameBoard().getTile(new Point(p.getLine() - 1, p.getCol() + 2)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() - 1, p.getCol() + 2)));
        }
        if (this.getGameBoard().getTile(new Point(p.getLine() + 1, p.getCol() + 2)) != null && this.getGameBoard().getTile(new Point(p.getLine() + 1, p.getCol() + 2)).getPieceInTile() == null) {
            ALM.add(new Move(this, new Point(p.getLine() + 1, p.getCol() + 2)));
        }
        return ALM;
    }

    @Override
    public ArrayList<Attack> getAttacks() {
        Point p = this.getPositionInBoard();
        ArrayList<Attack> ALA = new ArrayList<>();

        Point p1;
        p1 = new Point(p.getLine()-2,p.getCol()-1);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()-2,p.getCol()+1);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()+2,p.getCol()-1);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()+2,p.getCol()+1);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()-1,p.getCol()-2);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()+1,p.getCol()-2);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()-1,p.getCol()+2);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        p1 = new Point(p.getLine()+1,p.getCol()+2);if(this.getGameBoard().getTile(p1) != null && this.getGameBoard().getTile(p1).getPieceInTile() != null && this.getGameBoard().getTile(p1).getPieceInTile().getSide() != this.getSide()){ALA.add(new Attack(this,this.getGameBoard().getTile(p1).getPieceInTile()));}
        return ALA;
    }

    public ArrayList<Point> getPossibleAttacks() {
        return null;
    }

    @Override
    public String getLetter() {
        return "H";
    }

    @Override
    public String getUnicodeLetter() {
        return "\u265E";
    }

    @Override
    public String getName() {
        return "Knight";
    }
}
