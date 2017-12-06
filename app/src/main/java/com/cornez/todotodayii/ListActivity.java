package com.cornez.todotodayii;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    protected DBHelper mDBHelper;
    private List<Pet_Details> list;
    private MyAdapter adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mDBHelper = new DBHelper(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        list = mDBHelper.getAllTasks();
        adapt = new MyAdapter(this, R.layout.todo_item, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);
    }
    public void clearTasks(View view) {
        mDBHelper.clearAll(list);
        adapt.notifyDataSetChanged();
        // mDBHelper.resetThis();
    }
    public void deleteDone(View view) {
        mDBHelper.deleteSelected(list);
        adapt.notifyDataSetChanged();
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            //  User user = getItem(position);
            RadioButton isRadioBtn = null;
            //  Pet_Details dataProvider = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            //  ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.todo_item, parent, false);

                isRadioBtn = (RadioButton) convertView.findViewById(R.id.radioButton);
                convertView.setTag(isRadioBtn);

//                viewHolder.name = (TextView) convertView.findViewById(R.id.textViewName);
//                viewHolder.breed = (TextView) convertView.findViewById(R.id.textViewBreed);
//                viewHolder.age = (TextView) convertView.findViewById(R.id.textViewAge);
//                viewHolder.weight = (TextView) convertView.findViewById(R.id.textViewWeight);
//                viewHolder.owner = (TextView) convertView.findViewById(R.id.textViewOwner);
//                viewHolder.contact = (TextView) convertView.findViewById(R.id.textViewContact);
//                viewHolder.history = (TextView) convertView.findViewById(R.id.textViewHistory);

                isRadioBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RadioButton button = (RadioButton) view;
                        Pet_Details changeTask = (Pet_Details) button.getTag();
                        changeTask.setIs_done(button.isChecked() == true ? 1 : 0);
                        mDBHelper.updateTask(changeTask);
                    }
                });


            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                isRadioBtn = (RadioButton) convertView.getTag();
            }
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            Pet_Details current = taskList.get(position);
//              viewHolder.id.setText(dataProvider.get_id());
//            viewHolder.name.setText("Name: " + dataProvider.getName());
//            viewHolder.breed.setText("Breed: " + dataProvider.getBreed());
//            viewHolder.age.setText("Age: " + String.valueOf(dataProvider.getAge()));
//            viewHolder.weight.setText("Weight: " + String.valueOf(dataProvider.getWeight()));
//            viewHolder.owner.setText("Owner: " + dataProvider.getOwnerName());
//            viewHolder.contact.setText("Contact: " + PhoneNumberUtils.formatNumber(dataProvider.getContact()));
//            viewHolder.history.setText("History: " + dataProvider.getHistory());
            isRadioBtn.setText("name: " + current.getName() + " " +"breed: " + current.getBreed());
            //isRadioBtn.setText(current.getBreed());
            isRadioBtn.setChecked(current.getIs_done() == 1 ? true : false);
            isRadioBtn.setTag(current);
            return convertView;

        }

    }

}
