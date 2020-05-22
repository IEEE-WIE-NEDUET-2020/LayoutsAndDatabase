package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject1Test extends AppCompatActivity {

    DatabaseReference myDatabase;

    ListView listViewsub1Test;
    List<TestofSub1> test1list;
    ArrayList<String> str;
    String[] mydata = {};
    AllPurposeList adapter;

    ProgressBar mProgressbar;
    TextView mTextview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject1_test);

        mProgressbar = findViewById(R.id.progress_circular);
        mTextview = findViewById(R.id.no_notes_textview);

        mProgressbar.setVisibility(View.VISIBLE);
        mTextview.setVisibility(View.GONE);


        myDatabase = FirebaseDatabase.getInstance().getReference("tests");

        listViewsub1Test = (ListView) findViewById(R.id.listViewsub1Test);
        test1list = new ArrayList<>();
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
            bar.setTitle("Tests");
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




        listViewsub1Test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String first_letter = str.get(position);
                String temp2 = String.valueOf(first_letter.charAt(0)).toLowerCase();

                TestofSub1 test = test1list.get(position);
                showDialog(temp2, test.getSubject(), test.getTopic(), test.getDate(), test.getCategory());


            }
        });



        listViewsub1Test.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewsub1Test.setMultiChoiceModeListener(new  AbsListView.MultiChoiceModeListener() {

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

                        final int checkedCount  = test1list.size();
                        adapter.removeSelection();
                        for (int i = 0; i <  checkedCount; i++) {

                            listViewsub1Test.setItemChecked(i,   true);
                        }
                        mode.setTitle(checkedCount  + "  Selected");

                        return true;

                    case R.id.delete:
                        androidx.appcompat.app.AlertDialog.Builder  builder = new androidx.appcompat.app.AlertDialog.Builder(

                                Subject1Test.this);

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

                                for (int i = (selected.size() -1); i >= 0; i--) {

                                    if  (selected.valueAt(i)) {

                                        TestofSub1 selecteditem = adapter.getItem(selected.keyAt(i));
                                        String testId = selecteditem.getId();
                                        DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("tests").child(testId);
                                        newReference.removeValue();
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                mode.finish();
                                selected.clear();
                                adapter.notifyDataSetChanged();
                            }

                        });

                        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
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
                final int checkedCount  = listViewsub1Test.getCheckedItemCount();
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

                test1list.clear();
                str.clear();
                for(DataSnapshot testsub1snapshot : dataSnapshot.getChildren()){
                    TestofSub1 test1 = testsub1snapshot.getValue(TestofSub1.class);
                    test1list.add(test1);

                    String firstLetter = testsub1snapshot.child("subject").getValue(String.class);
                    str.add(firstLetter);
                }

                if (test1list.size()==0){
                    mProgressbar.setVisibility(View.GONE);
                    mTextview.setVisibility(View.VISIBLE);
                }



                else {

                    Collections.reverse(test1list);
                    Collections.reverse(str);


                    int size = str.size();
                    final Integer[] imgname = new Integer[size];
                    int c = 0;
                    for (String i : str) {
                        String temp = String.valueOf(i.charAt(0)).toLowerCase();
                        imgname[c] = mapping(temp);
                        c++;
                    }


                    adapter = new AllPurposeList(Subject1Test.this, test1list, imgname);
                    listViewsub1Test.setAdapter(adapter);
                    listViewsub1Test.setLongClickable(true);

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
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "open");

    }

    public void showDialog( String temp, String subjectName, String topicName, String date, String category){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.delete_dialog, null);
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
        final TextView subject = (TextView) view.findViewById(R.id.subject);
        final TextView setdate = (TextView) view.findViewById(R.id.textforDate);
        final TextView categoryName = (TextView) view.findViewById(R.id.textforCategory);
        final TextView topicname = (TextView) view.findViewById(R.id.textforTopic);

        icon.setImageResource(mapping(temp));
        subject.setText(subjectName);
        topicname.setText(topicName);
        setdate.setText(date);
        categoryName.setText(category);


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(700, 700);
//        alertDialog.getWindow().setGravity(Gravity.CENTER);

    }

    public void delTest(final String testId){
        DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("tests").child(testId);
        newReference.removeValue();
        Toast.makeText(this, "Test is deleted", Toast.LENGTH_LONG).show();
    }






//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.addition, menu);
//        return true;
//    }

}
