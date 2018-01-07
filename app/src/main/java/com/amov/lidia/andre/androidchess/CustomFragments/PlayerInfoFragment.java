package com.amov.lidia.andre.androidchess.CustomFragments;


import android.app.Fragment;
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
import android.widget.TextView;

import com.amov.lidia.andre.androidchess.ChessCore.Player;
import com.amov.lidia.andre.androidchess.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerInfoFragment extends Fragment {
    Player currentPlayer;
    SurfaceView playerImageSV;
    SurfaceView playerColorSV;
    TextView playerNameTA;

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
                if (currentPlayer.getPicturePath() != null) {
                    FileInputStream FIS = getActivity().openFileInput(currentPlayer.getPicturePath());
                    PlayerPicture = BitmapFactory.decodeStream(FIS);
                } else {
                    PlayerPicture = BitmapFactory.decodeResource(getResources(), R.mipmap.bot_image);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
}
