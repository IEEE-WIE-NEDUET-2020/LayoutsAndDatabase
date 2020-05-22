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

public class FriFragment extends Fragment {
    private static final String TAG = "FriFragment";

    DatabaseReference myDatabase;
    ListView friListView;
    List<FriDB> friList;
    FridayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friday_fragment, container, false);

        myDatabase = FirebaseDatabase.getInstance().getReference("Friday");

        friListView = (ListView) view.findViewById(R.id.friListView);
        friList = new ArrayList<>();

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                friList.clear();

                for(DataSnapshot frisnapshot : dataSnapshot.getChildren()){
                    FriDB fri = frisnapshot.getValue(FriDB.class);
                    friList.add(fri);
                }


                adapter = new FridayAdapter(getActivity(), friList);
                friListView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
