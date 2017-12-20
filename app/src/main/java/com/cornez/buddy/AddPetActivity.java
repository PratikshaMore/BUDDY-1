package com.cornez.buddy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import android.view.View.OnClickListener;

import static com.cornez.buddy.Utils.MakeRoundableImage;
import static com.cornez.buddy.Utils.getBitmap;
import static com.cornez.buddy.Utils.saveImage;


public class AddPetActivity extends AppCompatActivity {

    protected DBHelper mDBHelper;
    private List<Pet> list;
    private Pet petToEdit;

    private EditText editPetName;
    private EditText editPetBreed;
    private EditText editPetOwner;
    private EditText editPetContact;
    private TextView textViewImagePath;
    private Button btnAddPet;

    private ImageButton addImageButton;

    public int ACTIVITY_MODE_ADD = 1;
    public int ACTIVITY_MODE_EDIT = 2;

    public static final int CAMERA_PIC_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private int currentActivityMode = 1;


    public void onBackPressed()
    {
        this.startActivity(new Intent(getBaseContext(),PetListActivity.class));
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TASK 1: LAUNCH THE LAYOUT REPRESENTING THE MAIN ACTIVITY
        setContentView(R.layout.pet_add);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Save Pet");

        verifyStoragePermissions(this);

        // TASK 2: ESTABLISH REFERENCES TO THE UI ELEMENTS LOCATED ON THE LAYOUT
        editPetName = (EditText) findViewById(R.id.editPetName);
        editPetBreed = (EditText) findViewById(R.id.editPetBreed);
        editPetOwner = (EditText) findViewById(R.id.editPetOwnerName);
        editPetContact = (EditText) findViewById(R.id.editPetContact);
        textViewImagePath = (TextView) findViewById(R.id.textViewImagePath);
        addImageButton = (ImageButton) findViewById(R.id.addImageButton);

        btnAddPet = (Button) findViewById(R.id.btnAddNewPet);

        // TASK 3: SET UP THE DATABASE
        mDBHelper = new DBHelper(this);


        Intent i = getIntent();
        currentActivityMode = i.getIntExtra("EXTRA_ACTIVITY_MODE", 1);

        // TASK 4: ADD THE EVENT LISTENERS
        if(currentActivityMode == ACTIVITY_MODE_EDIT) {
            petToEdit = (Pet) i.getSerializableExtra("EXTRA_SERIALIZED_PET");
            editPetName.setText(petToEdit.getName());
            editPetBreed.setText(petToEdit.getBreed());
            editPetOwner.setText(petToEdit.getOwnerName());
            editPetContact.setText(petToEdit.getContact());
            textViewImagePath.setText(petToEdit.getImagePath());
            Bitmap petProfileImage = getBitmap(petToEdit.getImagePath());
            if(petProfileImage != null){
                addImageButton.setImageBitmap(petProfileImage);
            }
        } else {
            // CLEAR OUT THE PET EDIT VIEWS
            editPetName.setText("");
            editPetBreed.setText("");
            editPetOwner.setText("");
            editPetContact.setText("");
            textViewImagePath.setText("");
        }
        addImageButtonListener();
    }

    public void btnSavePetClick(View view){
        String name = editPetName.getText().toString();
        String breed = editPetBreed.getText().toString();
        String owner = editPetOwner.getText().toString();
        String contact = editPetContact.getText().toString();
        String imagePath = textViewImagePath.getText().toString();

        // validation for empty fields
        if (name.isEmpty() || breed.isEmpty() || owner.isEmpty() || contact.isEmpty()) {
            Toast.makeText(getApplicationContext(), "A field can not be blank", Toast.LENGTH_SHORT).show();
        } else {

            Pet pet = new Pet(-1, name, breed, owner, imagePath, contact);
            Pet insertedPet;
            //BUILD A NEW PET ITEM AND ADD IT TO THE DATABASE
            if(currentActivityMode == ACTIVITY_MODE_EDIT){
                // GET THE ID OF THE CURRENT PET FROM THE INTENT
                pet.setId(petToEdit.getId());
                mDBHelper.updatePet(pet);
                Toast.makeText(getApplicationContext(), "Update complete", Toast.LENGTH_SHORT).show();
                /* PASS THE VALUES TO THE PET LIST ACTIVITY */
                Intent intent = new Intent(getBaseContext(), PetListActivity.class);
                startActivity(intent);
            } else {
                insertedPet = mDBHelper.addPet(pet);
                Toast.makeText(getApplicationContext(), "Insert complete", Toast.LENGTH_SHORT).show();
                /* PASS THE VALUES TO THE HISTORY ACTIVITY */
                Intent intent = new Intent(getBaseContext(), AddHistoryActivity.class);
                intent.putExtra("EXTRA_SERIALIZED_PET", insertedPet);
                startActivity(intent);
            }


        }
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

    public void addImageButtonListener() {
        addImageButton = (ImageButton) findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageButton imageButton = (ImageButton) findViewById(R.id.addImageButton); //sets imageview as the bitmap
            RoundedBitmapDrawable roundedBitmapDrawable = MakeRoundableImage(getBaseContext(), image);
            imageButton.setImageDrawable(roundedBitmapDrawable);
            String savedImagePath = saveImage(image);
            textViewImagePath.setText(savedImagePath);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu.
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
