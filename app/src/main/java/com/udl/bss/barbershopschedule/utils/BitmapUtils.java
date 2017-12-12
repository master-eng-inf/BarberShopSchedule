package com.udl.bss.barbershopschedule.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

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

}
