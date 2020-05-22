package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements StaggeredRecyclerviewAdapter.onNoteListener {

    List<NotesDB> note_list;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    StaggeredRecyclerviewAdapter adapter;

    Uri FilePathUri;

    StorageReference storageReference;
    DatabaseReference myDatabase;
    int Image_Request_Code = 7;


    ProgressBar mProgressbar;
    TextView no_notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        note_list = new ArrayList<>();

        mProgressbar = (ProgressBar) findViewById(R.id.progress_circular);
        mProgressbar.setVisibility(View.VISIBLE);

        no_notes = (TextView) findViewById(R.id.no_notes_textview);
        no_notes.setVisibility(View.GONE);




        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab2.setImageResource(R.drawable.ic_add_photo);
        fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.new1)));
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
//                showDialog();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setImageResource(R.drawable.ic_add);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.new1)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, AddNotesActiviy.class);
                startActivity(intent);
            }
        });


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

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                note_list.clear();

                for (DataSnapshot note_snapshot : dataSnapshot.getChildren()) {
                    NotesDB note = note_snapshot.getValue(NotesDB.class);
                    note_list.add(note);
                }

                if (note_list.size() == 0){
                    mProgressbar.setVisibility(View.GONE);
                    no_notes.setVisibility(View.VISIBLE);
                }

                else {

                    recyclerView = findViewById(R.id.recyclerView);


                    staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
//                recyclerView.setHasFixedSize(true);

                    StaggeredRecyclerviewAdapter adapter = new StaggeredRecyclerviewAdapter(NotesActivity.this, note_list, NotesActivity.this);
                    recyclerView.setAdapter(adapter);

                    mProgressbar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
            Intent intent = new Intent(NotesActivity.this, AddImageNotes.class);
            intent.setData(FilePathUri );
            startActivity(intent);
        }

//
            }


    @Override
    public void onNoteClicked(int position) {
        NotesDB notesDB = note_list.get(position);
        Intent intent = new Intent(NotesActivity.this, ShowTheNote.class);
        intent.putExtra("Title", notesDB.getTitle());
        intent.putExtra("Note", notesDB.getNote());
        intent.putExtra("ImageUrl", notesDB.getImageURL().toString());
        intent.putExtra("BackgroundColor", notesDB.getColor());
        intent.putExtra("id", notesDB.getId());

        startActivity(intent);

    }


}




