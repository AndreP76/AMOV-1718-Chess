package com.amov.lidia.andre.androidchess.CustomDialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Bishop;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Knight;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Pawn;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Queen;
import com.amov.lidia.andre.androidchess.ChessCore.Pieces.Rook;
import com.amov.lidia.andre.androidchess.R;

/**
 * Created by andre on 1/10/18.
 */

public class PromotionDialog extends Dialog {
    Pawn promotedPawn;

    Button btnRook;
    Button btnBishop;
    Button btnKnight;
    Button btnQueen;

    public PromotionDialog(@NonNull Context context, Pawn p) {
        super(context);
        promotedPawn = p;
    }

    public PromotionDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected PromotionDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_promote_pawn);

        btnBishop = findViewById(R.id.btnBishop);
        btnRook = findViewById(R.id.btnRook);
        btnKnight = findViewById(R.id.btnKnight);
        btnQueen = findViewById(R.id.btnQueen);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        btnBishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chess.getCurrentGame().destroyPiece(promotedPawn);
                Bishop bp = new Bishop(Chess.getCurrentGame().getBoard(), promotedPawn.getPositionInBoard(), promotedPawn.getSide());
                Chess.getCurrentGame().rebuildPiece(bp);
                dismiss();
            }
        });

        btnQueen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chess.getCurrentGame().destroyPiece(promotedPawn);
                Queen bp = new Queen(Chess.getCurrentGame().getBoard(), promotedPawn.getPositionInBoard(), promotedPawn.getSide());
                Chess.getCurrentGame().rebuildPiece(bp);
                dismiss();
            }
        });

        btnKnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chess.getCurrentGame().destroyPiece(promotedPawn);
                Knight bp = new Knight(Chess.getCurrentGame().getBoard(), promotedPawn.getPositionInBoard(), promotedPawn.getSide());
                Chess.getCurrentGame().rebuildPiece(bp);
                dismiss();
            }
        });

        btnRook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chess.getCurrentGame().destroyPiece(promotedPawn);
                Rook bp = new Rook(Chess.getCurrentGame().getBoard(), promotedPawn.getPositionInBoard(), promotedPawn.getSide());
                Chess.getCurrentGame().rebuildPiece(bp);
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
