package com.example.reminderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduledActivity extends AppCompatActivity {

    private static final String TAG = "ScheduledActivity";

    private ViewPager mViewPager;

    private TabLayout tabLayout;

    Spinner day_spinner;
    Spinner subject_spinner;
    Spinner code_spinner;
    Spinner time_spinner;


    String day_of_week;
    String selection;
    ArrayAdapter<CharSequence> timings_adapter;

    private AppBarLayout appBarLayout;

    ///for monday to thursday///
    Spinner spinnerSubject1;
    Spinner spinnerSubject2;
    Spinner spinnerSubject3;
    Spinner spinnerSubject4;
    Spinner spinnerSubject5;
    Spinner spinnerSubject6;
    Spinner spinnerSubject7;
    Spinner spinnerSubject8;

    Spinner courseCode1;
    Spinner courseCode2;
    Spinner courseCode3;
    Spinner courseCode4;
    Spinner courseCode5;
    Spinner courseCode6;
    Spinner courseCode7;
    Spinner courseCode8;

    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    TextView time5;
    TextView time6;
    TextView time7;
    TextView time8;

    ////for friday only/////
    Spinner fridaySubject1;
    Spinner fridaySubject2;
    Spinner fridaySubject3;
    Spinner fridaySubject4;
    Spinner fridaySubject5;
    Spinner fridaySubject6;
    Spinner fridaySubject7;


    Spinner fridayCode1;
    Spinner fridayCode2;
    Spinner fridayCode3;
    Spinner fridayCode4;
    Spinner fridayCode5;
    Spinner fridayCode6;
    Spinner fridayCode7;


    TextView fridaytime1;
    TextView fridaytime2;
    TextView fridaytime3;
    TextView fridaytime4;
    TextView fridaytime5;
    TextView fridaytime6;
    TextView fridaytime7;


    ///for mon to thursday practicals/////
    Spinner practicalSubject1 ;
    Spinner practicalSubject2 ;
    Spinner practicalSubject3 ;

    Spinner practicalCode1;
    Spinner practicalCode2;
    Spinner practicalCode3;


    TextView practicaltime1 ;
    TextView practicaltime2 ;
    TextView practicaltime3 ;

    ////for friday practicals only////
    Spinner fridayPracticalSubject1;
    Spinner fridayPracticalSubject2;
    Spinner fridayPracticalSubject3;


    Spinner fridayPracticalCode1;
    Spinner fridayPracticalCode2 ;
    Spinner fridayPracticalCode3 ;

    TextView fridayPracticaltime1 ;
    TextView fridayPracticaltime2 ;
    TextView fridayPracticaltime3;

    Spinner SubjectSpinner;
    Spinner CodeSpinner;
    TextView TimeText;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled);

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Schedule");


        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        DaysPageAdapter adapter = new DaysPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonFragment(), "MON");
        adapter.addFragment(new TuesFragment(), "TUES");
        adapter.addFragment(new WedFragment(), "WED");
        adapter.addFragment(new ThursFragment(), "THURS");
        adapter.addFragment(new FriFragment(), "FRI");
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addition, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                selection = "class";
                select_day(selection);
                break;


            case R.id.item2:
                selection = "practical";
                select_day(selection);
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    public void select_day(final String select) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.day_layout, null);

        final Spinner spinner = view.findViewById(R.id.dayofweek);


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select a day")) {

                } else {
                    day_of_week = parent.getItemAtPosition(position).toString();
//                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(view)
//                .setTitle("New Test")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dayText = spinner.getSelectedItem().toString();
                        if (dayText.equals("Select a day")) {
//
                            Toast.makeText(ScheduledActivity.this, "Please select a day", Toast.LENGTH_LONG).show();


                        } else {
                            switch (select) {
                                case "class":
                                    if (day_of_week.equals("Friday")) {
                                        add_friday_class(day_of_week);
                                    } else {
                                        add_class(day_of_week);

                                    }
                                    break;

                                case "practical":
                                    if (day_of_week.equals("Friday")) {
                                        add_friday_practical(day_of_week);


                                    } else {
                                        add_practical(day_of_week);

                                    }
                                    break;
                            }
                        }

                    }


                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(500, 300);
        alertDialog.getWindow().setGravity(Gravity.CENTER);

        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(Color.BLUE);

        Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLUE);

    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void add_class(final String day) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.classes_layout_dialog, null);

        spinnerSubject1 = view.findViewById(R.id.subject1);
        spinnerSubject2 = view.findViewById(R.id.subject2);
        spinnerSubject3 = view.findViewById(R.id.subject3);
        spinnerSubject4 = view.findViewById(R.id.subject4);
        spinnerSubject5 = view.findViewById(R.id.subject5);
        spinnerSubject6 = view.findViewById(R.id.subject6);
        spinnerSubject7 = view.findViewById(R.id.subject7);
        spinnerSubject8 = view.findViewById(R.id.subject8);

        ArrayAdapter<CharSequence> subject_adapter = ArrayAdapter.createFromResource(this, R.array.subject_names, android.R.layout.simple_spinner_item);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject1.setAdapter(subject_adapter);
        spinnerSubject2.setAdapter(subject_adapter);
        spinnerSubject3.setAdapter(subject_adapter);
        spinnerSubject4.setAdapter(subject_adapter);
        spinnerSubject5.setAdapter(subject_adapter);
        spinnerSubject6.setAdapter(subject_adapter);
        spinnerSubject7.setAdapter(subject_adapter);
        spinnerSubject8.setAdapter(subject_adapter);


        courseCode1 = view.findViewById(R.id.code1);
        courseCode2 = view.findViewById(R.id.code2);
        courseCode3 = view.findViewById(R.id.code3);
        courseCode4 = view.findViewById(R.id.code4);
        courseCode5 = view.findViewById(R.id.code5);
        courseCode6 = view.findViewById(R.id.code6);
        courseCode7 = view.findViewById(R.id.code7);
        courseCode8 = view.findViewById(R.id.code8);

        ArrayAdapter<CharSequence> code_adapter = ArrayAdapter.createFromResource(this, R.array.course_codes, android.R.layout.simple_spinner_item);
        code_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseCode1.setAdapter(code_adapter);
        courseCode2.setAdapter(code_adapter);
        courseCode3.setAdapter(code_adapter);
        courseCode4.setAdapter(code_adapter);
        courseCode5.setAdapter(code_adapter);
        courseCode6.setAdapter(code_adapter);
        courseCode7.setAdapter(code_adapter);
        courseCode8.setAdapter(code_adapter);


        time1 = view.findViewById(R.id.time);
        time2 = view.findViewById(R.id.time2);
        time3 = view.findViewById(R.id.time3);
        time4 = view.findViewById(R.id.time4);
        time5 = view.findViewById(R.id.time5);
        time6 = view.findViewById(R.id.time6);
        time7 = view.findViewById(R.id.time7);
        time8 = view.findViewById(R.id.time8);


        final ArrayList<ArrayList<Spinner>> listOLists = new ArrayList<ArrayList<Spinner>>();

        ArrayList<Spinner> firstList = new ArrayList<>();
        firstList.add(spinnerSubject1);
        firstList.add(courseCode1);


        ArrayList<Spinner> secondList = new ArrayList<>();
        secondList.add(spinnerSubject2);
        secondList.add(courseCode2);


        ArrayList<Spinner> thirdList = new ArrayList<>();
        thirdList.add(spinnerSubject3);
        thirdList.add(courseCode3);


        ArrayList<Spinner> fourthList = new ArrayList<>();
        fourthList.add(spinnerSubject4);
        fourthList.add(courseCode4);


        ArrayList<Spinner> fifthList = new ArrayList<>();
        fifthList.add(spinnerSubject5);
        fifthList.add(courseCode5);


        ArrayList<Spinner> sixthList = new ArrayList<>();
        sixthList.add(spinnerSubject6);
        sixthList.add(courseCode6);


        ArrayList<Spinner> seventhList = new ArrayList<>();
        seventhList.add(spinnerSubject7);
        seventhList.add(courseCode7);

        ArrayList<Spinner> eighthList = new ArrayList<>();
        eighthList.add(spinnerSubject8);
        eighthList.add(courseCode8);

        listOLists.add(firstList);
        listOLists.add(secondList);
        listOLists.add(thirdList);
        listOLists.add(fourthList);
        listOLists.add(fifthList);
        listOLists.add(sixthList);
        listOLists.add(seventhList);
        listOLists.add(eighthList);

        final ArrayList<TextView> timeList = new ArrayList<>();
        timeList.add(time1);
        timeList.add(time2);
        timeList.add(time3);
        timeList.add(time4);
        timeList.add(time5);
        timeList.add(time6);
        timeList.add(time7);
        timeList.add(time8);





        builder.setView(view)
