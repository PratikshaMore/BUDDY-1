package com.cornez.todotodayii;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.List;
import android.view.View.OnClickListener;

import static com.cornez.todotodayii.Utils.MakeRoundableImage;
import static com.cornez.todotodayii.Utils.getBitmap;
import static com.cornez.todotodayii.Utils.saveImage;


public class HistoryListItemPage extends AppCompatActivity {


    private Pet currentPet;
    private History currentHistoryItem;

    private ImageView historyPagePetImage;
    private TextView historyPagePetName;
    private TextView historyPagePetOwner;
    private TextView historyPagePetContact;
    private TextView historyPagePetDescription;
    private TextView historyPagePetAge;
    private TextView historyPagePetWeight;
    private TextView historyPagePetDate;


    public void onBackPressed()
    {
        Intent intent = new Intent(getBaseContext(), HistoryListActivity.class);
        intent.putExtra("EXTRA_SERIALIZED_PET", currentPet);
        startActivity(intent);
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TASK 1: LAUNCH THE LAYOUT REPRESENTING THE MAIN ACTIVITY
        setContentView(R.layout.history_page);
        Intent i = getIntent();
        currentPet = (Pet) i.getSerializableExtra("EXTRA_SERIALIZED_PET");
        currentHistoryItem = (History) i.getSerializableExtra("EXTRA_SERIALIZED_HISTORY");


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        getSupportActionBar().setTitle(currentPet.getName()+"'s Visit");



        // TASK 2: ESTABLISH REFERENCES TO THE UI ELEMENTS LOCATED ON THE LAYOUT
        historyPagePetImage = (ImageView) findViewById(R.id.historyPagePetImage);
        historyPagePetName = (TextView) findViewById(R.id.historyPagePetName);
        historyPagePetOwner = (TextView) findViewById(R.id.historyPagePetOwner);
        historyPagePetContact = (TextView) findViewById(R.id.historyPagePetContact);
        historyPagePetDescription = (TextView) findViewById(R.id.historyPagePetDescription);
        historyPagePetAge = (TextView) findViewById(R.id.historyPagePetAge);
        historyPagePetWeight = (TextView) findViewById(R.id.historyPagePetWeight);
        historyPagePetDate = (TextView) findViewById(R.id.historyPagePetDate);


        RoundedBitmapDrawable roundedBitmapDrawable = MakeRoundableImage(getBaseContext(), currentPet.getImagePath());
        historyPagePetImage.setImageDrawable(roundedBitmapDrawable);
        historyPagePetName.setText(currentPet.getName());
        historyPagePetOwner.setText(currentPet.getOwnerName());
        historyPagePetContact.setText(currentPet.getContact());
        historyPagePetDescription.setText(currentHistoryItem.getDescription());
        historyPagePetAge.setText(String.valueOf(currentHistoryItem.getAge()));
        historyPagePetWeight.setText(String.valueOf(currentHistoryItem.getWeight()));
        historyPagePetDate.setText(String.valueOf(currentHistoryItem.getVisitDate()));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
