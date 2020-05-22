package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class ShowTheNote extends AppCompatActivity {
    ImageView imgView;
    TextView textView_title, textView_note;
    Context mContext;

    View layout;
    View root;
    int color;

    Date original_date;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private  TimePickerDialog.OnTimeSetListener mTimePicker;

    String title ;
    String note  ;
    String imageURL ;
    String itemId;
    Calendar newCalender;

    ProgressBar mProgressbar;

    Button buttonOK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_the_note);

        mProgressbar = findViewById(R.id.progress_circular);
        mProgressbar.setVisibility(View.VISIBLE);


        ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.setTitle("My Notes");
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

        layout = findViewById(R.id.myLinearLayout);
        root = layout.getRootView();

        imgView = findViewById(R.id.note_imageView);
        textView_title = findViewById(R.id.note_textView_title);
        textView_note = findViewById(R.id.note_textView_note);

//        Bundle bundle  = getIntent().getExtras();
        title = getIntent().getExtras().getString("Title");
        note  = getIntent().getStringExtra("Note");
        imageURL = String.valueOf(getIntent().getStringExtra("ImageUrl"));
        int color = getIntent().getExtras().getInt("BackgroundColor");
        itemId = getIntent().getExtras().getString("id");





        if (color != 0) {
            root.setBackgroundColor(color);
        }

        if (title.equals("")){
            textView_title.setVisibility(View.GONE);
        }
        else {
            textView_title.setText(title);
        }

        if (note.equals("")) {

            textView_note.setVisibility(View.GONE);
        }
        else {
            textView_note.setText(note);
        }



        Picasso.with(this)
                .load(imageURL)
                .into(imgView);
        mProgressbar.setVisibility(View.GONE);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_and_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.reminder_icon:
                newCalender = Calendar.getInstance();
                addReminder();

                break;

            case R.id.delete_icon:
                deleteNote();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteNote(){
        androidx.appcompat.app.AlertDialog.Builder  builder = new androidx.appcompat.app.AlertDialog.Builder(

                ShowTheNote.this);

        builder.setMessage("Are you sure?");
        builder.setNegativeButton("No", new  DialogInterface.OnClickListener() {

            @Override

            public void  onClick(DialogInterface dialog, int which) {
            }

        });

        builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("notes").child(itemId);
                newReference.removeValue();
                Toast.makeText(ShowTheNote.this, "Note is deleted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ShowTheNote.this, NotesActivity.class);
                startActivity(intent);
                finish();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.getWindow().setLayout(600, 250);

        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(Color.BLUE);

        Button buttonbackground1 = alertDialog.getButton(BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLUE);


    }

    public void addReminder(){
        DatePickerDialog dialog = new DatePickerDialog(ShowTheNote.this,android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final Calendar newDate = Calendar.getInstance();
                Calendar newTime = Calendar.getInstance();
                final TimePickerDialog time = new TimePickerDialog(ShowTheNote.this,android.R.style.Theme_DeviceDefault_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                        original_date = newDate.getTime();// original  date and time saved in Date variable

                        DatabaseReference myAlarmDatabase = FirebaseDatabase.getInstance().getReference("alarms_for_notes");
                        String id = myAlarmDatabase.push().getKey();
                        AlarmForNotesDB alarm = new AlarmForNotesDB(title, note, imageURL, id, original_date );

                        myAlarmDatabase.child(id).setValue(alarm);

                        Toast.makeText(ShowTheNote.this, "Alarm has been set!", Toast.LENGTH_LONG).show();


                    }
                },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),false);
                time.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                time.show();


            }
        },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }


}
