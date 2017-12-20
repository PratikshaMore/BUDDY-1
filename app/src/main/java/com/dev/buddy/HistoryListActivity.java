package com.dev.buddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.dev.buddy.Utils.MakeRoundableImage;

public class HistoryListActivity extends AppCompatActivity {
    protected DBHelper mDBHelper;
    private List<History> list;
    private List<History> historyToDelete;
    private boolean CHOICE_MODE_ENABLED = false;
    private HistoryArrayAdapter adapt;
    private Pet currentPet;

    private ListView historyListView;

    private RelativeLayout profileCardRelativeLayout;
    private ImageView petCardImageView;
    private TextView petCardNameTextView;
    private TextView petCardOwnerTextView;
    private TextView petCardContactTextView;

    private FloatingActionButton addNewHistoryButton;
    private FloatingActionButton deleteHistoryButton;
    private int ACTIVITY_MODE_EDIT = 2;

    public void onBackPressed()
    {
        this.startActivity(new Intent(getBaseContext(),PetListActivity.class));
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        mDBHelper = new DBHelper(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        addNewHistoryButton = (FloatingActionButton) findViewById(R.id.btnAddNewHistory);
        deleteHistoryButton = (FloatingActionButton) findViewById(R.id.btnDeleteHistory);

        historyListView = (ListView) findViewById(R.id.historyListView);

        historyToDelete = new ArrayList<>();
        Intent i = getIntent();
        currentPet = (Pet) i.getSerializableExtra("EXTRA_SERIALIZED_PET");
        if(currentPet.getName().isEmpty()){
            getSupportActionBar().setTitle("Pet's History");
        } else {
            getSupportActionBar().setTitle(currentPet.getName()+"'s History");
        }



        ListView historyListView = (ListView) findViewById(R.id.historyListView);

        View header = getLayoutInflater().inflate(R.layout.history_list_header,null);
        historyListView.addHeaderView(header);

        petCardImageView = (ImageView) findViewById(R.id.petProfileCardImage);
        petCardNameTextView = (TextView) findViewById(R.id.petProfileCardName);
        petCardOwnerTextView = (TextView) findViewById(R.id.petProfileCardOwner);
        petCardContactTextView = (TextView) findViewById(R.id.petProfileCardContact);
        profileCardRelativeLayout = (RelativeLayout) findViewById(R.id.petProfileCard);

        RoundedBitmapDrawable roundedBitmapDrawable = MakeRoundableImage(getBaseContext(), currentPet.getImagePath());

        if(roundedBitmapDrawable != null)
            petCardImageView.setImageDrawable(roundedBitmapDrawable);
        petCardNameTextView.setText(currentPet.getName());
        petCardOwnerTextView.setText(currentPet.getOwnerName());
        petCardContactTextView.setText(currentPet.getContact());



        profileCardRelativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HistoryListActivity.this, AddPetActivity.class);
                intent.putExtra("EXTRA_SERIALIZED_PET", currentPet);
                intent.putExtra("EXTRA_ACTIVITY_MODE", ACTIVITY_MODE_EDIT);
                startActivity(intent);
            }
        });

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int positionAfterHeader = position - 1;

                Intent intent = new Intent(getBaseContext(), HistoryListItemPage.class);
                intent.putExtra("EXTRA_SERIALIZED_PET", currentPet);
                intent.putExtra("EXTRA_SERIALIZED_HISTORY", list.get(positionAfterHeader));
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

            case R.id.action_delete:
                Toast.makeText(getApplicationContext(), "Clicked DELETE", Toast.LENGTH_SHORT).show();
                historyToDelete.clear();
                CHOICE_MODE_ENABLED = !CHOICE_MODE_ENABLED;
                if(CHOICE_MODE_ENABLED){
                    addNewHistoryButton.setVisibility(View.INVISIBLE);
                    deleteHistoryButton.setVisibility(View.VISIBLE);
                } else {
                    addNewHistoryButton.setVisibility(View.VISIBLE);
                    deleteHistoryButton.setVisibility(View.INVISIBLE);
                }
                adapt.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void btnAddNewHistClick(View view) {
        Intent intent = new Intent(getBaseContext(),AddHistoryActivity.class);
        intent.putExtra("EXTRA_SERIALIZED_PET", currentPet);
        startActivity(intent);
    }



    public void btnDeleteHistClick(View view) {
        int deleteCount = historyToDelete.size();

        new AlertDialog.Builder(this)
                .setTitle("Delete Visit History")
                .setMessage("Do you really want to delete " + String.valueOf(deleteCount) + " item(s)?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(), "Deleted items(s)", Toast.LENGTH_SHORT).show();
                        CHOICE_MODE_ENABLED = false;
                        addNewHistoryButton.setVisibility(View.VISIBLE);
                        deleteHistoryButton.setVisibility(View.INVISIBLE);
                        mDBHelper.deleteSelectedHistory(historyToDelete);
                        historyToDelete.clear();
                        onHistListUpdated();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void onHistListUpdated(){
        list = mDBHelper.getAllPetHistory(currentPet.getId());
        adapt = new HistoryListActivity.HistoryArrayAdapter(this, R.layout.history_item, list);
        ListView historyListView = (ListView) findViewById(R.id.historyListView);
        historyListView.setAdapter(adapt);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        currentPet = (Pet) i.getSerializableExtra("EXTRA_SERIALIZED_PET");
        list = mDBHelper.getAllPetHistory(currentPet.getId());
        adapt = new HistoryArrayAdapter(this, R.layout.history_item, list);
        ListView historyListView = (ListView) findViewById(R.id.historyListView);
        historyListView.setAdapter(adapt);
    }


    //******************* ADAPTER ******************************
    private class HistoryArrayAdapter extends ArrayAdapter<History> {
        Context context;
        List<History> historyList = new ArrayList<History>();

        public HistoryArrayAdapter(Context c, int rId, List<History> objects) {
            super(c, rId, objects);
            historyList = objects;
            context = c;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final History history = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
            }
            // Lookup view for data population
            TextView histAge = (TextView) convertView.findViewById(R.id.lblHistAge);
            TextView histWeight = (TextView) convertView.findViewById(R.id.lblHistWeight);
            TextView histDescription = (TextView) convertView.findViewById(R.id.lblHistDescription);
            final CheckBox histListCheckBox = (CheckBox) convertView.findViewById(R.id.histListCheckBox);
            final ImageButton editImageButton = (ImageButton) convertView.findViewById(R.id.histListEditButton);

            histListCheckBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    final boolean isChecked = histListCheckBox.isChecked();
                    if (isChecked){
                        historyToDelete.add(history);
                    }else {
                        historyToDelete.remove(history);
                    }
                }
            });

            editImageButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(HistoryListActivity.this, AddHistoryActivity.class);
                            intent.putExtra("EXTRA_SERIALIZED_PET", currentPet);
                            intent.putExtra("EXTRA_SERIALIZED_HISTORY", history);
                            intent.putExtra("EXTRA_ACTIVITY_MODE", ACTIVITY_MODE_EDIT);
                            startActivity(intent);
                        }
                    });


            // Populate the data into the template view using the data object
            histAge.setText(String.valueOf(history.getAge()));
            histWeight.setText(String.valueOf(history.getWeight()));
            histDescription.setText(history.getDescription());
            // Return the completed view to render on screen

            if(CHOICE_MODE_ENABLED){
                histListCheckBox.setVisibility(View.VISIBLE);
                editImageButton.setVisibility(View.GONE);
            }
            else{
                histListCheckBox.setVisibility(View.INVISIBLE);
                editImageButton.setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }

}
