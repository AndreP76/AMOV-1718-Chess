package com.amov.lidia.andre.androidchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by andre on 1/5/18.
 */

public class PictureManager {
    public static void WriteImageToFile(Context ctx, String path, byte[] data) {
        try {
            FileOutputStream FOS = ctx.openFileOutput(path, Context.MODE_PRIVATE);
            FOS.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteImageToFile(Context ctx, String path, Bitmap bmp, int rotation) {
        try {
            FileOutputStream FOS = ctx.openFileOutput(path, Context.MODE_PRIVATE);
            if (rotation > 0)
                bmp = RotateImageData(bmp, rotation);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, FOS);
            FOS.flush();
            FOS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap RotateImageData(Bitmap bp, int degrees) {
        //int matrixSide = (int) Math.sqrt(data.length);
        Matrix mtx = new Matrix();
        mtx.postRotate(degrees);
        return Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), mtx, true);
    }

    public static BitmapDrawable ImageToDrawable(Context ctx, Bitmap original) {
        return new BitmapDrawable(ctx.getResources(), original);
    }

    public static Bitmap ReadImageFromFile(Context ctx, String path) throws FileNotFoundException {
        return BitmapFactory.decodeStream(ctx.openFileInput(path));
    }

    public static Bitmap ScaleBitmap(Bitmap original, int WidthPixels, int HeightPixels) {
        return Bitmap.createScaledBitmap(original, WidthPixels, HeightPixels, false);
    }
}
