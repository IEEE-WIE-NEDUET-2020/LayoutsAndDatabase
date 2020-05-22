package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class AddImageNotes extends AppCompatActivity {
    Uri FilePathUri;
    Button buttonOK, buttonCancel;
    EditText editTextTitle, editTextNote;
    ImageView imageview;
    DatabaseReference myDatabase;
    StorageReference storageReference;

    private StorageTask mUploadTask;

    View layout;
    View root;
    int color;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_notes);

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

        imageview = (ImageView)findViewById(R.id.mySelectedImage);
        editTextTitle = findViewById(R.id.textTitle);
        editTextNote  = findViewById(R.id.textNote);

        layout = findViewById(R.id.myRelativeLayout);
        root = layout.getRootView();



        storageReference = FirebaseStorage.getInstance().getReference("notes");
        myDatabase = FirebaseDatabase.getInstance().getReference("notes");


        FilePathUri = getIntent().getData();

        Picasso.with(this).load(FilePathUri).into(imageview);

//        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("Image");
//        imageview.setImageBitmap(bitmap);




//
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImage() {

        if (FilePathUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(FilePathUri));
            fileReference.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri my_uri = uri;
                                    String uploadId = myDatabase.push().getKey();
                                    NotesDB upload = new NotesDB(editTextTitle.getText().toString().trim(),editTextNote.getText().toString().trim(), my_uri.toString(), color, uploadId);
                                    myDatabase.child(uploadId).setValue(upload);


                                }
                            });



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddImageNotes.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
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
                uploadImage();
                Intent intent = new Intent(AddImageNotes.this, NotesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.colorLens:
                showColorDialog();
                break;


        }

        return super.onOptionsItemSelected(item);
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

