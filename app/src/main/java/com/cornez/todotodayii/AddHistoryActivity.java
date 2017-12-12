package com.cornez.todotodayii;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;


public class AddHistoryActivity extends AppCompatActivity {

    protected DBHelper mDBHelper;
    private List<Pet> list;

    private TextView textPetId;
    private TextView textPetName;
    private TextView textPetBreed;
    private EditText editHistWeight;
    private EditText editHistAge;
    private EditText editHistDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TASK 1: LAUNCH THE LAYOUT REPRESENTING THE MAIN ACTIVITY
        setContentView(R.layout.history_add);

        // TASK 2: ESTABLISH REFERENCES TO THE UI
        //      ELEMENTS LOCATED ON THE LAYOUT
        editHistWeight = (EditText) findViewById(R.id.editHistWeight);
        editHistAge = (EditText) findViewById(R.id.editHistAge);
        editHistDescription = (EditText) findViewById(R.id.editHistDescription);

        textPetId = (TextView) findViewById(R.id.lblPetId);
        textPetName = (TextView) findViewById(R.id.lblPetName);
        textPetBreed = (TextView) findViewById(R.id.lblPetBreed);



        Intent i = getIntent();
        Pet pet = (Pet) i.getSerializableExtra("EXTRA_SERIALIZED_PET");
        getSupportActionBar().setTitle(pet.getName()+"'s visit");

        textPetId.setText(String.valueOf(pet.getId()));
        textPetName.setText(pet.getName());
        textPetBreed.setText(pet.getBreed());

        // TASK 3: SET UP THE DATABASE
        mDBHelper = new DBHelper(this);
    }

    public void btnSaveNewHistClick(View view){



        Integer petId = Integer.valueOf(textPetId.getText().toString());
        Float weight = Float.valueOf(editHistWeight.getText().toString());
        Integer age = Integer.valueOf(editHistAge.getText().toString());
        String description = editHistDescription.getText().toString();

        // validation for empty fields
        if (petId < 0 || weight < 0 || age <= 0 || description.isEmpty()) {
            Toast.makeText(getApplicationContext(), "A field can not be blank", Toast.LENGTH_SHORT).show();
        } else {

            //BUILD A NEW PET ITEM AND ADD IT TO THE DATABASE
            History history = new History(-1, petId, age, weight, description);
            mDBHelper.addPetHistory(history);


            // CLEAR OUT THE PET EDIT VIEWS
            editHistWeight.setText("");
            editHistAge.setText("");
            editHistDescription.setText("");

            Toast.makeText(getApplicationContext(), "Visit information added", Toast.LENGTH_SHORT).show();

//            /* ADD THE TASK AND SET A NOTIFICATION OF CHANGES */
//            adapt.add(pet);
//            adapt.notifyDataSetChanged();
//            Intent intent = new Intent(AddPetActivity.this, ListActivity.class);
//            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
