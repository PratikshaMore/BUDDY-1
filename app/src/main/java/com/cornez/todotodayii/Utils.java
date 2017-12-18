package com.cornez.todotodayii;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class Utils {

    public static Bitmap getBitmap(String path) {
        try {
            Bitmap bitmap = null;
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root);
        myDir.mkdirs();
        String millisStart = String.valueOf(Calendar.getInstance().getTimeInMillis());


        String fname = "/Buddy-"+ millisStart+".bmp";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.d("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return root+fname;
        } catch (Exception e) {
            e.printStackTrace();
            return "NO_FILE";
        }
    }

    public static RoundedBitmapDrawable MakeRoundableImage(Context context, String path){
        Bitmap image = getBitmap(path);
        if(image == null){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.camera);
        }

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), image);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }

    public static RoundedBitmapDrawable MakeRoundableImage(Context context, Bitmap image){
        if(image == null){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.camera);
        }
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), image);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }
}
