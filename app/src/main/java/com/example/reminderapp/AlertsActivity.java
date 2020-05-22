package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {

    DatabaseReference myDatabase;

    ListView alertListView;
    List<AlertsDB> alert_list;
    ArrayList<String> str;
    String[] mydata = {};
    AlertsAdapter adapter;

    private EditText topicText;
    private EditText detailsText;

    ProgressBar mProgressbar;
    TextView mTextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        mProgressbar = (ProgressBar) findViewById(R.id.progress_circular);
        mTextview = (TextView) findViewById(R.id.no_notes_textview);

        mProgressbar.setVisibility(View.VISIBLE);
        mTextview.setVisibility(View.GONE);



        myDatabase = FirebaseDatabase.getInstance().getReference("alerts");

        alertListView = (ListView) findViewById(R.id.listViewAlerts);
        alert_list = new ArrayList<>();
        str = new ArrayList<String>();
        ImageView im1 = (ImageView) findViewById(R.id.im1);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setImageResource(R.drawable.ic_add);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.new1)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        ActionBar bar = getSupportActionBar();

        if(bar!=null){
            bar.setTitle("Alerts");
            TextView tv = new TextView(getApplicationContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
            tv.setLayoutParams(lp);
            tv.setText(bar.getTitle());
//            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            bar.setCustomView(tv);
        }


        alertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String first_letter = str.get(position);
                String temp2 = String.valueOf(first_letter.charAt(0)).toLowerCase();

                AlertsDB alert = alert_list.get(position);
                showDialog(temp2, alert.getTitle(), alert.getDetails());


            }
        });



        alertListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        alertListView.setMultiChoiceModeListener(new  AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean  onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }


            @Override
            public void  onDestroyActionMode(ActionMode mode) {
            }
            @Override
            public boolean  onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.multiple_delete, menu);
                return true;
            }
            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {

                switch  (item.getItemId()) {

                    case R.id.selectAll:

                        final int checkedCount  = alert_list.size();
                        adapter.removeSelection();
                        for (int i = 0; i <  checkedCount; i++) {

                            alertListView.setItemChecked(i,   true);
                        }
                        mode.setTitle(checkedCount  + "  Selected");

                        return true;

                    case R.id.delete:
                        androidx.appcompat.app.AlertDialog.Builder  builder = new androidx.appcompat.app.AlertDialog.Builder(

                                AlertsActivity.this);

                        builder.setMessage("Do you  want to delete selected record(s)?");
                        builder.setNegativeButton("No", new  DialogInterface.OnClickListener() {

                            @Override

                            public void  onClick(DialogInterface dialog, int which) {
                            }

                        });

                        builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {

                            @Override

                            public void  onClick(DialogInterface dialog, int which) {

                                SparseBooleanArray selected = adapter.getSelectedIds();

                                for (int i =  (selected.size() - 1); i >= 0; i--) {

                                    if  (selected.valueAt(i)) {
//

                                        AlertsDB selecteditem = adapter.getItem(selected.keyAt(i));
                                        String assignId = selecteditem.getId();
                                        DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("alerts").child(assignId);
                                        newReference.removeValue();
                                    }
                                }
                                mode.finish();
                                selected.clear();
                            }

                        });

//
//

                        AlertDialog alertDialog = builder.create();
                        alertDialog.setIcon(R.drawable.questionicon);// dialog  Icon
                        alertDialog.setTitle("Confirmation"); // dialog  Title
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.CENTER);

                        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        buttonbackground.setTextColor(Color.BLUE);

                        Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        buttonbackground1.setTextColor(Color.BLUE);



                        return true;
                    default:
                        return false;

                }
            }

            @Override
            public void  onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount  = alertListView.getCheckedItemCount();
                mode.setTitle(checkedCount  + "  Selected");
                adapter.toggleSelection(position);

            }

        });



    }


    private Integer mapping(String mychar) {
        Integer[] imgname = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z };
        String myabc = "abcdefghijklmnopqrstuvwxyz";
        int pos = myabc.indexOf(mychar);
        return imgname[pos];
    }

    @Override
    protected void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                alert_list.clear();
                str.clear();
                for(DataSnapshot alert_snapshot : dataSnapshot.getChildren()){
                    AlertsDB alert = alert_snapshot.getValue(AlertsDB.class);
                    alert_list.add(alert);

                    String firstLetter = alert_snapshot.child("title").getValue(String.class);
                    str.add(firstLetter);


                }

                if (alert_list.size()==0){
                    mProgressbar.setVisibility(View.GONE);
                    mTextview.setVisibility(View.VISIBLE);
                }

                else {

                    Collections.reverse(alert_list);
                    Collections.reverse(str);


                    int size = str.size();
                    final Integer[] imgname = new Integer[size];
                    int c = 0;
                    for (String i : str) {
                        String temp = String.valueOf(i.charAt(0)).toLowerCase();
                        imgname[c] = mapping(temp);
                        c++;
                    }


                    adapter = new AlertsAdapter(AlertsActivity.this, alert_list, imgname);
                    alertListView.setAdapter(adapter);
                    alertListView.setLongClickable(true);

                    mProgressbar.setVisibility(View.GONE);
                    mTextview.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void openDialog(){
       android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_alerts_dialog, null);





        builder.setView(view)
                .setTitle("New Alert")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addAlerttoDB();

                    }


                });

        topicText = view.findViewById(R.id.editTextTopic);
        detailsText = view.findViewById(R.id.editTextDetails);


        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 600);
        alertDialog.getWindow().setGravity(Gravity.CENTER);

        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(Color.BLUE);

        Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLUE);



    }

    public void showDialog( String temp, String topicName, String detailsName){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.alertdisplay_dialog, null);
        builder.setView(view)

                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
//                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        delTest(testId);
//
//                    }
//                });

        final ImageView icon = (ImageView) view.findViewById(R.id.image);
        final TextView topic = (TextView) view.findViewById(R.id.textTopic);
        final TextView details = (TextView) view.findViewById(R.id.textforDetails);

        icon.setImageResource(mapping(temp));
        topic.setText(topicName);
        details.setText(detailsName);




        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(650, 600);
//        alertDialog.getWindow().setGravity(Gravity.CENTER);

    }

    public void addAlerttoDB(){
        String topic = topicText.getText().toString().trim();
        String desc = detailsText.getText().toString().trim();


        if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(desc)){

            String id = myDatabase.push().getKey();

            //switch case
            AlertsDB alert = new AlertsDB(topic, desc, id );

            myDatabase.child(id).setValue(alert);

            Toast.makeText(AlertsActivity.this, "New test added!", Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(AlertsActivity.this, "Please give complete information", Toast.LENGTH_LONG).show();
        }


    }





}
