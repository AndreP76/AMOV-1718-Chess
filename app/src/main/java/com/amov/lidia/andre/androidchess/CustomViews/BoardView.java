package com.amov.lidia.andre.androidchess.CustomViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Board;
import com.amov.lidia.andre.androidchess.ChessCore.Game;
import com.amov.lidia.andre.androidchess.ChessCore.OnPieceMoveListenerInterface;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.King;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;
import com.amov.lidia.andre.androidchess.R;

import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

/**
 * Created by andre on 12/15/17.
 */

public class BoardView extends View implements OnPieceMoveListenerInterface {
    private Paint whitePaint;
    private Paint bluePaint;
    private Paint yellowPaint;
    private Paint blackPaint;
    private Paint brownPaint;
    private Paint whiteSidePaint;
    private Paint blackSidePaint;
    private Paint redPaint;
    private Paint textPaint;

    private int boardHeight;            //this is all in pixels
    private int boardWidth;             //this is all in pixels
    private int widthPerCol;            //this is all in pixels
    private int heightPerLine;          //this is all in pixels
    private int borderThicknessSides;   //this is all in pixels
    private int borderThicknessTops;    //this is all in pixels
    private int textHorizontalOffset;    //this is all in pixels
    private int textVerticalOffset;    //this is all in pixels
    private Paint greenPaint;
    private int borderVerticalTextOffset;
    private int borderHorizontalTextOffset;
    private int boardStartX;
    private int boardStartY;

    private int currentIndex = -1;

    private Game currentGame;

    private boolean whiteSideWon;
    private boolean blackSideWon;

    private Context ctx;
    private boolean whiteInDanger;
    private boolean blackInDanger;

    public BoardView(Context context) {
        super(context);
        this.ctx = context;
        whiteSideWon = blackSideWon = false;
        currentGame = Chess.getCurrentGame();
        currentGame.addMoveListener(this);
        yellowPaint = new Paint(Paint.DITHER_FLAG);
        yellowPaint.setColor(ContextCompat.getColor(context, R.color.colorYellowTile));
        yellowPaint.setStyle(Paint.Style.FILL);

        brownPaint = new Paint(Paint.DITHER_FLAG);
        brownPaint.setColor(ContextCompat.getColor(context, R.color.colorBrownTile));
        brownPaint.setStyle(Paint.Style.FILL);

        blackPaint = new Paint(Paint.DITHER_FLAG);
        blackPaint.setColor(ContextCompat.getColor(context, R.color.colorBoardBorder));
        blackPaint.setStyle(Paint.Style.FILL);

        redPaint = new Paint(Paint.DITHER_FLAG);
        redPaint.setColor(ContextCompat.getColor(context, R.color.colorAttackedTile));
        redPaint.setStyle(Paint.Style.FILL);

        bluePaint = new Paint(Paint.DITHER_FLAG);
        bluePaint.setColor(ContextCompat.getColor(context, R.color.colorMovingTile));
        bluePaint.setStyle(Paint.Style.FILL);

        greenPaint = new Paint(Paint.DITHER_FLAG);
        greenPaint.setColor(ContextCompat.getColor(context, R.color.colorSelectedTile));
        greenPaint.setStyle(Paint.Style.FILL);

        whiteSidePaint = new Paint(Paint.DITHER_FLAG);
        whiteSidePaint.setColor(ContextCompat.getColor(context, R.color.colorWhiteSide));
        whiteSidePaint.setStyle(Paint.Style.FILL);

        blackSidePaint = new Paint(Paint.DITHER_FLAG);
        blackSidePaint.setColor(ContextCompat.getColor(context, R.color.colorBlackSide));
        blackSidePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.DITHER_FLAG);
        textPaint.setColor(ContextCompat.getColor(context, R.color.colorBoardText));
        textPaint.setStyle(Paint.Style.FILL);

