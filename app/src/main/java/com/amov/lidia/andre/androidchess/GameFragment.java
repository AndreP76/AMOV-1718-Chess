package com.amov.lidia.andre.androidchess;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class GameFragment extends Fragment {
    public GameFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_game,container);
        return r;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle parameters = getArguments();
            String PlayerMode = parameters.getString("gameMode");
            if(PlayerMode != null && PlayerMode.toLowerCase().equals("single")){

            }
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class interactionHandler implements GameFragment.OnFragmentInteractionListener{

        @Override
        public void onFragmentInteraction(Uri uri) {

        }
    }
}