//                .setTitle("New Test")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save_classes(day, listOLists, timeList, "class");

                    }


                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 1200);
        alertDialog.getWindow().setGravity(Gravity.CENTER);


    }

    private void add_practical(final String day) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.practical_layout_dialog, null);

        practicalSubject1 = view.findViewById(R.id.subject1);
        practicalSubject2 = view.findViewById(R.id.subject2);
        practicalSubject3 = view.findViewById(R.id.subject3);



        ArrayAdapter<CharSequence> subject_adapter = ArrayAdapter.createFromResource(this, R.array.prac_subjects, android.R.layout.simple_spinner_item);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        practicalSubject1.setAdapter(subject_adapter);
        practicalSubject2.setAdapter(subject_adapter);
        practicalSubject3.setAdapter(subject_adapter);




        practicalCode1 = view.findViewById(R.id.code1);
        practicalCode2 = view.findViewById(R.id.code2);
        practicalCode3 = view.findViewById(R.id.code3);



        ArrayAdapter<CharSequence> code_adapter = ArrayAdapter.createFromResource(this, R.array.prac_codes, android.R.layout.simple_spinner_item);
        code_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        practicalCode1.setAdapter(code_adapter);
        practicalCode2.setAdapter(code_adapter);
        practicalCode3.setAdapter(code_adapter);




        practicaltime1 = view.findViewById(R.id.time);
        practicaltime2 = view.findViewById(R.id.time2);
        practicaltime3 = view.findViewById(R.id.time3);




        final ArrayList<ArrayList<Spinner>> listOLists = new ArrayList<ArrayList<Spinner>>();

        ArrayList<Spinner> firstList = new ArrayList<>();
        firstList.add(practicalSubject1);
        firstList.add( practicalCode1);


        ArrayList<Spinner> secondList = new ArrayList<>();
        secondList.add( practicalSubject2);
        secondList.add( practicalCode2);


        ArrayList<Spinner> thirdList = new ArrayList<>();
        thirdList.add( practicalSubject3);
        thirdList.add( practicalCode3);



        listOLists.add(firstList);
        listOLists.add(secondList);
        listOLists.add(thirdList);



        final ArrayList<TextView> timeList = new ArrayList<>();
        timeList.add( practicaltime1);
        timeList.add( practicaltime2);
        timeList.add( practicaltime3);





        builder.setView(view)
