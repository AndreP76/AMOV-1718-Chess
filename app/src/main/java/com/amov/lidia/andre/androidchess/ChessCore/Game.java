package com.amov.lidia.andre.androidchess.ChessCore;

import com.amov.lidia.andre.androidchess.ChessCore.*;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.*;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.*;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.*;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {
    public static final short WHITE_SIDE = 0;
    public static final short BLACK_SIDE = 1;

    public static final short STANDARD_PAWNS_COUNT = 8;

    ArrayList<GamePiece>[] SidesPieces;
    ArrayList<GamePiece>[] CapturedPieces;
    Board GameBoard;
    Short StartSide = WHITE_SIDE;
    private short CurrentPlayer;

    Game() {
        CurrentPlayer = StartSide;
        GameBoard = new Board();
        GameBoard.AllPieces = new ArrayList<>();
        SidesPieces = new ArrayList[2];
        SidesPieces[0] = new ArrayList<GamePiece>();
        SidesPieces[1] = new ArrayList<GamePiece>();

        CapturedPieces = new ArrayList[2];
        CapturedPieces[0] = new ArrayList<GamePiece>();
        CapturedPieces[1] = new ArrayList<GamePiece>();
        try {
            for (int i = 0; i < STANDARD_PAWNS_COUNT; ++i) {
                Pawn WhitePawn = new Pawn(GameBoard, new Point(1, i), WHITE_SIDE);
                Pawn BlackPawn = new Pawn(GameBoard, new Point(GameBoard.getBoardLines() - 2, i), BLACK_SIDE);
                SidesPieces[WHITE_SIDE].add(WhitePawn);
                SidesPieces[BLACK_SIDE].add(BlackPawn);
            }
            Rook WhiteRook1 = new Rook(GameBoard, new Point(0, 0), WHITE_SIDE);
            Rook WhiteRook2 = new Rook(GameBoard, new Point(0, GameBoard.getBoardCols() - 1), WHITE_SIDE);
            Bishop WhiteBishop1 = new Bishop(GameBoard, new Point(0, 2), WHITE_SIDE);
            Bishop WhiteBishop2 = new Bishop(GameBoard, new Point(0, GameBoard.getBoardCols() - 3), WHITE_SIDE);
            Knight WhiteKnight1 = new Knight(GameBoard, new Point(0, 1), WHITE_SIDE);
            Knight WhiteKnight2 = new Knight(GameBoard, new Point(0, GameBoard.getBoardCols() - 2), WHITE_SIDE);
            King WhiteKing = new King(GameBoard, new Point(0, 3), WHITE_SIDE);
            Queen WhiteQueen = new Queen(GameBoard, new Point(0, 4), WHITE_SIDE);

            Rook BlackRook1 = new Rook(GameBoard, new Point(GameBoard.getBoardLines() - 1, 0), BLACK_SIDE);
            Rook BlackRook2 = new Rook(GameBoard, new Point(GameBoard.getBoardLines() - 1, GameBoard.getBoardCols() - 1), BLACK_SIDE);
            Bishop BlackBishop1 = new Bishop(GameBoard, new Point(GameBoard.getBoardLines() - 1, 2), BLACK_SIDE);
            Bishop BlackBishop2 = new Bishop(GameBoard, new Point(GameBoard.getBoardLines() - 1, GameBoard.getBoardCols() - 3), BLACK_SIDE);
            Knight BlackKnight1 = new Knight(GameBoard, new Point(GameBoard.getBoardLines() - 1, 1), BLACK_SIDE);
            Knight BlackKnight2 = new Knight(GameBoard, new Point(GameBoard.getBoardLines() - 1, GameBoard.getBoardCols() - 2), BLACK_SIDE);
            King BlackKing = new King(GameBoard, new Point(GameBoard.getBoardLines() - 1, 3), BLACK_SIDE);
            Queen BlackQueen = new Queen(GameBoard, new Point(GameBoard.getBoardLines() - 1, 4), BLACK_SIDE);

            SidesPieces[BLACK_SIDE].add(BlackRook1);
            SidesPieces[WHITE_SIDE].add(WhiteRook1);
            SidesPieces[BLACK_SIDE].add(BlackRook2);
            SidesPieces[WHITE_SIDE].add(WhiteRook2);
            SidesPieces[BLACK_SIDE].add(BlackBishop1);
            SidesPieces[WHITE_SIDE].add(WhiteBishop1);
            SidesPieces[BLACK_SIDE].add(BlackBishop2);
            SidesPieces[WHITE_SIDE].add(WhiteBishop2);
            SidesPieces[BLACK_SIDE].add(BlackKnight1);
            SidesPieces[WHITE_SIDE].add(WhiteKnight1);
            SidesPieces[BLACK_SIDE].add(BlackKnight2);
            SidesPieces[WHITE_SIDE].add(WhiteKnight2);
            SidesPieces[BLACK_SIDE].add(BlackKing);
            SidesPieces[WHITE_SIDE].add(WhiteKing);
            SidesPieces[BLACK_SIDE].add(BlackQueen);
            SidesPieces[WHITE_SIDE].add(WhiteQueen);

            GameBoard.AllPieces.addAll(SidesPieces[BLACK_SIDE]);
            GameBoard.AllPieces.addAll(SidesPieces[WHITE_SIDE]);

        } catch (AlreadyFilledException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Move> T ListContainsMove(ArrayList<T> M, Point originPoint, Point destinationPoint) {
        if (M == null) return null;
        for (int i = 0; i < M.size(); ++i) {
            if (M.get(i).getDestination().equals(destinationPoint) && M.get(i).getOrigin().equals(originPoint)) {
                return M.get(i);
            }
        }
        return null;
    }

    public Board getBoard() {
        return GameBoard;
    }

    public boolean Move(Point pointOrigin, Point pointDestination) {
        GamePiece Origin = GameBoard.getPieceInPoint(pointOrigin);
        if (Origin != null) {//valid piece
            if (Origin.getSide() == CurrentPlayer) {
                ArrayList<Move> PieceAvailableMoves = Origin.getPossibleMoves();
                Move destinationMove = ListContainsMove(PieceAvailableMoves, Origin.getPositionInBoard(), pointDestination);
                if (destinationMove != null) {//valid move
                    boolean ret = Origin.Move(destinationMove.getDestination());
                    if (ret) {
                        if (CurrentPlayer == BLACK_SIDE) CurrentPlayer = WHITE_SIDE;
                        else CurrentPlayer = BLACK_SIDE;
                    }
                    return ret;
                } else return false;
            } else return false;
        } else return false;
    }

    public Short getCurrentPlayer() {
        return CurrentPlayer;
    }

    /**
     * @return -1 if the game is still running, or the winning side if the game ends
     */
    public int CheckGameEnd() throws NoKingException{
            if(GameBoard.isKingInDanger(WHITE_SIDE)){
                return GameBoard.canKingEscape(WHITE_SIDE) ? -1 : BLACK_SIDE;
            }else if(GameBoard.isKingInDanger(BLACK_SIDE)){
                return GameBoard.canKingEscape(BLACK_SIDE) ? -1 : WHITE_SIDE;
            }else return -1;
    }

    public ArrayList<GamePiece> getAllPieces() {
        return GameBoard.AllPieces;
    }
}
