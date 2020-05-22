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

public class ThursdayAdapter extends ArrayAdapter<ThursDB> {
    private Activity context;
    private List<ThursDB> thursdayList;


    public ThursdayAdapter(Activity context, List<ThursDB> thursdayList){

        super((Context) context, R.layout.schedule_list_layout, thursdayList);
        this.context= (Activity) context;
        this.thursdayList = thursdayList;


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

        ThursDB thurs = thursdayList.get(position);
        textViewsubject.setText(thurs.getSubject());
        textViewcode.setText(thurs.getCode());
        textViewtimings.setText(thurs.getTime());

        if (position % 2 == 0) {
            rowView.setBackgroundResource(R.color.background1);
        }


        return rowView;



    }
}
