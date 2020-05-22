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

public class WednesdayAdapter extends ArrayAdapter<WedDB> {
    private Activity context;
    private List<WedDB> wednesdayList;


    public WednesdayAdapter(Activity context, List<WedDB> wednesdayList){

        super((Context) context, R.layout.schedule_list_layout, wednesdayList);
        this.context= (Activity) context;
        this.wednesdayList = wednesdayList;


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

        WedDB wed = wednesdayList.get(position);
        textViewsubject.setText(wed.getSubject());
        textViewcode.setText(wed.getCode());
        textViewtimings.setText(wed.getTime());

        if (position % 2 == 0) {
            rowView.setBackgroundResource(R.color.background1);
        }


        return rowView;



    }

}
