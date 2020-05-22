package com.example.reminderapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonFragment extends Fragment {
    private static final String TAG = "MonFragment";

    DatabaseReference myDatabase;
    ListView monListView;
    List<MondayDB> monList;
    ScheduleAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monday_fragment, container, false);

        myDatabase = FirebaseDatabase.getInstance().getReference("Monday");

        monListView = (ListView) view.findViewById(R.id.monListView);
        monList = new ArrayList<>();

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                monList.clear();

                for(DataSnapshot monsnapshot : dataSnapshot.getChildren()){
                    MondayDB mon = monsnapshot.getValue(MondayDB.class);
                    monList.add(mon);
                }


                adapter = new ScheduleAdapter(getActivity(), monList);
                monListView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
