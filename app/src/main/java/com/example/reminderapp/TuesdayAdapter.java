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

public class TuesdayAdapter extends ArrayAdapter<TuesDB> {
    private Activity context;
    private List<TuesDB> tuesdayList;


    public TuesdayAdapter(Activity context, List<TuesDB> tuesdayList){

        super((Context) context, R.layout.schedule_list_layout, tuesdayList);
        this.context= (Activity) context;
        this.tuesdayList = tuesdayList;


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

        TuesDB tues = tuesdayList.get(position);
        textViewsubject.setText(tues.getSubject());
        textViewcode.setText(tues.getCode());
        textViewtimings.setText(tues.getTime());

        if (position % 2 == 0) {
            rowView.setBackgroundResource(R.color.background1);
        }


        return rowView;



    }

}
