package com.amov.lidia.andre.androidchess.ChessCore;

import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.NoKingException;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.King;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.ChessTile;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

public class Board {
    public static final int STANDARD_BOARD_LENGTH = 8;
    public static final int STANDARD_BOARD_WIDTH = 8;
    ArrayList<GamePiece> AllPieces;
    ChessTile[][] Tiles;
    private int BoardLines;
    private int BoardCols;

    Board(int Lines, int Cols){
        this.BoardLines = Lines;
        this.BoardCols = Cols;
        Tiles = new ChessTile[BoardLines][];
        for (int i = 0; i < BoardLines;++i){
            Tiles[i] = new ChessTile[BoardCols];
            for (int j = 0; j < BoardCols;++j){
                Tiles[i][j] = new ChessTile(i,j,this);
            }
        }
    }

    Board(){
        this(STANDARD_BOARD_LENGTH,STANDARD_BOARD_WIDTH);
    }

    public int getBoardCols() {
        return BoardCols;
    }

    public int getBoardLines() {
        return BoardLines;
    }

    public void setPiece(GamePiece GP, ChessTile Tile) throws AlreadyFilledException {
        if(Tile.getPieceInTile() == null)
            Tile.setPieceInTile(GP);
        else throw new AlreadyFilledException();
    }

    public ChessTile getTile(Point position) {
        try {
            return Tiles[position.getLine()][position.getCol()];
        }catch (NullPointerException | ArrayIndexOutOfBoundsException npe){
            return null;
        }
    }

    public GamePiece getPieceInPoint(Point pointOrigin) {
        return Tiles[pointOrigin.getLine()][pointOrigin.getCol()].getPieceInTile();
    }

//    public void toTerminalString(Coordinate originC, Short side) {
//        String AnsiStart = "@|";
//        String AnsiEnd = "|@";
//        ArrayList<Attack> AvailableAttacks;
//        ArrayList<Move> AvailableMoves;
//        if(originC != null && getTile(originC).getPieceInTile() != null) {
//            AvailableMoves = Tiles[originC.getLine()][originC.getCol()].getPieceInTile().getMoves();
//            AvailableAttacks = Tiles[originC.getLine()][originC.getCol()].getPieceInTile().getAttacks();
//        }else{
//            AvailableAttacks = null;
//            AvailableMoves = null;
//        }
//        AnsiConsole.out.print(ansi().eraseScreen());
//        String player = (side == Game.BLACK_SIDE ? "Black" : "White");
//        AnsiConsole.out.println(ansi().bgDefault().fgDefault().a("\n\n\nCurrent player : " + player));
//        AnsiConsole.out.print("  ");
//        for(int i = 0; i < BoardCols;++i){
//            AnsiConsole.out.print((char) ('A' + i));
//        }
//
//        String PieceInTile = " ";
//        Ansi.Color BackgroundColor = Ansi.Color.DEFAULT;
//        boolean brightBG = false;
//        Ansi.Color ForeroundColor = Ansi.Color.DEFAULT;
//
//        for (int i = 0;i < BoardLines;++i){
//            AnsiConsole.out.println();
//            AnsiConsole.out.print(i + " ");
//            for (int j = 0; j < BoardCols;++j){
//                if(ListContainsMove(AvailableAttacks,originC,new Point(i,j)) != null){
//                    BackgroundColor = Ansi.Color.RED;
//                    brightBG = false;
//                }else if(ListContainsMove(AvailableMoves,originC,new Point(i,j)) != null){//this is a possible move
//                    BackgroundColor = Ansi.Color.CYAN;
//                    brightBG = false;
//                }else if(Tiles[i][j].getCoordinatesInBoard().equals(originC)){
//                    BackgroundColor = Ansi.Color.GREEN;
//                    brightBG = false;
//                }else if(Tiles[i][j].getTileColor() == ChessTile.BLACK_TILE){
//                    BackgroundColor = Ansi.Color.YELLOW;
//                    brightBG = false;
//                }else {BackgroundColor = Ansi.Color.YELLOW;brightBG = true;}
//
//                GamePiece GP;
//                if((GP = Tiles[i][j].getPieceInTile()) != null){
//                    PieceInTile = GP.getLetter();
//                    if(GP.getSide() == BLACK_SIDE) ForeroundColor = Ansi.Color.BLUE;
//                    else if(GP.getSide() == WHITE_SIDE) ForeroundColor = Ansi.Color.MAGENTA;
//                }else PieceInTile = " ";
//
//                if(brightBG) AnsiConsole.out.print(ansi().bgBright(BackgroundColor).fg(ForeroundColor).a(PieceInTile));
//                else AnsiConsole.out.print(ansi().bg(BackgroundColor).fg(ForeroundColor).a(PieceInTile));
//            }
//            AnsiConsole.out.print(ansi().bgDefault().fgDefault().a(""));
//        }
//        AnsiConsole.out.println();
//        AnsiConsole.out.flush();
//    }

    public boolean emptyTile(Point point) {
        return Tiles[point.getLine()][point.getCol()].getPieceInTile() == null;
    }


    public boolean isKingInDanger(short Side) throws NoKingException {
        King k = (King) getPieceOfSide(King.class, Side);
        if(k != null) {
            return TileIsAttackedBySide(k.getPositionInBoard(), k.getSide() == BLACK_SIDE ? WHITE_SIDE : BLACK_SIDE);
        }else throw new NoKingException(Side == WHITE_SIDE ? "White " : "Black" + " side has no king! How can this happen ?");
    }

    private GamePiece getPieceOfSide(Class pieceClass, short side) {
        for (GamePiece gp : AllPieces) {
            if (pieceClass.isInstance(gp) && gp.getSide() == side) {
                return gp;
            }
        }
        return null;
    }

    private boolean TileIsAttackedBySide(Point positionInBoard, short side) {
        ArrayList<Attack> OtherSideAttacks = new ArrayList<>();
        for(GamePiece gp : AllPieces){
            if (gp.getSide() == side) {
                OtherSideAttacks.addAll(gp.getAttacks());
            }
        }
        return Game.PointIsAttacked(OtherSideAttacks, positionInBoard) != null;
    }

    public boolean canKingEscape(short Side) {
        King k = null;
        for (GamePiece gp : AllPieces) {
            if (gp instanceof King && gp.getSide() == Side) {
                k = (King) gp;
            }
        }

        if(k != null){
            ArrayList<Move> KingPossibleMoves = k.getMoves();
            for (Move m:
                 KingPossibleMoves) {
                if (!TileIsAttackedBySide(m.getDestination(), k.getSide() == BLACK_SIDE ? WHITE_SIDE : BLACK_SIDE))
                    return true;
            }
            return false;// GG, king is ded, long live the king
        }else throw new NoKingException(Side == WHITE_SIDE ? "White " : "Black" + " side has no king! How can this happen ?");
    }

    public boolean TileIsAttacked(ChessTile t, short side) {
        return TileIsAttackedBySide(t.getCoordinatesInBoard(), side);
    }
}
