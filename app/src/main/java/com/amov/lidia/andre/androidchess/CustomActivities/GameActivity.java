package com.amov.lidia.andre.androidchess.CustomActivities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Game;
import com.amov.lidia.andre.androidchess.ChessCore.Player;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.GameMode;
import com.amov.lidia.andre.androidchess.CustomFragments.GameFragment;
import com.amov.lidia.andre.androidchess.CustomFragments.PlayerInfoFragment;
import com.amov.lidia.andre.androidchess.Historico;
import com.amov.lidia.andre.androidchess.R;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

public class GameActivity extends Activity {

    Historico itemHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle b = getIntent().getBundleExtra("args");
        if (b == null)
            b = getIntent().getExtras();

        itemHistorico = new Historico();

        String PlayerMode = b.getString("gameMode");
        if (PlayerMode != null && PlayerMode.toLowerCase().equals("single")) {
            if (Chess.getCurrentGame() == null) {
                GameMode gameMode = GameMode.SinglePlayer;
                Player player1 = (Player) b.getSerializable("Player1");
                Player player2 = new Player(player1.getSide() == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE);
                Game g = new Game(player1, player2, gameMode, itemHistorico, this);
                Chess.setCurrentGame(g);
            }
        } else if (PlayerMode != null && PlayerMode.toLowerCase().equals("local")) {
            Player pl1 = (Player) b.getSerializable("Player1");
            Player pl2 = (Player) b.getSerializable("Player2");
            Chess.setCurrentGame(new Game(pl1, pl2, GameMode.LocalMultiplayer, itemHistorico, this));
        }

        Fragment gf = GameFragment.newInstance(b);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment pf1 = PlayerInfoFragment.newInstance(Chess.getCurrentGame().getBlackPlayer());
        Fragment pf2 = PlayerInfoFragment.newInstance(Chess.getCurrentGame().getWhitePlayer());
        ft.add(R.id.player1Container, pf1);
        ft.add(R.id.player2Container, pf2);
        ft.add(R.id.gameFragmentContainer, gf);
        ft.commit();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Chess.addHistorico(itemHistorico);
    }
}
