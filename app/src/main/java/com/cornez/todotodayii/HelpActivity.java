package com.cornez.todotodayii;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        mDBHelper.seedDatabase();
        Toast.makeText(getApplicationContext(), "Database seeded with random values", Toast.LENGTH_SHORT).show();

    }
}

