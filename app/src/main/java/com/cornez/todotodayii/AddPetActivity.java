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


public class AddPetActivity extends Activity {

    protected DBHelper mDBHelper;
    private List<Pet> list;
//    private PetArrayAdapter adapt;

    private EditText editPetName;
    private EditText editPetBreed;
    private EditText editPetAge;
    private EditText editPetOwner;
    private EditText editPetContact;
    private TextView textViewImagePath;

    private ImageButton addImageButton;
    public static final int CAMERA_PIC_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifyStoragePermissions(this);

        // TASK 1: LAUNCH THE LAYOUT REPRESENTING THE MAIN ACTIVITY
        setContentView(R.layout.pet_add);

        // TASK 2: ESTABLISH REFERENCES TO THE UI ELEMENTS LOCATED ON THE LAYOUT
        editPetName = (EditText) findViewById(R.id.editPetName);
        editPetBreed = (EditText) findViewById(R.id.editPetBreed);
        editPetAge = (EditText) findViewById(R.id.editPetAge);
        editPetOwner = (EditText) findViewById(R.id.editPetOwnerName);
        editPetContact = (EditText) findViewById(R.id.editPetContact);
        textViewImagePath = (TextView) findViewById(R.id.textViewImagePath);

        // TASK 3: SET UP THE DATABASE
        mDBHelper = new DBHelper(this);
        /*
        list = mDBHelper.getAllTasks();
        adapt = new MyAdapter(this, R.layout.todo_item, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);
        */

        // TASK 4: ADD THE EVENT LISTENERS
        addImageButtonListener();
    }

    public void btnSaveNewPetClick(View view){
        String name = editPetName.getText().toString();
        String breed = editPetBreed.getText().toString();
        Integer age = Integer.valueOf(editPetAge.getText().toString());
        String owner = editPetOwner.getText().toString();
        String contact = editPetContact.getText().toString();
        String imagePath = textViewImagePath.getText().toString();

        // validation for empty fields
        if (name.isEmpty() || breed.isEmpty() || age <= 0 || owner.isEmpty() || contact.isEmpty()) {
            Toast.makeText(getApplicationContext(), "A field can not be blank", Toast.LENGTH_SHORT).show();
        } else {

            //BUILD A NEW PET ITEM AND ADD IT TO THE DATABASE
            Pet pet = new Pet(-1, name, breed, age, owner, imagePath, contact);
            mDBHelper.addPet(pet);

            // CLEAR OUT THE PET EDIT VIEWS
            editPetName.setText("");
            editPetBreed.setText("");
            editPetAge.setText("");
            editPetOwner.setText("");
            editPetContact.setText("");
            textViewImagePath.setText("");

//            /* ADD THE TASK AND SET A NOTIFICATION OF CHANGES */
//            adapt.add(pet);
//            adapt.notifyDataSetChanged();
//            Intent intent = new Intent(AddPetActivity.this, ListActivity.class);
//            startActivity(intent);
        }
    }

    private void saveImage(Bitmap finalBitmap, String image_name) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.d("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            textViewImagePath.setText(root+fname);
        } catch (Exception e) {
            e.printStackTrace();
            textViewImagePath.setText("Error saving image");
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
//            ((ImageButton) view).setImageResource(R.drawable.icon2);
            ImageButton imageview = (ImageButton) findViewById(R.id.addImageButton); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
            saveImage(image,"TestImageName");
        }
    }



//    private class MyAdapter extends ArrayAdapter<Pet_Details> {
//        Context context;
//        List<Pet_Details> taskList = new ArrayList<Pet_Details>();
//
//        public MyAdapter(Context c, int rId, List<Pet_Details> objects) {
//            super(c, rId, objects);
//            taskList = objects;
//            context = c;
//        }

//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // Get the data item for this position
//            //  User user = getItem(position);
//            RadioButton isRadioBtn = null;
//            //  Pet_Details dataProvider = getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            //  ViewHolder viewHolder; // view lookup cache stored in tag
//            if (convertView == null) {
//                // If there's no view to re-use, inflate a brand new view for row
//                LayoutInflater inflater = (LayoutInflater) context
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.todo_item, parent, false);
//
//                isRadioBtn = (RadioButton) convertView.findViewById(R.id.radioButton);
//                convertView.setTag(isRadioBtn);
//
////                viewHolder.name = (TextView) convertView.findViewById(R.id.textViewName);
////                viewHolder.breed = (TextView) convertView.findViewById(R.id.textViewBreed);
////                viewHolder.age = (TextView) convertView.findViewById(R.id.textViewAge);
////                viewHolder.weight = (TextView) convertView.findViewById(R.id.textViewWeight);
////                viewHolder.owner = (TextView) convertView.findViewById(R.id.textViewOwner);
////                viewHolder.contact = (TextView) convertView.findViewById(R.id.textViewContact);
////                viewHolder.history = (TextView) convertView.findViewById(R.id.textViewHistory);
//
//                isRadioBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RadioButton button = (RadioButton) view;
//                        Pet_Details changeTask = (Pet_Details) button.getTag();
//                        changeTask.setIs_done(button.isChecked() == true ? 1 : 0);
//                        mDBHelper.updateTask(changeTask);
//                    }
//                });
//
//
//            } else {
//                // View is being recycled, retrieve the viewHolder object from tag
//                isRadioBtn = (RadioButton) convertView.getTag();
//            }
//            // Populate the data from the data object via the viewHolder object
//            // into the template view.
//            Pet_Details current = taskList.get(position);
////              viewHolder.id.setText(dataProvider.get_id());
////            viewHolder.name.setText("Name: " + dataProvider.getName());
////            viewHolder.breed.setText("Breed: " + dataProvider.getBreed());
////            viewHolder.age.setText("Age: " + String.valueOf(dataProvider.getAge()));
////            viewHolder.weight.setText("Weight: " + String.valueOf(dataProvider.getWeight()));
////            viewHolder.owner.setText("Owner: " + dataProvider.getOwnerName());
////            viewHolder.contact.setText("Contact: " + PhoneNumberUtils.formatNumber(dataProvider.getContact()));
////            viewHolder.history.setText("History: " + dataProvider.getHistory());
//            isRadioBtn.setText("name: " + current.getName() + " " +"breed: " + current.getBreed());
//            //isRadioBtn.setText(current.getBreed());
//            isRadioBtn.setChecked(current.getIs_done() == 1 ? true : false);
//            isRadioBtn.setTag(current);
//            return convertView;
//
//        }

   // }


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
