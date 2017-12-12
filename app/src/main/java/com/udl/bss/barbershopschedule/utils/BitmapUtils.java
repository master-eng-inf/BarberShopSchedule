package com.udl.bss.barbershopschedule.utils;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Bitmap.createScaledBitmap;

public class BitmapUtils {

    public static byte[] bitmapToByteArray (Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static Bitmap byteArrayToBitmap (byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static int sizeOfBitmap (Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static Bitmap reduceSize(Bitmap bitmap) {
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        return createScaledBitmap(bitmap, 512, nh, true);
    }

    public static Bitmap getImageFromUri(Uri uri, Activity activity) {
        try {
            return MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, String name, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory,name+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            try { if (fos != null) fos.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path, String name) {

        try {
            File f=new File(path, name+".jpg");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        return null;
    }

}
