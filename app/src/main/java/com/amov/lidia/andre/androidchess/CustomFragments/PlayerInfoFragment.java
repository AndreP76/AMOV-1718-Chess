package com.amov.lidia.andre.androidchess.CustomFragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.ChessCore.OnPieceMoveListenerInterface;
import com.amov.lidia.andre.androidchess.ChessCore.Player;
import com.amov.lidia.andre.androidchess.CustomActivities.RegisterPlayer;
import com.amov.lidia.andre.androidchess.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerInfoFragment extends Fragment implements OnPieceMoveListenerInterface {
    public static final int NEW_PLAYER_REGISTER = 851646;
    Player currentPlayer;
    SurfaceView playerImageSV;
    SurfaceView playerColorSV;
    TextView playerNameTA;
    Button resignBtn;

    Bitmap PlayerPicture;

    public PlayerInfoFragment() {
        // Required empty public constructor
    }

    public static PlayerInfoFragment newInstance(Player thisPlayer) {
        PlayerInfoFragment fragment = new PlayerInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("player", thisPlayer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPlayer = (Player) getArguments().getSerializable("player");
            try {
                if (currentPlayer != null && currentPlayer.getPicturePath() != null) {
                    FileInputStream FIS = getActivity().openFileInput(currentPlayer.getPicturePath());
                    PlayerPicture = BitmapFactory.decodeStream(FIS);
                } else {
                    PlayerPicture = BitmapFactory.decodeResource(getResources(), R.mipmap.bot_image);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Chess.getCurrentGame().addMoveListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        playerColorSV = view.findViewById(R.id.PlayerSide);
        playerImageSV = view.findViewById(R.id.PlayerPicture);
        playerNameTA = view.findViewById(R.id.playerName);
        resignBtn = view.findViewById(R.id.resignButton);

        playerImageSV.post(new Runnable() {
            @Override
            public void run() {
                playerImageSV.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(PlayerPicture, playerImageSV.getWidth(), playerImageSV.getHeight(), false)));
            }
        });
        playerColorSV.post(new Runnable() {
            @Override
            public void run() {
                playerColorSV.setBackgroundColor(currentPlayer.getSide() == WHITE_SIDE ? Color.WHITE : Color.BLACK);
            }
        });
        playerNameTA.setText(currentPlayer.getName());
    }

    public void onResignClick(View v) {
        if (!this.currentPlayer.isAI()) {
            this.currentPlayer.setAI(true);
            resignBtn.setText(R.string.playerEnter);
            playerNameTA.setText("HAL 9000");
        } else {//carregar novo jogador
            Intent RegisterPlayerIntent = new Intent(this.getActivity(), RegisterPlayer.class);
            startActivityForResult(RegisterPlayerIntent, NEW_PLAYER_REGISTER);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_PLAYER_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                Player p = (Player) data.getSerializableExtra("newPlayer");
                p.setSide(currentPlayer.getSide());
                Chess.getCurrentGame().setPlayer(currentPlayer.getSide(), p);
                currentPlayer = p;

                FileInputStream FIS = null;
                try {
                    FIS = getActivity().openFileInput(currentPlayer.getPicturePath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PlayerPicture = BitmapFactory.decodeStream(FIS);

                playerImageSV.setBackground(new BitmapDrawable(getResources(), PlayerPicture));
                playerNameTA.setText(p.getName());
            }
        }
    }

    @Override
    public void onMove() {
        if (Chess.getCurrentGame().getCurrentPlayerSide() == this.currentPlayer.getSide() && !this.currentPlayer.isAI()) {
            resignBtn.setVisibility(View.INVISIBLE);
        } else if (this.currentPlayer.isAI()) {
            resignBtn.setVisibility(View.VISIBLE);
            resignBtn.setText(R.string.playerEnter);
        }
    }
}