//                .setTitle("New Test")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save_classes(day, listOLists, timeList, "practical");

                    }


                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 900);
        alertDialog.getWindow().setGravity(Gravity.CENTER);



    }

    private void add_friday_class(final String day) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.friday_layout_dialog, null);

        fridaySubject1 = view.findViewById(R.id.subject1);
        fridaySubject2 = view.findViewById(R.id.subject2);
        fridaySubject3 = view.findViewById(R.id.subject3);
        fridaySubject4 = view.findViewById(R.id.subject4);
        fridaySubject5 = view.findViewById(R.id.subject5);
        fridaySubject6 = view.findViewById(R.id.subject6);
        fridaySubject7 = view.findViewById(R.id.subject7);


        ArrayAdapter<CharSequence> subject_adapter = ArrayAdapter.createFromResource(this, R.array.subject_names, android.R.layout.simple_spinner_item);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridaySubject1.setAdapter(subject_adapter);
        fridaySubject2.setAdapter(subject_adapter);
        fridaySubject3.setAdapter(subject_adapter);
        fridaySubject4.setAdapter(subject_adapter);
        fridaySubject5.setAdapter(subject_adapter);
        fridaySubject6.setAdapter(subject_adapter);
        fridaySubject7.setAdapter(subject_adapter);



        fridayCode1 = view.findViewById(R.id.code1);
        fridayCode2 = view.findViewById(R.id.code2);
        fridayCode3 = view.findViewById(R.id.code3);
        fridayCode4 = view.findViewById(R.id.code4);
        fridayCode5 = view.findViewById(R.id.code5);
        fridayCode6 = view.findViewById(R.id.code6);
        fridayCode7 = view.findViewById(R.id.code7);


        ArrayAdapter<CharSequence> code_adapter = ArrayAdapter.createFromResource(this, R.array.course_codes, android.R.layout.simple_spinner_item);
        code_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridayCode1.setAdapter(code_adapter);
        fridayCode2.setAdapter(code_adapter);
        fridayCode3.setAdapter(code_adapter);
        fridayCode4.setAdapter(code_adapter);
        fridayCode5.setAdapter(code_adapter);
        fridayCode6.setAdapter(code_adapter);
        fridayCode7.setAdapter(code_adapter);



        fridaytime1 = view.findViewById(R.id.time);
        fridaytime2 = view.findViewById(R.id.time2);
        fridaytime3 = view.findViewById(R.id.time3);
        fridaytime4 = view.findViewById(R.id.time4);
        fridaytime5 = view.findViewById(R.id.time5);
        fridaytime6 = view.findViewById(R.id.time6);
        fridaytime7 = view.findViewById(R.id.time7);



        final ArrayList<ArrayList<Spinner>> listOLists = new ArrayList<ArrayList<Spinner>>();

        ArrayList<Spinner> firstList = new ArrayList<>();
        firstList.add(fridaySubject1);
        firstList.add(fridayCode1);


        ArrayList<Spinner> secondList = new ArrayList<>();
        secondList.add(fridaySubject2);
        secondList.add(fridayCode2);


        ArrayList<Spinner> thirdList = new ArrayList<>();
        thirdList.add(fridaySubject3);
        thirdList.add(fridayCode3);


        ArrayList<Spinner> fourthList = new ArrayList<>();
        fourthList.add(fridaySubject4);
        fourthList.add(fridayCode4);


        ArrayList<Spinner> fifthList = new ArrayList<>();
        fifthList.add(fridaySubject5);
        fifthList.add(fridayCode5);


        ArrayList<Spinner> sixthList = new ArrayList<>();
        sixthList.add(fridaySubject6);
        sixthList.add(fridayCode6);


        ArrayList<Spinner> seventhList = new ArrayList<>();
        seventhList.add(fridaySubject7);
        seventhList.add(fridayCode7);



        listOLists.add(firstList);
        listOLists.add(secondList);
        listOLists.add(thirdList);
        listOLists.add(fourthList);
        listOLists.add(fifthList);
        listOLists.add(sixthList);
        listOLists.add(seventhList);


        final ArrayList<TextView> timeList = new ArrayList<>();
        timeList.add(fridaytime1);
        timeList.add(fridaytime2);
        timeList.add(fridaytime3);
        timeList.add(fridaytime4);
        timeList.add(fridaytime5);
        timeList.add(fridaytime6);
        timeList.add(fridaytime7);






        builder.setView(view)
