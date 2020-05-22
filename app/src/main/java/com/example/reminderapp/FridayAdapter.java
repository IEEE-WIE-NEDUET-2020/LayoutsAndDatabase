package com.example.reminderapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FridayAdapter extends ArrayAdapter<FriDB> {
    private Activity context;
    private List<FriDB> fridayList;


    public FridayAdapter(Activity context, List<FriDB> fridayList){

        super((Context) context, R.layout.schedule_list_layout, fridayList);
        this.context= (Activity) context;
        this.fridayList = fridayList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//

        LayoutInflater inflater=  context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.schedule_list_layout, null, true);

        TextView textViewsubject = (TextView) rowView.findViewById(R.id.subjectName);
        TextView textViewcode = (TextView) rowView.findViewById(R.id.courseCode);
        TextView textViewtimings = (TextView) rowView.findViewById(R.id.times);

        FriDB fri = fridayList.get(position);
        textViewsubject.setText(fri.getSubject());
        textViewcode.setText(fri.getCode());
        textViewtimings.setText(fri.getTime());

        if (position % 2 == 0) {
            rowView.setBackgroundResource(R.color.background1);
        }

        return rowView;



    }

}
