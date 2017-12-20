package com.cornez.buddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import java.util.List;
import static com.cornez.buddy.Utils.seedPetListData;


public class HelpActivity extends Activity {
    protected DBHelper mDBHelper;

    public void onBackPressed()
    {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        mDBHelper = new DBHelper(this);

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

