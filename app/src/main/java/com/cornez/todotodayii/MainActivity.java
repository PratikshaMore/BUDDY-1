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

public class MainActivity extends Activity {
    protected DBHelper mDBHelper;
    private EditText username;
    private EditText password;
    private TextView displayName;
    private TextView loginError;
    String login_name;
    String login_password;

    public void onBackPressed()
    {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText)findViewById(R.id.editPassword);
        mDBHelper = new DBHelper(this);
    }

    public void btnHelpClick(View view){
        Intent intent1 = new Intent(getBaseContext(), HelpActivity.class);
        startActivity(intent1);
    }


    public void btnSigninClick(View view)
    {
        login_name = username.getText().toString();
        login_password = password.getText().toString();

    // if(true)
      if((login_name.matches("Keerthana") && login_password.matches("abcd"))|| (login_name.matches("Pratiksha")&& login_password.matches("1234")))

        {
            Intent intent1 = new Intent(getBaseContext(), PetListActivity.class);
            startActivity(intent1);
        }
        else
        {
            loginError = (TextView)findViewById(R.id.loginError);
            loginError.setText("Invalid username/password");
        }

    }

//    public void startAddDetailsActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this,AddDetailsActivity.class);
//        startActivity(intent);
//    }

//    public void startMainListActivity(View view)
//    {
//
//    }

//    public void onClickResetDB(View view){
//        mDBHelper.resetThis();
//    }
//    public void onClickSeedDatabase(View view){
//        mDBHelper.seedDatabase();
//    }
}