//                .setTitle("New Test")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save_classes(day, listOLists, timeList, "class");

                    }


                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 1200);
        alertDialog.getWindow().setGravity(Gravity.CENTER);



    }

    private void add_friday_practical(final String day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.friday_practical_dialog, null);

        fridayPracticalSubject1 = view.findViewById(R.id.subject1);
        fridayPracticalSubject2 = view.findViewById(R.id.subject2);
        fridayPracticalSubject3 = view.findViewById(R.id.subject3);



        ArrayAdapter<CharSequence> subject_adapter = ArrayAdapter.createFromResource(this, R.array.prac_subjects, android.R.layout.simple_spinner_item);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridayPracticalSubject1.setAdapter(subject_adapter);
        fridayPracticalSubject2.setAdapter(subject_adapter);
        fridayPracticalSubject3.setAdapter(subject_adapter);




        fridayPracticalCode1 = view.findViewById(R.id.code1);
        fridayPracticalCode2 = view.findViewById(R.id.code2);
        fridayPracticalCode3 = view.findViewById(R.id.code3);



        ArrayAdapter<CharSequence> code_adapter = ArrayAdapter.createFromResource(this, R.array.prac_codes, android.R.layout.simple_spinner_item);
        code_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fridayPracticalCode1.setAdapter(code_adapter);
        fridayPracticalCode2.setAdapter(code_adapter);
        fridayPracticalCode3.setAdapter(code_adapter);




        fridayPracticaltime1 = view.findViewById(R.id.time);
        fridayPracticaltime2 = view.findViewById(R.id.time2);
        fridayPracticaltime3 = view.findViewById(R.id.time3);




        final ArrayList<ArrayList<Spinner>> listOLists = new ArrayList<ArrayList<Spinner>>();

        ArrayList<Spinner> firstList = new ArrayList<>();
        firstList.add(fridayPracticalSubject1);
        firstList.add( fridayPracticalCode1);


        ArrayList<Spinner> secondList = new ArrayList<>();
        secondList.add( fridayPracticalSubject2);
        secondList.add( fridayPracticalCode2);


        ArrayList<Spinner> thirdList = new ArrayList<>();
        thirdList.add( fridayPracticalSubject3);
        thirdList.add( fridayPracticalCode3);



        listOLists.add(firstList);
        listOLists.add(secondList);
        listOLists.add(thirdList);



        final ArrayList<TextView> timeList = new ArrayList<>();
        timeList.add( fridayPracticaltime1);
        timeList.add( fridayPracticaltime2);
        timeList.add( fridayPracticaltime3);





        builder.setView(view)
