package com.example.reminderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<MondayDB> {
    private Activity context;
    private List<MondayDB> mondayList;


    public ScheduleAdapter(Activity context, List<MondayDB> mondayList){

        super((Context) context, R.layout.schedule_list_layout, mondayList);
        this.context= (Activity) context;
        this.mondayList = mondayList;


    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//

        LayoutInflater inflater=  context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.schedule_list_layout, null, true);

        TextView textViewsubject = (TextView) rowView.findViewById(R.id.subjectName);
        TextView textViewcode = (TextView) rowView.findViewById(R.id.courseCode);
        TextView textViewtimings = (TextView) rowView.findViewById(R.id.times);

        MondayDB mon = mondayList.get(position);
        textViewsubject.setText(mon.getSubject());
        textViewcode.setText(mon.getCode());
        textViewtimings.setText(mon.getTime());

        if (position % 2 == 0) {
            rowView.setBackgroundResource(R.color.background1);
        }
//        } else {
//            rowView.setBackgroundColor(Color.CYAN);
//        }


        return rowView;



    }

}
