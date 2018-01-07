package com.amov.lidia.andre.androidchess;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by andre on 1/7/18.
 */

public class CameraHandler {
    private Camera camera;
    private int CameraID;
    private CameraState currentCameraState;
    private SurfaceHolder currentSurfaceHolder;

    public CameraHandler(int CameraID) {
        this.CameraID = CameraID;
        camera = null;
        currentCameraState = CameraState.Closed;
    }

    public static int findFrontCamera() {
        int cameraCount = Camera.getNumberOfCameras();
        int frontCameraIndex = -1;
        for (int i = 0; i < cameraCount; ++i) {
            Camera.CameraInfo CI = new Camera.CameraInfo();
            Camera.getCameraInfo(i, CI);
            if (CI.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
                frontCameraIndex = i;
        }
        return frontCameraIndex;
    }

    public boolean openCameraForPreview(SurfaceHolder sh) {
        if (currentCameraState != CameraState.Open) {
            currentCameraState = CameraState.Open;
            currentSurfaceHolder = sh;

            try {
                camera = Camera.open(CameraID);
                camera.setPreviewDisplay(sh);
                camera.lock();
                Camera.Parameters cp = camera.getParameters();
                cp.setRotation(90);
                camera.setDisplayOrientation(90);
                camera.setParameters(cp);
                camera.startPreview();
                return true;
            } catch (IOException e) {
                Log.e("[CAMERA] :: ", "Error opening camera! (Maybe another application has the camera locked ?)");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void releaseAndCloseCamera() {
        if (camera != null) {
            camera.stopPreview();
            try {
                camera.setPreviewDisplay(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.unlock();
            camera.release();
            camera = null;
            currentCameraState = CameraState.Closed;
        }
    }

    public void setCamera(int CameraID) {
        releaseAndCloseCamera();
        this.CameraID = CameraID;
    }

    public void takePicture(Camera.PictureCallback pcb) {
        if (currentCameraState == CameraState.Open) {
            camera.takePicture(null, null, pcb);
        }
    }
}
