package com.amov.lidia.andre.androidchess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by André Pereira and Lidia Fonseca on 12/14/17.
 */

public class SinglePlayerStartDialog extends DialogFragment{
    public Dialog onCreateDialog(Bundle state){
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setMessage(R.string.loadOrNewMessage).setPositiveButton(R.string.loadButton,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent toLoadScreenActivity = new Intent(getActivity(),LoadActivity.class);
                toLoadScreenActivity.putExtra("nextClass","GameActivity");
                Bundle nextActivityParams = new Bundle();
                nextActivityParams.putString("gameMode","single");
                toLoadScreenActivity.putExtra("nextActivityParams",nextActivityParams);
                startActivity(toLoadScreenActivity);
            }
        }).setNegativeButton(R.string.newButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent toLoadScreenActivity = new Intent(getActivity(),GameActivity.class);
                toLoadScreenActivity.putExtra("gameMode","single");
                Chess.setCurrentGame(null);
                startActivity(toLoadScreenActivity);
            }
        }).create();
        return b.create();
    }
}
