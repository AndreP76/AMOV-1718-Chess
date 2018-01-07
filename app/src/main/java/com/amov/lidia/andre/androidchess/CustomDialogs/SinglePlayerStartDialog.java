package com.amov.lidia.andre.androidchess.CustomDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.amov.lidia.andre.androidchess.Chess;
import com.amov.lidia.andre.androidchess.CustomActivities.GameActivity;
import com.amov.lidia.andre.androidchess.CustomActivities.RegisterPlayer;
import com.amov.lidia.andre.androidchess.R;

/**
 * Created by André Pereira and Lidia Fonseca on 12/14/17.
 */

public class SinglePlayerStartDialog extends DialogFragment{
    public Dialog onCreateDialog(Bundle state){
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setMessage(R.string.loadOrNewMessage).setPositiveButton(R.string.loadButton,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent toLoadScreenActivity = new Intent(getActivity(), GameActivity.class);
                toLoadScreenActivity.putExtra("gameMode", "single");
                startActivity(toLoadScreenActivity);
            }
        }).setNegativeButton(R.string.newButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent toLoadScreenActivity = new Intent(getActivity(), RegisterPlayer.class);
                Bundle nextActivityArgs = new Bundle();
                nextActivityArgs.putString("gameMode", "single");
                toLoadScreenActivity.putExtra(RegisterPlayer.PLAYER_COUNT_ID, 1);
                toLoadScreenActivity.putExtra(RegisterPlayer.NEXT_ACTIVITY_ARGS_ID, nextActivityArgs);
                toLoadScreenActivity.putExtra(RegisterPlayer.NEXT_ACTIVITY_CLASS_ID, GameActivity.class.getCanonicalName());
                Chess.setCurrentGame(null);
                startActivity(toLoadScreenActivity);
            }
        }).create();
        return b.create();
    }
}
