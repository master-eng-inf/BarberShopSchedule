package com.udl.bss.barbershopschedule.utils;


import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    public static byte[] bitmapToByteArray (Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

}
