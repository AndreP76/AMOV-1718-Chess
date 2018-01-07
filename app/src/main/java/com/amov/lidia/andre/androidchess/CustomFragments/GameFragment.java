package com.amov.lidia.andre.androidchess.CustomFragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.Utils.GameMode;
import com.amov.lidia.andre.androidchess.CustomViews.BoardView;

public class GameFragment extends Fragment {
    GameMode gameMode;
    BoardView BV;
    public GameFragment() {

    }

    public static GameFragment newInstance(Bundle args) {
        /*Bundle args = new Bundle();

        args.putString("gameMode", "single");*/
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_game, container);
        return (BV = new BoardView(getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            Bundle parameters = getArguments();
//            String PlayerMode = parameters.getString("gameMode");
//            if (PlayerMode != null && PlayerMode.toLowerCase().equals("single")) {
//                if (Chess.getCurrentGame() == null) {
//                    gameMode = GameMode.SinglePlayer;
//                    Player player1 = (Player) parameters.getSerializable("Player1");
//                    Player player2 = new Player(player1.getSide() == WHITE_SIDE ? BLACK_SIDE : WHITE_SIDE);
//                    Chess.setCurrentGame(new Game(player1, player2, gameMode));
//                }
//            }
//        }

        gameMode = Chess.getCurrentGame().gameMode;

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
