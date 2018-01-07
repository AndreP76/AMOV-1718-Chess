package com.amov.lidia.andre.androidchess.ChessCore;

import android.util.Log;

import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.AlreadyFilledException;
import com.amov.lidia.andre.androidchess.ChessCore.Exceptions.NoKingException;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Bishop;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.King;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Knight;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Pawn;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Queen;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Rook;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.GameMode;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Game extends Observable implements Serializable {
    public static final short WHITE_SIDE = 0;
    public static final short BLACK_SIDE = 1;

    public static final short STANDARD_PAWNS_COUNT = 8;
    public GameMode gameMode;

    ArrayList<GamePiece>[] SidesPieces;
    ArrayList<GamePiece>[] CapturedPieces;
    Board GameBoard;
    Short StartSide = WHITE_SIDE;
    private short CurrentPlayer;
    private Player WhitePlayer;
    private Player BlackPlayer;
    private GamePiece currentSelectedPiece;

    private ArrayList<OnPieceMoveListenerInterface> onPieceMoveListener;

    public Game(Player WhitePlayer, Player BlackPlayer, GameMode gm) {
        Log.d("[GAME CONSTRUCTOR] :: ", "New game created!");
        this.gameMode = gm;
        this.WhitePlayer = WhitePlayer == null ? new Player(WHITE_SIDE) : WhitePlayer;
        this.BlackPlayer = BlackPlayer == null ? new Player(BLACK_SIDE) : BlackPlayer;
        onPieceMoveListener = new ArrayList<>();
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
            assert WhitePlayer != null;
            WhitePlayer.addPieces(SidesPieces[WHITE_SIDE]);
            assert BlackPlayer != null;
            BlackPlayer.addPieces(SidesPieces[BLACK_SIDE]);
        } catch (AlreadyFilledException e) {
            e.printStackTrace();
        }
    }

    public static Move PointCanBeMovedTo(ArrayList<Move> M, Point destinationPoint) {
        if (M == null) return null;
        for (int i = 0; i < M.size(); ++i) {
            if (M.get(i).getDestination().equals(destinationPoint)) {
                return M.get(i);
            }
        }
        return null;
    }

    public static Attack PieceIsAttacked(ArrayList<Attack> M, GamePiece piece) {
        for (int i = 0; i < M.size(); ++i) {
            Attack a = M.get(i);
            if (a.getAttackedPiece().equals(piece)) {
                return a;
            }
        }
        return null;
    }

    public static Attack PointIsAttacked(ArrayList<Attack> M, Point p) {
        for (int i = 0; i < M.size(); ++i) {
            Attack a = M.get(i);
            if (a.getAttackedPiece().getPositionInBoard().equals(p)) {
                return a;
            }
        }
        return null;
    }

    public Board getBoard() {
        return GameBoard;
    }

    public Player getCurrentPlayer() {
        return CurrentPlayer == WHITE_SIDE ? WhitePlayer : BlackPlayer;
    }

    /**
     * @return -1 if the game is still running, or the winning side if the game ends
     */
    public int CheckGameEnd() throws NoKingException{
        if (GameBoard.isKingInDanger(WHITE_SIDE)) {//if the white king is in danger
            return GameBoard.canKingEscape(WHITE_SIDE) ? -1 : BLACK_SIDE;
        } else if (GameBoard.isKingInDanger(BLACK_SIDE)) {
            return GameBoard.canKingEscape(BLACK_SIDE) ? -1 : WHITE_SIDE;
        } else return -1;
    }

    public ArrayList<GamePiece> getAllPieces() {
        return GameBoard.AllPieces;
    }

    public boolean executeMove(Move m){
        if (m instanceof Attack) return executeMove((Attack) m);
        if(m.isValidMove()) {
            moveCommons();
            m.getPiece().Move(m.getDestination());
            if (onPieceMoveListener != null) {
                notifyMoveObservers();
            }
            return true;
        }
        return false;
    }

    private void moveCommons() {
        CurrentPlayer = CurrentPlayer == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE;
        setCurrentSelectedPiece(null);
    }

    public boolean executeMove(Attack a){
        if(a.isValidMove()){
            moveCommons();
            a.getPiece().Move(a.getDestination());
            destroyPiece(a.getAttackedPiece());
            notifyMoveObservers();
            return true;
        }
        return false;
    }

    private void destroyPiece(GamePiece attackedPiece) {
        if(attackedPiece.getSide() == BLACK_SIDE){SidesPieces[BLACK_SIDE].remove(attackedPiece);BlackPlayer.removePiece(attackedPiece);}
        else if(attackedPiece.getSide() == WHITE_SIDE) {SidesPieces[WHITE_SIDE].remove(attackedPiece);WhitePlayer.removePiece(attackedPiece);}

        GameBoard.AllPieces.remove(attackedPiece);
    }

    public GamePiece getCurrentSelectedPiece() {
        return currentSelectedPiece;
    }

    public void setCurrentSelectedPiece(GamePiece currentSelectedPiece) {
        this.currentSelectedPiece = currentSelectedPiece;
    }

    public short getCurrentPlayerSide() {
        return CurrentPlayer;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Player getWhitePlayer() {
        return WhitePlayer;
    }

    public Player getBlackPlayer() {
        return BlackPlayer;
    }

    public void addMoveListener(OnPieceMoveListenerInterface boardView) {
        if (!onPieceMoveListener.contains(boardView))
            onPieceMoveListener.add(boardView);
    }

    private void notifyMoveObservers() {
        for (int i = 0; i < onPieceMoveListener.size(); ++i) {
            onPieceMoveListener.get(i).onMove();
        }
    }

    public void executeAIMove(int side) {
        Player p = getCurrentPlayer();

        ArrayList<GamePiece> pieces = p.getPieces();
        ArrayList<Attack> attacks = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();
        for (GamePiece gp : pieces) {
            attacks.addAll(gp.getAttacks());
            moves.addAll(gp.getMoves());
        }

        Random SR = new Random(System.currentTimeMillis());
        boolean shouldAttack = SR.nextInt(100) > 50;
        if (shouldAttack && attacks.size() > 0) {
            executeMove(attacks.get(SR.nextInt(attacks.size())));
        } else {
            executeMove(moves.get(SR.nextInt(moves.size())));
        }
    }
}