        whitePaint = new Paint(Paint.DITHER_FLAG);
        whitePaint.setColor(ContextCompat.getColor(context, R.color.colorWhite));
        whitePaint.setStyle(Paint.Style.FILL);
    }

    // 0 1 2 3 4 5 6 7
    //
    // wwbbwwbbwwbbwwbb   0
    // bbwwbbwwbbwwbbww   1
    // wwbbwwbbwwbbwwbb   2
    // bbwwbbwwbbwwbbww   3
    // wwbbwwbbwwbbwwbb   4
    // bbwwbbwwbbwwbbww   5
    // wwbbwwbbwwbbwwbb   6
    // bbwwbbwwbbwwbbww   7

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(boardStartX, boardStartY, boardStartX + boardWidth, boardStartY + boardHeight, currentGame.getCurrentPlayerSide() == WHITE_SIDE ? blackPaint : whitePaint);
        int textXLeft = boardStartX + borderHorizontalTextOffset;
        int textXRight = boardStartX + this.boardWidth - borderThicknessSides + borderHorizontalTextOffset;
        int textY = boardStartY + borderThicknessTops - borderVerticalTextOffset;
        int textYBottom = boardStartY + boardHeight - borderVerticalTextOffset;
        for (int i = 0; i < 8; ++i) {
            int textX = boardStartX + borderThicknessSides - textHorizontalOffset + (widthPerCol / 2) + ((widthPerCol) * (i));
            int textYSides = boardStartY + borderThicknessTops - textVerticalOffset + (heightPerLine / 2) + ((heightPerLine) * (i));
            textPaint.setColor(currentGame.getCurrentPlayerSide() == WHITE_SIDE ? ContextCompat.getColor(ctx, R.color.colorWhite) : ContextCompat.getColor(ctx, R.color.colorBlack));
            canvas.drawText((char) ('A' + i) + "", textX, textY, textPaint);
            canvas.drawText((char) ('A' + i) + "", textX, textYBottom, textPaint);
            canvas.drawText((i + 1) + "", textXLeft, textYSides, textPaint);
            canvas.drawText((i + 1) + "", textXRight, textYSides, textPaint);
        }

        GamePiece gp = Chess.getCurrentSelectedPiece();
        ArrayList<Move> Movements = null;
        ArrayList<Attack> Attacks = null;
        if (gp != null) {
            Movements = gp.getMoves();
            Attacks = gp.getAttacks();
        }

        for (int i = 0; i < 8; ++i) {//the standard render loop, renders the tiles
            for (int j = 0; j < 8; ++j) {
                int startX = j * widthPerCol + borderThicknessSides + boardStartX;
                int startY = i * heightPerLine + borderThicknessTops + boardStartY;
                canvas.drawRect(startX, startY, startX + widthPerCol, startY + heightPerLine, DefaultResolvePaint(i, j));
            }
        }


        if (gp != null) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    Paint p = null;
                    if (i == gp.getPositionInBoard().getLine() && j == gp.getPositionInBoard().getCol())
                        p = greenPaint;
                    else if (Game.PointIsAttacked(Attacks, new Point(i, j)) != null) {
                        p = redPaint;
                    } else if (Game.PointCanBeMovedTo(Movements, new Point(i, j)) != null) {
                        p = bluePaint;
                    } else continue;

                    int startX = (int) (j * widthPerCol + borderThicknessSides + boardStartX + widthPerCol * 0.1);
                    int startY = (int) (i * heightPerLine + borderThicknessTops + boardStartY + heightPerLine * 0.1);
                    canvas.drawRect(startX, startY, startX + widthPerCol * 0.8f, startY + heightPerLine * 0.8f, p);
                }
            }
        }

        if (whiteInDanger) {
            Point whiteKingPos = currentGame.getBoard().getPieceOfSide(King.class, WHITE_SIDE).getPositionInBoard();
            int borderStartX = boardStartX + whiteKingPos.getCol() * widthPerCol + borderThicknessSides;
            int borderStartY = boardStartY + (whiteKingPos.getLine()) * heightPerLine + borderThicknessTops;
            int tileStartX = (int) (borderStartX + widthPerCol * 0.1);
            int tileStartY = (int) (borderStartY + heightPerLine * 0.1);

            canvas.drawRoundRect(borderStartX, borderStartY, borderStartX + widthPerCol, borderStartY + heightPerLine, widthPerCol, heightPerLine, redPaint);
            canvas.drawRoundRect(tileStartX, tileStartY, tileStartX + widthPerCol * 0.8f, tileStartY + heightPerLine * 0.8f, widthPerCol * 0.8f, heightPerLine * 0.8f, DefaultResolvePaint(whiteKingPos.getLine(), whiteKingPos.getCol()));
        }

        if (blackInDanger) {
            Point KingPos = currentGame.getBoard().getPieceOfSide(King.class, BLACK_SIDE).getPositionInBoard();
            int borderStartX = boardStartX + KingPos.getCol() * widthPerCol + borderThicknessSides;
            int borderStartY = boardStartY + (KingPos.getLine()) * heightPerLine + borderThicknessTops;
            int tileStartX = (int) (borderStartX + widthPerCol * 0.1);
            int tileStartY = (int) (borderStartY + heightPerLine * 0.1);

            canvas.drawRoundRect(borderStartX, borderStartY, borderStartX + widthPerCol, borderStartY + heightPerLine, widthPerCol * 0.8f, heightPerLine * 0.8f, redPaint);
            canvas.drawRoundRect(tileStartX, tileStartY, tileStartX + widthPerCol * 0.8f, tileStartY + heightPerLine * 0.8f, widthPerCol * 0.8f, heightPerLine * 0.8f, DefaultResolvePaint(KingPos.getLine(), KingPos.getCol()));
        }

        Game G = Chess.getCurrentGame();
        ArrayList<GamePiece> AllPieces = G.getAllPieces();
        for (int i = 0; i < AllPieces.size(); ++i) {
            GamePiece p = AllPieces.get(i);
            int startX = boardStartX + (p.getPositionInBoard().getCol()) * widthPerCol + borderThicknessSides + textHorizontalOffset;
            int startY = boardStartY + (p.getPositionInBoard().getLine() + 1) * heightPerLine + borderThicknessTops + textVerticalOffset;
            canvas.drawText(p.getUnicodeLetter(), startX, startY, p.getSide() == WHITE_SIDE ? whiteSidePaint : blackSidePaint);
        }

        if (whiteSideWon || blackSideWon) {
            canvas.drawRect(0, boardHeight * 0.25f, boardWidth, boardHeight * 0.75f, whiteSideWon ? blackSidePaint : whiteSidePaint);
            canvas.drawText(whiteSideWon ? Chess.resources.getString(R.string.white_victory) : Chess.resources.getString(R.string.black_victory), 0, boardHeight * 0.5f, whiteSideWon ? whiteSidePaint : blackSidePaint);
        }
    }

    private Paint DefaultResolvePaint(int i, int j) {
        if (i % 2 == 0) {//linhas pares
            if (j % 2 == 0) {//colunas pares
                return yellowPaint;
            } else
                return brownPaint;
        } else {
            if (j % 2 == 0) {//colunas pares
                return brownPaint;
            } else
                return yellowPaint;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.boardWidth = this.boardHeight = Math.min(w, h);//força o campo a ser quadrado ao assumir o menor espaço possivel como o tamanho de cada lado do campo

        boardStartX = (w - this.boardWidth) / 2;
        boardStartY = (h - this.boardHeight) / 2;

        borderThicknessSides = (int) (this.boardWidth * 0.05);
        borderThicknessTops = (int) (this.boardHeight * 0.05);

        widthPerCol = (this.boardWidth - (borderThicknessSides * 2)) / 8;
        heightPerLine = (this.boardHeight - (borderThicknessTops * 2)) / 8;
        whiteSidePaint.setTextSize(widthPerCol);
        blackSidePaint.setTextSize(widthPerCol);
        textPaint.setTextSize(Math.min(borderThicknessSides, borderThicknessTops));
        textHorizontalOffset = (int) (this.widthPerCol * 0.15);
        textVerticalOffset = (int) (this.heightPerLine * -0.15);

        borderVerticalTextOffset = (int) (borderThicknessTops * 0.15);
        borderHorizontalTextOffset = (int) (borderThicknessSides * 0.15);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //*@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);//not interested, thanks

        if (whiteSideWon || blackSideWon)
            ((Activity) getContext()).finish();//horrible hack

        int X = (int) event.getX();
        int Y = (int) event.getY();

        int LineIndex = IntervalInterpolate(Y, boardHeight, 8);
        int ColIndex = IntervalInterpolate(X, boardWidth, 8);
        Log.d("[BOARDVIEW] :: ", "Line index : " + LineIndex + "\tColIndex : " + ColIndex);

        if (ColIndex < 0 || ColIndex > 7 || LineIndex > 7 || LineIndex < 0)
            return true;//Not a valid line

        if (Chess.getCurrentSelectedPiece() != null) {//selecting a destination
            Move m = Game.PointCanBeMovedTo(Chess.getCurrentSelectedPiece().getMoves(), new Point(LineIndex, ColIndex));
            if (m != null) {//this is a valid move
                Chess.getCurrentGame().executeMove(m);
                invalidate();
                return true;
            } else {//this may be an attack
                Attack a = Game.PointIsAttacked(Chess.getCurrentSelectedPiece().getAttacks(), new Point(LineIndex, ColIndex));
                if (a != null) {//this in an attack
                    Chess.getCurrentGame().executeMove(a);
                    invalidate();
                    return true;
                } else {//this is an invalid move
                    Board b = currentGame.getBoard();
                    if (!b.emptyTile(new Point(LineIndex, ColIndex))) {//but the player clicked a piece
                        GamePiece gp = b.getTile(new Point(LineIndex, ColIndex)).getPieceInTile();
                        if (gp.getSide() == Chess.getCurrentGame().getCurrentPlayerSide()) {//one of his. switch selection
                            Chess.setCurrentSelectedPiece(gp);
                            invalidate();
                            return true;
                        } else {//an enemy piece. Deselect the piece
                            Chess.setCurrentSelectedPiece(null);
                            invalidate();
                            return true;
                        }
                    } else {//and the player clicked an empty tile. Deselect the tile
                        Chess.setCurrentSelectedPiece(null);
                        invalidate();
                        return true;
                    }
                }
            }
        } else {//selecting the starting piece
            Board b = currentGame.getBoard();
            if (!b.emptyTile(new Point(LineIndex, ColIndex))) {//there's a piece here
                GamePiece gp = b.getTile(new Point(LineIndex, ColIndex)).getPieceInTile();
                if (gp.getSide() == Chess.getCurrentGame().getCurrentPlayerSide()) {
                    Chess.setCurrentSelectedPiece(gp);
                    invalidate();
                    return true;
                } else {//clicked the oponents piece. Do nothing
                    Chess.setCurrentSelectedPiece(null);
                    return true;
                }
            }
            return true;//clicked an invalid place
        }
    }

    private int IntervalInterpolate(int pos, int intervalLength, int intervals) {
        int widthPerInterval = intervalLength / intervals;
        for (int i = 0; i <= intervals - 1; ++i) {
            int beginingOfInterval = widthPerInterval * i;
            int endOfInterval = widthPerInterval * (i + 1);
            if (pos >= beginingOfInterval && pos < endOfInterval) return i;
        }
        return -1;//Not in interval
    }

    @Override
    public void onMove() {
        int end = currentGame.CheckGameEnd();
        if (end > -1) {
            if (end == WHITE_SIDE) {
                Log.v("[GAME] ::", "White side has won!");
                whiteSideWon = true;
            } else {
                Log.v("[GAME] ::", "Black side has won!");
                blackSideWon = true;
            }
        } else if (currentGame.getWhitePlayer().isAI() && currentGame.getBlackPlayer().isAI()) {
            ((Activity) getContext()).finish();
        } else {//game not ended
            whiteInDanger = currentGame.getBoard().isKingInDanger(WHITE_SIDE);
            blackInDanger = currentGame.getBoard().isKingInDanger(BLACK_SIDE);

            if (currentGame.getCurrentPlayer().isAI()) {
                currentGame.executeAIMove(currentGame.getCurrentPlayer().getSide());
            }
        }
    }
}
