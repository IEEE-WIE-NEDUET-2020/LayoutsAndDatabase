package com.example.reminderapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class ThursFragment extends Fragment {
    private static final String TAG = "ThursFragment";

    DatabaseReference myDatabase;
    ListView thursListView;
    List<ThursDB> thursList;
    ThursdayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thursday_fragment, container, false);

        myDatabase = FirebaseDatabase.getInstance().getReference("Thursday");

        thursListView = (ListView) view.findViewById(R.id.thursListView);
        thursList = new ArrayList<>();

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                thursList.clear();

                for(DataSnapshot thurssnapshot : dataSnapshot.getChildren()){
                    ThursDB thurs = thurssnapshot.getValue(ThursDB.class);
                    thursList.add(thurs);
                }


                adapter = new ThursdayAdapter(getActivity(), thursList);
                thursListView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