//                .setTitle("New Test")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save_classes(day, listOLists, timeList, "practical");

                    }


                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 900);
        alertDialog.getWindow().setGravity(Gravity.CENTER);





    }

    private void save_classes(String day_of_week, ArrayList<ArrayList<Spinner>> listOfLists, ArrayList<TextView> timeList , String category) {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query classQuery = ref.child(day_of_week).orderByChild("category").equalTo(category);

        classQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot classSnapshot: dataSnapshot.getChildren()) {
                    classSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


        int i;
        for(i=0; i<listOfLists.size(); i++){
            SubjectSpinner = listOfLists.get(i).get(0);
            CodeSpinner = listOfLists.get(i).get(1);
            TimeText = timeList.get(i);

            String subjectText = SubjectSpinner.getSelectedItem().toString();
            String codeText = CodeSpinner.getSelectedItem().toString();
            String timeText = TimeText.getText().toString();

            /////// 8:30 TO 9:20 //////////////
            if (!subjectText.equals("Select a subject") && !codeText.equals("Select course code")) {
                switch (day_of_week){
                    case "Monday":
                        DatabaseReference monDatabase = FirebaseDatabase.getInstance().getReference("Monday");
                        String id1 = monDatabase.push().getKey();

                        MondayDB monDB = new MondayDB(day_of_week, subjectText, codeText, timeText, category);

                        monDatabase.child(id1).setValue(monDB);
                        break;

                    case "Tuesday":
                        DatabaseReference tuesDatabase = FirebaseDatabase.getInstance().getReference("Tuesday");
                        String id2 = tuesDatabase.push().getKey();

                        TuesDB tuesDB = new TuesDB(day_of_week, subjectText, codeText, timeText, category);

                        tuesDatabase.child(id2).setValue(tuesDB);
                        break;


                    case "Wednesday":
                        DatabaseReference wedDatabase = FirebaseDatabase.getInstance().getReference("Wednesday");
                        String id3 = wedDatabase.push().getKey();

                        WedDB wedDB = new WedDB(day_of_week, subjectText, codeText, timeText, category);

                        wedDatabase.child(id3).setValue(wedDB);
                        break;


                    case "Thursday":
                        DatabaseReference thursDatabase = FirebaseDatabase.getInstance().getReference("Thursday");
                        String id4 = thursDatabase.push().getKey();

                        ThursDB thursDB = new ThursDB(day_of_week, subjectText, codeText, timeText, category);

                        thursDatabase.child(id4).setValue(thursDB);
                        break;

                    case "Friday":
                        DatabaseReference friDatabase = FirebaseDatabase.getInstance().getReference("Friday");
                        String id5 = friDatabase.push().getKey();

                        FriDB friDB = new FriDB(day_of_week, subjectText, codeText, timeText, category);

                        friDatabase.child(id5).setValue(friDB);
                        break;

                }
                Toast.makeText(ScheduledActivity.this, "New test added!", Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(ScheduledActivity.this, "not successful", Toast.LENGTH_LONG).show();
            }

        }




    }
}
