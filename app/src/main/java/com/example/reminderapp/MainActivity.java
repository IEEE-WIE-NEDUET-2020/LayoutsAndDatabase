package com.example.reminderapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button but1, but2, but3, but4, but5, but6;
    DatabaseReference myDatabase;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.d("Hello", "hello world");
//        Toolbar toolbar= findViewById(R.id.toolbar);
//        TextView toolbar_text = findViewById(R.id.toolbar_text);
//
//        toolbar_text.setText("Tests");
//        setSupportActionBar(toolbar);



        Button subject1 = findViewById(R.id.subject1);
        Button subject2 = findViewById(R.id.subject2);
        Button subject3 = findViewById(R.id.subject3);
        Button subject4 = findViewById(R.id.subject4);
        Button subject5 = findViewById(R.id.subject5);
        Button subject6 = findViewById(R.id.subject6);

        subject1.setText("Tests/Quizes");
        subject2.setText("Assignments");
        subject3.setText("Schedule");
        subject4.setText("Alerts");
        subject5.setText("Notes");
        subject6.setText("Subject 6");

        subject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Subject1Test.class);
                startActivity(intent);

            }
        });

        subject2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AssignmentsActivity.class);
                startActivity(intent);
            }
        });

        subject3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScheduledActivity.class);
                startActivity(intent);

            }
        });

        subject4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlertsActivity.class);
                startActivity(intent);

            }
        });

        subject5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);

            }
        });



        ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.setTitle("Reminder App");
            TextView tv = new TextView(getApplicationContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
            tv.setLayoutParams(lp);
            tv.setText(bar.getTitle());
//            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            bar.setTitle("Reminder App");
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            bar.setCustomView(tv);
        }
    }


//
//        setSupportActionBar(toolbar);
//        toolbar_title.setText(toolbar.getTitle());
//        getSupportActionBar().setDisplayShowTitleEnabled(true);




}




