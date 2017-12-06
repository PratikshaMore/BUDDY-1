package com.cornez.todotodayii;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddDetailsActivity extends Activity {

    protected DBHelper mDBHelper;
    private List<Pet_Details> list;
    private MyAdapter adapt;
    private EditText myTaskName;
    private EditText myTaskBreed;
    private EditText myTaskAge;
    private EditText myTaskWeight;
    private EditText myTaskOwner;
    private EditText myTaskContact;
    private EditText myTaskHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TASK 1: LAUNCH THE LAYOUT REPRESENTING THE MAIN ACTIVITY
        setContentView(R.layout.activity_add_details);

        // TASK 2: ESTABLISH REFERENCES TO THE UI
        //      ELEMENTS LOCATED ON THE LAYOUT

        myTaskName = (EditText) findViewById(R.id.editText3);
        myTaskBreed = (EditText) findViewById(R.id.editText10);
        myTaskAge = (EditText) findViewById(R.id.editText9);
        myTaskWeight = (EditText) findViewById(R.id.editText11);
        myTaskOwner = (EditText) findViewById(R.id.editText);
        myTaskContact = (EditText) findViewById(R.id.editTextContact);
        myTaskHistory = (EditText) findViewById(R.id.editText12);

        // TASK 3: SET UP THE DATABASE
        mDBHelper = new DBHelper(this);
        /*
        list = mDBHelper.getAllTasks();
        adapt = new MyAdapter(this, R.layout.todo_item, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = mDBHelper.getAllTasks();
        adapt = new MyAdapter(this, R.layout.todo_item, list);
        //ListView listTask = (ListView) findViewById(R.id.listView1);
        //listTask.setAdapter(adapt);
    }

    //BUTTON CLICK EVENT FOR ADDING A TODO TASK
    public void addTaskNow(View view) {
        String name = myTaskName.getText().toString();
        String breed = myTaskBreed.getText().toString();
        Integer age = Integer.valueOf(myTaskAge.getText().toString());
        Integer weight = Integer.valueOf(myTaskWeight.getText().toString());
        String owner = myTaskOwner.getText().toString();
        String contact = myTaskContact.getText().toString();
        String history = myTaskHistory.getText().toString();

        //validation for empty fields
        if (name.isEmpty() || breed.isEmpty() || owner.isEmpty() || history.isEmpty()|| contact.isEmpty()) {
            Toast.makeText(getApplicationContext(), "A field can not be blank", Toast.LENGTH_SHORT).show();
        } else {

            //BUILD A NEW TASK ITEM AND ADD IT TO THE DATABASE
            Pet_Details task = new Pet_Details(name, breed, age, weight, owner, contact, history,0);
            mDBHelper.addToDoItem(task);

            // CLEAR OUT THE TASK EDITVIEW
            myTaskName.setText("");
            myTaskBreed.setText("");
            myTaskAge.setText("");
            myTaskWeight.setText("");
            myTaskOwner.setText("");
            myTaskHistory.setText("");
            myTaskContact.setText("");
            /* ADD THE TASK AND SET A NOTIFICATION OF CHANGES */
            adapt.add(task);
            adapt.notifyDataSetChanged();
            Intent intent = new Intent(AddDetailsActivity.this,ListActivity.class);
            startActivity(intent);
        }
    }


    //******************* ADAPTER ******************************
    private class MyAdapter extends ArrayAdapter<Pet_Details> {
        Context context;
        List<Pet_Details> taskList = new ArrayList<Pet_Details>();

        public MyAdapter(Context c, int rId, List<Pet_Details> objects) {
            super(c, rId, objects);
            taskList = objects;
            context = c;
        }

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
