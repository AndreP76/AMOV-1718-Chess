package com.amov.lidia.andre.androidchess;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amov.lidia.andre.androidchess.ChessCore.Game;
import com.amov.lidia.andre.androidchess.ChessCore.Player;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.GameMode;

public class GameFragment extends Fragment {
    GameMode gameMode;

    public GameFragment() {

    }

    public static GameFragment newInstance() {
        Bundle args = new Bundle();

        args.putString("gameMode", "single");
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_game, container);
        return new BoardView(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle parameters = getArguments();
            String PlayerMode = parameters.getString("gameMode");
            if (PlayerMode != null && PlayerMode.toLowerCase().equals("single")) {
                gameMode = GameMode.SinglePlayer;
                Player player1 = new Player("Human Player", false);
                Player player2 = new Player();
                Chess.setCurrentGame(new Game(player1, player2,gameMode));
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class interactionHandler implements GameFragment.OnFragmentInteractionListener {

        @Override
        public void onFragmentInteraction(Uri uri) {
            Log.v("[GAMEFRAG] :: ", "Game fragment interacted!");
        }
    }
}
