package com.amov.lidia.andre.androidchess.CustomActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.CustomDialogs.SinglePlayerStartDialog;
import com.amov.lidia.andre.androidchess.ProfileManager;
import com.amov.lidia.andre.androidchess.R;

import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Log.v("[Folder location] :: ", getFilesDir().getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singlePlayerStart(View view) {
        SinglePlayerStartDialog spsd= new SinglePlayerStartDialog();
        spsd.show(getFragmentManager(),"spsd");
    }

    public void multiPlayerStart(View view) {
        Intent toLoadScreenActivity = new Intent(this, RegisterPlayer.class);
        Bundle nextActivityArgs = new Bundle();
        nextActivityArgs.putString("gameMode", "local");
        toLoadScreenActivity.putExtra(RegisterPlayer.PLAYER_COUNT_ID, 2);
        toLoadScreenActivity.putExtra(RegisterPlayer.NEXT_ACTIVITY_ARGS_ID, nextActivityArgs);
        toLoadScreenActivity.putExtra(RegisterPlayer.NEXT_ACTIVITY_CLASS_ID, GameActivity.class.getCanonicalName());
        Chess.setCurrentGame(null);
        startActivity(toLoadScreenActivity);
    }

    public void onlineStart(View view) {

    }

    public void eraseProfiles(View view) {
        ProfileManager.DestroyProfilesAndPictures(this);
    }
}
