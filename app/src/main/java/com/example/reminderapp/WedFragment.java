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

public class WedFragment extends Fragment {
    private static final String TAG = "WedFragment";

    DatabaseReference myDatabase;
    ListView wedListView;
    List<WedDB> wedList;
    WednesdayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wednesday_fragment, container, false);

        myDatabase = FirebaseDatabase.getInstance().getReference("Wednesday");

        wedListView = (ListView) view.findViewById(R.id.wedListView);
        wedList = new ArrayList<>();

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                wedList.clear();

                for(DataSnapshot wedsnapshot : dataSnapshot.getChildren()){
                    WedDB wed = wedsnapshot.getValue(WedDB.class);
                    wedList.add(wed);
                }


                adapter = new WednesdayAdapter(getActivity(), wedList);
                wedListView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
