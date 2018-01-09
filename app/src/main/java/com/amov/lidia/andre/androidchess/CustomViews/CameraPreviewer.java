package com.amov.lidia.andre.androidchess.CustomViews;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.amov.lidia.andre.androidchess.CameraHandler;

/**
 * Created by andre on 1/8/18.
 */

public class CameraPreviewer extends SurfaceView implements SurfaceHolder.Callback {
    private CameraHandler CH;

    public CameraPreviewer(Context context) {
        super(context);
    }

    public CameraPreviewer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreviewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CameraPreviewer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (CH == null)
            CH = new CameraHandler(CameraHandler.findFrontCamera());
        CH.openCameraForPreview(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (CH != null)
            CH.releaseAndCloseCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (CH != null)
            CH.releaseAndCloseCamera();
    }

    public void stopPreviewing() {
        if (CH != null)
            CH.releaseAndCloseCamera();
    }

    public void startPreviewing() {
        if (CH == null)
            CH = new CameraHandler(CameraHandler.findFrontCamera());
        CH.openCameraForPreview(this.getHolder());
    }

    public void takePicture(Camera.PictureCallback pictureCallback) {
        CH.takePicture(pictureCallback);
    }
}
