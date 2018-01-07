package com.amov.lidia.andre.androidchess.CustomDialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amov.lidia.andre.androidchess.ChessCore.Player;
import com.amov.lidia.andre.androidchess.PictureManager;
import com.amov.lidia.andre.androidchess.PlayerProfile;
import com.amov.lidia.andre.androidchess.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

/**
 * Created by andre on 1/5/18.
 */

public class SelectProfileDialog extends Dialog implements View.OnClickListener {

    ListView lv;
    Context ctx;
    ArrayList<PlayerProfile> ALP;
    private Button cancelButton;
    private Bundle argsToAdd;
    private int playerID;

    public SelectProfileDialog(Context context, ArrayList<PlayerProfile> ALP, int playerID, Bundle argsToAdd) {
        super(context);
        ctx = context;
        this.ALP = ALP;
        this.argsToAdd = argsToAdd;
        this.playerID = playerID;
    }

    /*public SelectProfileDialog(@NonNull Context context, @StyleRes int themeResId, ArrayList<PlayerProfile> ALP) {
        super(context, themeResId);
        ctx = context;
        this.ALP = ALP;
    }

    protected SelectProfileDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, ArrayList<PlayerProfile> ALP) {
        super(context, cancelable, cancelListener);
        ctx = context;
        this.ALP = ALP;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_profile);
        lv = findViewById(R.id.profileList);
        lv.setAdapter(new ProfileAdapter(ctx, ALP));
        cancelButton = findViewById(R.id.cancelSelectProfile);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectProfileDialog.this.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        this.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        Log.d("[SPD] :: ", "Touched : " + v.getId());
    }

    private class ProfileAdapter extends BaseAdapter {
        ArrayList<PlayerProfile> ALS;
        Context ctx;
        int currentViewIndex = 0;

        public ProfileAdapter(Context ctx, ArrayList<PlayerProfile> ALS) {
            this.ALS = ALS;
        }

        @Override
        public int getCount() {
            return ALS.size();
        }

        @Override
        public Object getItem(int position) {
            return ALS.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final PlayerProfile p = ALS.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.profilelist_menu_item, parent, false);
                convertView.setId(currentViewIndex);
            }

            convertView.setMinimumHeight(parent.getHeight() / 10);
            final SurfaceView SV = convertView.findViewById(R.id.PlayerPicture);
            TextView TV = convertView.findViewById(R.id.playerName);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    argsToAdd.putSerializable("Player" + playerID, new Player(ALP.get(position), playerID % 2 == 0 ? BLACK_SIDE : WHITE_SIDE, false));
                    SelectProfileDialog.this.dismiss();
                }
            });

            SV.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        SV.setBackground(PictureManager.ImageToDrawable(SelectProfileDialog.this.getContext(), PictureManager.ScaleBitmap(PictureManager.ReadImageFromFile(SelectProfileDialog.this.getContext(), p.getPictureFilePath()), SV.getWidth(), SV.getHeight())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            TV.setText(p.getName());

            return convertView;
        }
    }
}
