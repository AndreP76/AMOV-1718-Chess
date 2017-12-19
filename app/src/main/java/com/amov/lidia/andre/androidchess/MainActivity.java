package com.amov.lidia.andre.androidchess;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singlePlayerStart(View view) {
        SinglePlayerStartDialog spsd= new SinglePlayerStartDialog();
        spsd.show(getFragmentManager(),"spsd");
    }

    public void multiPlayerStart(View view) {

    }

    public void onlineStart(View view) {

    }
}
