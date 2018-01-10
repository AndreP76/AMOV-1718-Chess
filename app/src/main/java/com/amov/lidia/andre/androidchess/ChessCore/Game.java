package com.amov.lidia.andre.androidchess.ChessCore;

import android.content.Context;
import android.util.Log;

import com.amov.lidia.andre.androidchess.Chess;
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
import com.amov.lidia.andre.androidchess.Historico;
import com.amov.lidia.andre.androidchess.R;

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
    Board GameBoard;
    Short StartSide = WHITE_SIDE;
    private short CurrentPlayer;
    private Player WhitePlayer;
    private Player BlackPlayer;
    private GamePiece currentSelectedPiece;

    private Context context;
    private Historico itemHistorico;

    private ArrayList<OnPieceMoveListenerInterface> onPieceMoveListener;

    public Game(Player WhitePlayer, Player BlackPlayer, GameMode gm, Historico itemHistorico, Context context) {
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

            this.context = context;

            this.itemHistorico = itemHistorico;
            itemHistorico.setDate();
            itemHistorico.setTitle();
            this.itemHistorico.addJogada(WhitePlayer.getName(), BlackPlayer.getName(),
                    translateGameMode(gameMode),
                    context.getResources().getString(R.string.game_start));

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

    private String translateGameMode(GameMode gm) {
        String str = "";
        switch (gm) {
            case OnlineMultiplayer:
                str = context.getResources().getString(R.string.OnlineString);
                break;
            case LocalMultiplayer:
                str = context.getResources().getString(R.string.MultiPlayerString);
                break;
            case SinglePlayer:
                str = context.getResources().getString(R.string.SinglePlayerString);
                break;
            default:
                break;
        }
        return str;
    }

    private String translateSide(short side) {
        return side == WHITE_SIDE ?
                context.getResources().getString(R.string.white) :
                context.getResources().getString(R.string.black);
    }

    private String getMoveDescription(Move m) {
        String str = m.getPiece().getName() + " " + translateSide(m.getPiece().getSide()) + " ";
        if (m instanceof Attack) {
            Attack a = (Attack) m;
            str += context.getResources().getString(R.string.piece_attack) + " " +
                    a.getAttackedPiece().getName() + " " +
                    translateSide(a.getAttackedPiece().getSide());
        } else {
            str += context.getString(R.string.moved_from) + " " + m.getOrigin().getColString() +
                    m.getOrigin().getLine() + " " +
                    context.getResources().getString(R.string.moved_to) + " " +
                    m.getDestination().getColString() + m.getDestination().getLine();
        }
        return str;
    }

    private void addGameEndItemToHistory(String winner) {
        itemHistorico.addJogada(WhitePlayer.getName(), BlackPlayer.getName(),
                translateGameMode(gameMode),
                context.getResources().getString(R.string.game_end) + " " + winner);
        itemHistorico.setVencedor(winner);
        Chess.addHistorico(itemHistorico);
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
    public int CheckGameEnd() throws NoKingException {
        if (GameBoard.isKingInDanger(WHITE_SIDE)) {//if the white king is in danger
            if (!GameBoard.canKingEscape(WHITE_SIDE)) {
                addGameEndItemToHistory(BlackPlayer.getName());
                return BLACK_SIDE;
            }
            return -1;
        } else if (GameBoard.isKingInDanger(BLACK_SIDE)) {
            if (!GameBoard.canKingEscape(BLACK_SIDE)) {
                addGameEndItemToHistory(WhitePlayer.getName());
                return WHITE_SIDE;
            }
//            return GameBoard.canKingEscape(BLACK_SIDE) ? -1 : WHITE_SIDE;
            return -1;
        } else return -1;
    }

    public ArrayList<GamePiece> getAllPieces() {
        return GameBoard.AllPieces;
    }

    public boolean executeMove(Move m) {
        if (m instanceof Attack) return executeMove((Attack) m);
        if (m.isValidMove()) {
            if (!kingWillBeInDanger(getCurrentPlayerSide(), m)) {
                moveCommons();
                m.getPiece().Move(m.getDestination());
                itemHistorico.addJogada(WhitePlayer.getName(), BlackPlayer.getName(),
                        translateGameMode(gameMode), getMoveDescription(m));
                if (onPieceMoveListener != null) {
                    notifyMoveObservers();
                }
                return true;
            }
        }
        return false;
    }

    private void moveCommons() {
        for (GamePiece gp : SidesPieces[CurrentPlayer == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE]) {
            if (gp instanceof Pawn) {
                Pawn p = (Pawn) gp;
                p.setFirstMoveUsed(false);
            }
        }

        CurrentPlayer = CurrentPlayer == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE;
        setCurrentSelectedPiece(null);
    }

    public boolean executeMove(Attack a) {
        if (a.isValidMove()) {
            if (!kingWillBeInDanger(getCurrentPlayerSide(), a)) {//checks if the king will be endangered after the move
                moveCommons();
                a.getPiece().Move(a.getDestination());
                destroyPiece(a.getAttackedPiece());
                itemHistorico.addJogada(WhitePlayer.getName(), BlackPlayer.getName(),
                        translateGameMode(gameMode), getMoveDescription(a));
                notifyMoveObservers();
                return true;
            }
        }
        return false;
    }

    private boolean kingWillBeInDanger(short currentPlayerSide, Attack a) {
        GamePiece enemy = a.getAttackedPiece();

        destroyPiece(enemy);
        a.getPiece().setPositionInBoard(a.getDestination());

        if (getBoard().isKingInDanger(currentPlayerSide)) {
            a.getPiece().setPositionInBoard(a.getOrigin());
            rebuildPiece(enemy);
            return true;
        }
        return false;
    }

    private boolean kingWillBeInDanger(short currentPlayerSide, Move a) {
        Point dest = a.getDestination();
        Point orig = a.getOrigin();
        GamePiece target = a.getPiece();

        target.setPositionInBoard(dest);//mock the move

        if (getBoard().isKingInDanger(currentPlayerSide)) {//if the king will be in danger, undo the mock move
            target.setPositionInBoard(orig);
            return true;
        }
        return false;
    }

    public void destroyPiece(GamePiece attackedPiece) {
        if (attackedPiece.getSide() == BLACK_SIDE) {
            SidesPieces[BLACK_SIDE].remove(attackedPiece);
            BlackPlayer.removePiece(attackedPiece);
        } else if (attackedPiece.getSide() == WHITE_SIDE) {
            SidesPieces[WHITE_SIDE].remove(attackedPiece);
            WhitePlayer.removePiece(attackedPiece);
        }

        GameBoard.AllPieces.remove(attackedPiece);
    }

    public void rebuildPiece(GamePiece gp) {
        SidesPieces[gp.getSide()].add(gp);
        GameBoard.AllPieces.add(gp);
        if (gp.getSide() == WHITE_SIDE)
            WhitePlayer.addPiece(gp);
        else BlackPlayer.addPiece(gp);

        gp.setPositionInBoard(gp.getPositionInBoard());
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

        /*try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        ArrayList<GamePiece> pieces = p.getPieces();
        ArrayList<Attack> attacks = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();
        for (GamePiece gp : pieces) {
            attacks.addAll(gp.getAttacks());
            moves.addAll(gp.getMoves());
        }

        Random SR = new Random(System.currentTimeMillis());
        boolean moveSuccess = false;
        while (!moveSuccess) {
            boolean shouldAttack = moves.isEmpty() || SR.nextInt(100) > 50;
            if (shouldAttack && attacks.size() > 0) {
                int index = SR.nextInt(attacks.size());
                moveSuccess = executeMove(attacks.get(index));
                attacks.remove(index);
            } else {
                int index = SR.nextInt(moves.size());
                moveSuccess = executeMove(moves.get(index));
                moves.remove(index);
            }
        }
    }

    public void removeOnMoveListener(OnPieceMoveListenerInterface playerInfoFragment) {
        if (this.onPieceMoveListener.contains(playerInfoFragment)) {
            this.onPieceMoveListener.remove(playerInfoFragment);
        }
    }

    public void setPlayer(int side, Player p) {
        if (side == WHITE_SIDE) {
            WhitePlayer = p;
        } else BlackPlayer = p;
    }
}
