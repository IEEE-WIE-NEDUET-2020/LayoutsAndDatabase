package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNotesActiviy extends AppCompatActivity {
    Button buttonOK, buttonCancel;
    EditText editTextTitle, editTextNote;
    DatabaseReference myDatabase;
//    RelativeLayout layout;
    View layout;
    View root;
    int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes_activiy);


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

        myDatabase = FirebaseDatabase.getInstance().getReference("notes");
        editTextTitle = findViewById(R.id.textTitle);
        editTextNote  = findViewById(R.id.textNote);
        layout = findViewById(R.id.myRelativeLayout);
        root = layout.getRootView();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adding_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.OK:
                uploadText();
                Intent intent = new Intent(AddNotesActiviy.this, NotesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.colorLens:
                showColorDialog();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void uploadText(){
        String TITLE = editTextTitle.getText().toString().trim();
        String NOTE = editTextNote.getText().toString().trim();




        if (!TextUtils.isEmpty(TITLE) || !TextUtils.isEmpty(NOTE)){

            String id = myDatabase.push().getKey();

            //switch case
            NotesDB newnote = new NotesDB(TITLE, NOTE, "null" ,color, id);

            myDatabase.child(id).setValue(newnote);

            Toast.makeText(AddNotesActiviy.this, "New test added!", Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(AddNotesActiviy.this, "Please give complete information", Toast.LENGTH_LONG).show();
        }

        Intent intent  = new Intent(AddNotesActiviy.this, NotesActivity.class);
        startActivity(intent);
        finish();

    }
    public void showColorDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.colored_circles_layout, null);
        builder.setView(view)

//                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        color = ((ColorDrawable)root.getBackground()).getColor();
//                        delTest(testId);

                    }
                });

        final Button button1 = view.findViewById(R.id.color_button1);
        final Button button2 = view.findViewById(R.id.color_button2);
        final Button button3 = view.findViewById(R.id.color_button3);
        final Button button4 = view.findViewById(R.id.color_button4);
        final Button button5 = view.findViewById(R.id.color_button5);
        final Button button6 = view.findViewById(R.id.color_button6);
        final Button button7 = view.findViewById(R.id.color_button7);
        final Button button8 = view.findViewById(R.id.color_button8);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.red_circle));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.orange_circle));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.yellow_circle));
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.grass_green_circle));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.firoze_circle));
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.blue_circle));
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.dull_pink_circle));
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setBackgroundColor(getResources().getColor(R.color.purple_circle));
            }
        });




        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(650, 450);
//        alertDialog.getWindow().setGravity(Gravity.CENTER);
    }

}
