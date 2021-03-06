package com.dev.buddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import static com.dev.buddy.Utils.MakeRoundableImage;


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
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

}
