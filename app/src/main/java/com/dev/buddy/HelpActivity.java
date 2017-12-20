package com.dev.buddy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;


import java.util.List;
import static com.dev.buddy.Utils.seedPetListData;


public class HelpActivity extends Activity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    protected DBHelper mDBHelper;

    public void onBackPressed()
    {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    //permission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        mDBHelper = new DBHelper(this);
        verifyStoragePermissions(this);
    }

    public void onClickResetDB(View view){
        mDBHelper.resetThis();
        Toast.makeText(getApplicationContext(), "Database RESET", Toast.LENGTH_SHORT).show();
    }

    public void onClickSeedDatabase(View view){
        List<Pet> petList = seedPetListData(getBaseContext());
        mDBHelper.seedDatabase(petList);
        Toast.makeText(getApplicationContext(), "Database seeded with random values", Toast.LENGTH_SHORT).show();

    }
}

