package com.cornez.buddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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

    public static  String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Date getDateFromSQLDateTime(String cursorString) {
        Calendar t = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY",Locale.getDefault());
        Date dt = null; //replace 4 with the column index
        try {
            dt = sdf.parse(cursorString);
            return dt;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }

    }

    public static List<Pet> seedPetListData(Context context){
        List<Pet> petList = new ArrayList<>();

        Drawable d1 = context.getResources().getDrawable(R.drawable.image1);
        Bitmap petImage1 = ((BitmapDrawable) d1).getBitmap();
        String imagePath1 = saveImage(petImage1);

        Drawable d2 = context.getResources().getDrawable(R.drawable.image2);
        Bitmap petImage2 = ((BitmapDrawable) d2).getBitmap();
        String imagePath2 = saveImage(petImage2);

        Drawable d3 = context.getResources().getDrawable(R.drawable.image3);
        Bitmap petImage3 = ((BitmapDrawable) d3).getBitmap();
        String imagePath3 = saveImage(petImage3);

        Drawable d4 = context.getResources().getDrawable(R.drawable.image4);
        Bitmap petImage4 = ((BitmapDrawable) d4).getBitmap();
        String imagePath4 = saveImage(petImage4);

        Drawable d5 = context.getResources().getDrawable(R.drawable.image5);
        Bitmap petImage5 = ((BitmapDrawable) d5).getBitmap();
        String imagePath5 = saveImage(petImage5);

        Drawable d6 = context.getResources().getDrawable(R.drawable.image6);
        Bitmap petImage6 = ((BitmapDrawable) d6).getBitmap();
        String imagePath6 = saveImage(petImage6);

        Drawable d7 = context.getResources().getDrawable(R.drawable.image7);
        Bitmap petImage7 = ((BitmapDrawable) d7).getBitmap();
        String imagePath7 = saveImage(petImage7);

        Drawable d8 = context.getResources().getDrawable(R.drawable.image8);
        Bitmap petImage8 = ((BitmapDrawable) d8).getBitmap();
        String imagePath8 = saveImage(petImage8);

        Pet p1 = new Pet(-1, "Snooky", "Beagle", "Rey", imagePath1, "12025550142");
        Pet p2 = new Pet(-1, "Tank", "Bernedoodle", "Timothy", imagePath2, "12025550170");
        Pet p3 = new Pet(-1, "Porche", "Chow Chow", "Miranda", imagePath3, "12025550123");
        Pet p4 = new Pet(-1, "Mercedes", "Fox Terrier", "Mylie", imagePath4, "12025550193");
        Pet p5 = new Pet(-1, "Kassie", "Lowchen", "Rishi", imagePath5, "12025550190");
        Pet p6 = new Pet(-1, "Booboo", "Puggle", "Amina", imagePath6, "12025550191");
        Pet p7 = new Pet(-1, "Cupcake", "Rottweiler", "Gaven", imagePath7, "12025550131");
        Pet p8 = new Pet(-1, "Rocky", "Vizsla", "Paris", imagePath8, "12025550888");

        petList.add(p1);
        petList.add(p2);
        petList.add(p3);
        petList.add(p4);
        petList.add(p5);
        petList.add(p6);
        petList.add(p7);
        petList.add(p8);
        return petList;
    }
}
