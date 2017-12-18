package com.cornez.todotodayii;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.RadioButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.cornez.todotodayii.Utils.MakeRoundableImage;
import static com.cornez.todotodayii.Utils.getBitmap;


public class PetListActivity extends AppCompatActivity {
    protected DBHelper mDBHelper;
    private List<Pet> list;
    private PetArrayAdapter adapt;
    private ListView petListView;
    private FloatingActionButton addNewPetButton;
    private FloatingActionButton deletePetButton;
    private List<Pet> petsToDelete;
    private boolean CHOICE_MODE_ENABLED = false;
    private int ACTIVITY_MODE_ADD = 1;
    private int ACTIVITY_MODE_EDIT = 2;

    public void onBackPressed()
    {
        this.startActivity(new Intent(getBaseContext(),MainActivity.class));
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        addNewPetButton = (FloatingActionButton) findViewById(R.id.btnAddNewPet);
        deletePetButton = (FloatingActionButton) findViewById(R.id.btnDeletePet);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("All Pets");

        mDBHelper = new DBHelper(this);
        petsToDelete = new ArrayList<>();

        // ADD THE LIST ITEM CLICK LISTENER
        petListView = (ListView)findViewById(R.id.petListView);
        petListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        petListView.setAdapter(adapt);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), HistoryListActivity.class);
                intent.putExtra("EXTRA_SERIALIZED_PET", list.get(position));
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pet_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_search:
//                Toast.makeText(getApplicationContext(), "Clicked Search", Toast.LENGTH_SHORT).show();
////                CHOICE_MODE_ENABLED = !CHOICE_MODE_ENABLED;
////                adapt.notifyDataSetChanged();
//                return true;

            case R.id.action_delete:
                Toast.makeText(getApplicationContext(), "Clicked DELETE", Toast.LENGTH_SHORT).show();
                petsToDelete.clear();
                CHOICE_MODE_ENABLED = !CHOICE_MODE_ENABLED;
                if(CHOICE_MODE_ENABLED){
                    addNewPetButton.setVisibility(View.INVISIBLE);
                    deletePetButton.setVisibility(View.VISIBLE);
                } else {
                    addNewPetButton.setVisibility(View.VISIBLE);
                    deletePetButton.setVisibility(View.INVISIBLE);
                }
                adapt.notifyDataSetChanged();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void btnAddNewPetClick(View view) {
        Intent intent1 = new Intent(PetListActivity.this,AddPetActivity.class);
        startActivity(intent1);
    }

    public void btnDeletePetClick(View view) {
        int deleteCount = petsToDelete.size();

        new AlertDialog.Builder(this)
                .setTitle("Delete Pet")
                .setMessage("Do you really want to delete " + String.valueOf(deleteCount) + " pet(s)?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(), "Deleted pet(s)", Toast.LENGTH_SHORT).show();
                        CHOICE_MODE_ENABLED = false;
                        addNewPetButton.setVisibility(View.VISIBLE);
                        deletePetButton.setVisibility(View.INVISIBLE);
                        mDBHelper.deleteSelectedPets(petsToDelete);
                        petsToDelete.clear();
                        onPetListUpdated();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
        Log.d("TEST", "TEST 2");

    }

    public void onPetListUpdated(){
        list = mDBHelper.getAllPets();
        adapt = new PetArrayAdapter(this, R.layout.pet_item, list);
        ListView petListView = (ListView) findViewById(R.id.petListView);
        petListView.setAdapter(adapt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onPetListUpdated();
    }


    //******************* ADAPTER ******************************
    private class PetArrayAdapter extends ArrayAdapter<Pet> {
        Context context;
        List<Pet> petList = new ArrayList<>();

        public PetArrayAdapter(Context c, int rId, List<Pet> objects) {
            super(c, rId, objects);
            petList = objects;
            context = c;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Pet pet = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.pet_item, parent, false);
            }
            // Lookup view for data population
            TextView petListName = (TextView) convertView.findViewById(R.id.petListName);
            TextView petBreed = (TextView) convertView.findViewById(R.id.petListBreed);
            TextView petOwner = (TextView) convertView.findViewById(R.id.petListOwner);
            ImageView petImageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            final CheckBox petListCheckBox = (CheckBox) convertView.findViewById(R.id.petListCheckBox);
            final ImageButton editImageButton = (ImageButton) convertView.findViewById(R.id.petListEditButton);

            petListCheckBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    final boolean isChecked = petListCheckBox.isChecked();
                    if (isChecked){
                        petsToDelete.add(pet);
                    }else {
                        petsToDelete.remove(pet);
                    }
                }
            });

            petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent intent = new Intent(getBaseContext(), HistoryListActivity.class);
                    intent.putExtra("EXTRA_SERIALIZED_PET", list.get(position));
                    startActivity(intent);
                }
            });

            editImageButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(PetListActivity.this,AddPetActivity.class);
                        intent.putExtra("EXTRA_SERIALIZED_PET", pet);
                        intent.putExtra("EXTRA_ACTIVITY_MODE", ACTIVITY_MODE_EDIT);
                        startActivity(intent);
                    }
            });





            // Populate the data into the template view using the data object
            petListName.setText(pet.getName());
            petBreed.setText(pet.getBreed());
            petOwner.setText(pet.getOwnerName());
            RoundedBitmapDrawable roundedBitmapDrawable = MakeRoundableImage(getBaseContext(), pet.getImagePath());


            if(roundedBitmapDrawable != null){
                petImageView.setImageDrawable(roundedBitmapDrawable);
            }
            else {
                petImageView.setImageResource(R.drawable.camera);
            }
            if(CHOICE_MODE_ENABLED){
                petListCheckBox.setVisibility(View.VISIBLE);
                editImageButton.setVisibility(View.GONE);
            }
            else{
                petListCheckBox.setVisibility(View.INVISIBLE);
                editImageButton.setVisibility(View.VISIBLE);
            }

            // Return the completed view to render on screen
            return convertView;
        }


        @Override
        public long getItemId(int position) {
            return getItem(position).getName().hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }







}
