package com.example.reminderapp;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.reminderapp.ui.main.SectionsPagerAdapter;

public class ScheduleActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule2);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setTitle("Schedule");



//        ActionBar bar = getSupportActionBar();
//
//        if (bar != null) {
//            bar.setTitle("Reminder App");
//            TextView tv = new TextView(getApplicationContext());
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
//                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
//            tv.setLayoutParams(lp);
//            tv.setText(bar.getTitle());
////            tv.setGravity(Gravity.CENTER);
//            tv.setTextColor(Color.WHITE);
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
////            bar.setTitle("Reminder App");
//            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//            bar.setCustomView(tv);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addition, menu);
        return true;
    }
}