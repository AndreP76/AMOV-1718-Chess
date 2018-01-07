package com.amov.lidia.andre.androidchess.CustomActivities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amov.lidia.andre.androidchess.ChessCore.Player;
import com.amov.lidia.andre.androidchess.CustomDialogs.SelectProfileDialog;
import com.amov.lidia.andre.androidchess.PlayerProfile;
import com.amov.lidia.andre.androidchess.ProfileManager;
import com.amov.lidia.andre.androidchess.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.amov.lidia.andre.androidchess.ChessCore.Game.BLACK_SIDE;
import static com.amov.lidia.andre.androidchess.ChessCore.Game.WHITE_SIDE;

public class RegisterPlayer extends Activity implements SurfaceHolder.Callback {
    public static final String PLAYER_COUNT_ID = "playerCount";
    public static final String NEXT_ACTIVITY_ARGS_ID = "nextActivityArgs";
    public static final String NEXT_ACTIVITY_CLASS_ID = "nextClass";
    private static final int CAMERA_PERMISSION_REQUEST = 12345;
    Camera camera;
    boolean cameraActive = false;
    BitmapDrawable capturedPicture = null;
    byte[] capturedPictureData;
    int howManyPlayers;
    int currentPayerCount;
    Bundle nextActivityArgs;
    Class nextActivityClass;
    Button takePictureButton;
    SurfaceView cameraPreview;
    SurfaceHolder cameraPreviewHolder;
    EditText playerName;
    TextView playerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);

        howManyPlayers = getIntent().getIntExtra(PLAYER_COUNT_ID, 1);
        currentPayerCount = 0;
        nextActivityArgs = getIntent().getBundleExtra(NEXT_ACTIVITY_ARGS_ID);
        try {
            nextActivityClass = Class.forName(getIntent().getStringExtra(NEXT_ACTIVITY_CLASS_ID));
        } catch (ClassNotFoundException e) {
            Log.w("[REGISTER PLAYER] :: ", "Missing next Activity parameter, or unknown activity");
            finish();
        }

        cameraPreview = findViewById(R.id.pictureArea);
        cameraPreviewHolder = cameraPreview.getHolder();
        cameraPreviewHolder.addCallback(this);
        takePictureButton = findViewById(R.id.takePictureButton);
        playerName = findViewById(R.id.nameBox);
        playerID = findViewById(R.id.playerId);

        playerID.setText(getString(R.string.player) + (currentPayerCount + 1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                String[] s = {android.Manifest.permission.CAMERA};
                requestPermissions(s, CAMERA_PERMISSION_REQUEST);
            } else {//has permission
                startCamera();
            }
        } else {//permission not needed
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                //TODO : add a default image here
            }
        }
    }

    private void startCamera() {
        if (!cameraActive && camera == null) {
            cameraPreview.setBackground(null);
            int cameraCount = Camera.getNumberOfCameras();
            int frontCameraIndex = -1;
            for (int i = 0; i < cameraCount; ++i) {
                Camera.CameraInfo CI = new Camera.CameraInfo();
                Camera.getCameraInfo(i, CI);
                if (CI.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
                    frontCameraIndex = i;
            }

            camera = Camera.open(frontCameraIndex == -1 ? 0 : frontCameraIndex);
            camera.lock();
            cameraActive = true;
            int orientationDegrees = 0;
            switch (getResources().getConfiguration().orientation) {
                case Configuration.ORIENTATION_LANDSCAPE: {
                    orientationDegrees = 0;
                    break;
                }
                case Configuration.ORIENTATION_PORTRAIT: {
                    orientationDegrees = 90;
                    break;
                }
            }
            camera.setDisplayOrientation(orientationDegrees);
            Camera.Parameters cp = camera.getParameters();
            cp.setRotation(3 * orientationDegrees);
            camera.setParameters(cp);
            camera.startPreview();
            try {
                camera.setPreviewDisplay(cameraPreviewHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.unlock();
            cameraActive = false;
            camera.release();
            camera = null;
        }
    }

    public void acceptPlayerClick(View view) {
        currentPayerCount++;
        String thisPictureName = generatePictureName("");

        //Bitmap originalBitmap = BitmapFactory.decodeByteArray(capturedPictureData,0,capturedPictureData.length);
        //Bitmap rotatedBitmap = PictureManager.RotateImageData(originalBitmap,orientationDegrees);
        //capturedPictureData = PictureManager.BitmapToData(rotatedBitmap);

        try (FileOutputStream FW = openFileOutput(thisPictureName, MODE_PRIVATE)) {
            FW.write(capturedPictureData);
        } catch (IOException e) {
            Log.e("[REGISTER PLAYER] :: ", "Error opening file for writing!");
            finish();
            e.printStackTrace();
        }

        PlayerProfile P = ProfileManager.addNewProfile(playerName.getText().toString(), thisPictureName, this);
        nextActivityArgs.putSerializable("Player" + currentPayerCount, new Player(P, currentPayerCount % 2 == 0 ? BLACK_SIDE : WHITE_SIDE, false));

        if (currentPayerCount >= howManyPlayers) {
            Intent it = new Intent(this, nextActivityClass);
            it.putExtra("args", nextActivityArgs);
            startActivity(it);
            finish();
        } else {
            startCamera();
            playerName.setText("");
            playerID.setText(R.string.player + currentPayerCount + 1);
        }
    }

    private String generatePictureName(String picturesRoot) {
        String newName = picturesRoot + "picture_";
        String extension = ".jpg";
        Random SR = new Random(System.currentTimeMillis());
        newName += SR.nextInt(10);
        while (new File(newName + extension).exists()) {
            newName += SR.nextInt(10);
        }
        return newName + extension;
    }

    public void cameraButtonClicked(View view) {
        if (camera != null && cameraActive) {
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    stopCamera();
                    takePictureButton.setText(R.string.resetPictureText);
                    capturedPictureData = data;
                    capturedPicture = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(data, 0, data.length));
                    cameraPreview.setBackground(capturedPicture);
                }
            });
        } else if (!cameraActive && capturedPicture != null) {
            capturedPicture = null;
            takePictureButton.setText(R.string.takePictureText);
            startCamera();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                String[] s = {android.Manifest.permission.CAMERA};
                requestPermissions(s, CAMERA_PERMISSION_REQUEST);
            } else {//has permission
                startCamera();
            }
        } else {//permission not needed
            startCamera();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }

    @Override
    public void onBackPressed() {
        stopCamera();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.load_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.loadProfile) {//mostrar a modal
            onLoadClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onLoadClicked() {
        ArrayList<PlayerProfile> profiles = ProfileManager.getAllProfiles(this);
        SelectProfileDialog SPD = new SelectProfileDialog(this, profiles, currentPayerCount + 1, nextActivityArgs);
        SPD.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (nextActivityArgs.getSerializable("Player" + (currentPayerCount + 1)) != null) {
                    currentPayerCount++;
                    if (currentPayerCount >= howManyPlayers) {
                        Intent it = new Intent(RegisterPlayer.this, nextActivityClass);
                        it.putExtra("args", nextActivityArgs);
                        startActivity(it);
                        finish();
                    } else {
                        startCamera();
                        playerName.setText("");
                        playerID.setText(R.string.player + currentPayerCount + 1);
                    }
                }
            }
        });
        SPD.show();
    }
}

