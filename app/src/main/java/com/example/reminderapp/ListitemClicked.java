package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListitemClicked extends AppCompatActivity {

    DatabaseReference myDatabase;
    int pos;

    List<TestofSub1> test1list;
    ArrayList<String> str;

    ImageView icon;
    TextView subject;
    TextView topicname;
    TextView setdate;
    TextView categoryName;

    TextView topic;
    TextView date;
    TextView category;

    RelativeLayout layout;

    View view;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem_clicked);

//        Toolbar toolbar= findViewById(R.id.innertoolbar);
////        TextView toolbar_text = findViewById(R.id.toolbar_text);
////        toolbar_text.setText("Tests");
//        setSupportActionBar(toolbar);


        myDatabase = FirebaseDatabase.getInstance().getReference("tests");
        test1list = new ArrayList<>();
        str = new ArrayList<String>();

        view = findViewById(android.R.id.content);

        Intent intent = getIntent();
        pos = intent.getExtras().getInt("Position");




        icon = (ImageView) findViewById(R.id.image);
        subject = (TextView) findViewById(R.id.subject);
        topic = (TextView) findViewById(R.id.textTopic);
        topicname = (TextView) findViewById(R.id.textforTopic);
        date = (TextView) findViewById(R.id.textDate);
        setdate = (TextView) findViewById(R.id.textforDate);
        category = (TextView) findViewById(R.id.textCategory);
        categoryName = (TextView) findViewById(R.id.textforCategory);


//        ActionBar bar = getSupportActionBar();
//
//        if(bar!=null){
//            bar.setTitle("Test");
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



        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                test1list.clear();
                str.clear();
                for (DataSnapshot testsub1snapshot : dataSnapshot.getChildren()) {
                    TestofSub1 test1 = testsub1snapshot.getValue(TestofSub1.class);
                    test1list.add(test1);

                    String firstLetter = testsub1snapshot.child("subject").getValue(String.class);
                    str.add(firstLetter);
                }

                //importnt work
                String first_letter = str.get(pos);
                String temp2 = String.valueOf(first_letter.charAt(0)).toLowerCase();


                icon.setImageResource(mapping(temp2));
                TestofSub1 test = test1list.get(pos);
                subject.setText(test.getSubject());
                topicname.setText(test.getTopic());
                setdate.setText(test.getDate());
                categoryName.setText(test.getCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private Integer mapping(String mychar) {
        Integer[] imgname = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z };
        String myabc = "abcdefghijklmnopqrstuvwxyz";
        int position = myabc.indexOf(mychar);
        return imgname[position];
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        myDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                test1list.clear();
//                str.clear();
//                for (DataSnapshot testsub1snapshot : dataSnapshot.getChildren()) {
//                    TestofSub1 test1 = testsub1snapshot.getValue(TestofSub1.class);
//                    test1list.add(test1);
//
//                    String firstLetter = testsub1snapshot.child("subject").getValue(String.class);
//                    str.add(firstLetter);
//
//
//                }
//
//                //importnt work
//                String first_letter = str.get(pos);
//                String temp2 = String.valueOf(first_letter.charAt(0)).toLowerCase();
//
//
//                icon.setImageResource(mapping(temp2));
//                TestofSub1 test = test1list.get(pos);
//                subject.setText(test.getSubject());
//                topicname.setText(test.getTopic());
//                setdate.setText(test.getDate());
//                categoryName.setText(test.getCategory());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TestofSub1 test1 = test1list.get(pos);
//        showDeleteDialog(test1.getId());

//        snackbar = Snackbar.make(view, "Are you sure you want to delete?", Snackbar.LENGTH_LONG)
//                .setAction("NO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        snackbar.dismiss();
//                    }
//                });
//
//                snackbar.show();
        return super.onOptionsItemSelected(item);
    }

//    public void showDeleteDialog(final String testId){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//        final View view = inflater.inflate(R.layout.delete_dialog, null);
//
//        builder.setView(view)
//
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(ListitemClicked.this, Subject1Test.class);
//                        startActivity(intent);
//                        finish();
//
//                        DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("tests").child(testId);
//                        newReference.removeValue();
////                        notifydatasetchanged();
//
////
////                        delTest(testId);
//
//                    }
//                });
//
//        TextView delete = (TextView) view.findViewById(R.id.textViewDel);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//    }
//
//    public void delTest(final String testId){
//        DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("tests").child(testId);
//        Intent intent = new Intent(ListitemClicked.this, Subject1Test.class);
//        startActivity(intent);
//        newReference.removeValue();
//
////        Toast.makeText(this, "Test is deleted", Toast.LENGTH_LONG).show();
//
//    }
}




