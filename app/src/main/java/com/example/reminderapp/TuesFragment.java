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

public class TuesFragment extends Fragment {
    private static final String TAG = "TuesFragment";

    DatabaseReference myDatabase;
    ListView tuesListView;
    List<TuesDB> tuesList;
    TuesdayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuesday_fragment, container, false);

        myDatabase = FirebaseDatabase.getInstance().getReference("Tuesday");

        tuesListView = (ListView) view.findViewById(R.id.tuesListView);
        tuesList = new ArrayList<>();

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tuesList.clear();

                for(DataSnapshot tuessnapshot : dataSnapshot.getChildren()){
                    TuesDB tues = tuessnapshot.getValue(TuesDB.class);
                    tuesList.add(tues);
                }


                adapter = new TuesdayAdapter(getActivity(), tuesList);
                tuesListView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
