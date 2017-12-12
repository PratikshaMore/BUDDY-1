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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText)findViewById(R.id.editPassword);
        mDBHelper = new DBHelper(this);
    }


    public void btnSigninClick(View view)
    {
        login_name = username.getText().toString();
        login_password = password.getText().toString();

//      if((login_name.matches("Keerthana") && login_password.matches("abcd"))|| (login_name.matches("Pratiksha")&& login_password.matches("1234")))
        if(true)
        {
            Intent intent1 = new Intent(MainActivity.this,ListActivity.class);
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

    public void onClickResetDB(View view){
        mDBHelper.resetThis();
    }
}

