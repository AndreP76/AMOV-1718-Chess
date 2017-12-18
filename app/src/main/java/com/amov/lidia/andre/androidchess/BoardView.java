package com.amov.lidia.andre.androidchess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Game;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.GamePiece;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Attack;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Move;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.Point;
import com.amov.lidia.andre.androidchess.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by andre on 12/15/17.
 */

public class BoardView extends View {
    private Paint bluePaint;
    private Paint yellowPaint;
    private Paint blackPaint;
    private Paint brownPaint;
    private Paint whiteSidePaint;
    private Paint blackSidePaint;
    private Paint redPaint;
    private int boardHeight;            //this is all in pixels
    private int boardWidth;             //this is all in pixels
    private int widthPerCol;            //this is all in pixels
    private int heightPerLine;          //this is all in pixels
    private int borderThicknessSides;   //this is all in pixels
    private int borderThicknessTops;    //this is all in pixels
    private Paint greenPaint;

    public BoardView(Context context) {
        super(context);
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
    }

    // 0 1 2 3 4 5 6 7

    // wwbbwwbbwwbbwwbb 0
    // bbwwbbwwbbwwbbww 1
    // wwbbwwbbwwbbwwbb 2
    // bbwwbbwwbbwwbbww 3
    // wwbbwwbbwwbbwwbb 4
    // bbwwbbwwbbwwbbww 5
    // wwbbwwbbwwbbwwbb 6
    // bbwwbbwwbbwwbbww 7

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, boardWidth, boardHeight, blackPaint);
        GamePiece gp = Chess.getCurrentSelectedPiece();
        ArrayList<Move> Movements = null;
        ArrayList<Attack> Attacks = null;
        if (gp != null) {
            Movements = gp.getPossibleMoves();
            Attacks = gp.getPossibleAttacks();
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Paint p = null;
                if (gp != null) {
                    if(i == gp.getPositionInBoard().getLine() && j == gp.getPositionInBoard().getCol())
                        p = greenPaint;
                    else if (Game.ListContainsMove(Attacks, gp.getPositionInBoard(), new Point(i + 1, j + 1)) != null) {
                        p = redPaint;
                    } else if (Game.ListContainsMove(Movements, gp.getPositionInBoard(), new Point(i + 1, j + 1)) != null) {
                        p = bluePaint;
                    }else{
                        p = DefaultResolvePaint(i,j);
                    }
                } else {
                    p = DefaultResolvePaint(i,j);
                }

                canvas.drawRect(j * widthPerCol, i * heightPerLine, j * widthPerCol + widthPerCol, i * heightPerLine + heightPerLine, p);
            }
        }

        Game G = Chess.getCurrentGame();
        ArrayList<GamePiece> AllPieces = G.getAllPieces();
        for(int i = 0; i < AllPieces.size();++i){
            GamePiece p = AllPieces.get(i);
            canvas.drawText(p.getLetter(),p.getPositionInBoard().getCol() * widthPerCol,p.getPositionInBoard().getLine() * heightPerLine,p.getSide() == Game.WHITE_SIDE ? whiteSidePaint : blackSidePaint);
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
        this.boardWidth = w;
        this.boardHeight = h;

        widthPerCol = w / 8;
        heightPerLine = h / 8;
        borderThicknessSides = (int) (w * 0.05);
        borderThicknessTops = (int) (h * 0.05);

        super.onSizeChanged(w, h, oldw, oldh);
    }
}
