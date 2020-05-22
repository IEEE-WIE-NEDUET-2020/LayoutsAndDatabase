package com.example.reminderapp;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AssignmentAdapter extends ArrayAdapter<Assignment> {
    private Activity context;
    private List<Assignment> assign_list;
    private SparseBooleanArray mSelectedItemsIds;
    private Integer[] imageId;  //new item added


    public AssignmentAdapter(Activity context, List<Assignment> assign_list, Integer[] imageId){
        super(context, R.layout.list_layout, assign_list);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context=context;
        this.assign_list = assign_list;
        this.imageId = imageId;  //new item added


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//

        LayoutInflater inflater=  context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewtopic = (TextView) rowView.findViewById(R.id.textViewtopic);
        TextView textViewmessage = (TextView) rowView.findViewById(R.id.textViewmessage);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        Assignment assign = assign_list.get(position);
        textViewtopic.setText(assign.getSubject());
        textViewmessage.setText(assign.getTopic());

        imageView.setImageResource(imageId[position]);
        return rowView;



    }

    public void removeSelection() {
        mSelectedItemsIds = new  SparseBooleanArray();

        notifyDataSetChanged();
    }

    public void  toggleSelection(int position) {

        selectView(position, !mSelectedItemsIds.get(position));

    }

    public void selectView(int position, boolean value) {

        if (value)

            mSelectedItemsIds.put(position,  value);

        else

            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();

    }

    public  SparseBooleanArray getSelectedIds() {

        return mSelectedItemsIds;

    }
}
