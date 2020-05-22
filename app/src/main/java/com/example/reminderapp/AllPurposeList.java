package com.example.reminderapp;

import android.app.Activity;
import android.media.Image;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class AllPurposeList extends ArrayAdapter<TestofSub1> {
    private Activity context;
    private List<TestofSub1> testofsub1;
    private  SparseBooleanArray mSelectedItemsIds;
    private Integer[] imageId;  //new item added

    public AllPurposeList(Activity context, List<TestofSub1> testofsub1, Integer[] imageId){

        super(context, R.layout.list_layout, testofsub1);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context=context;
        this.testofsub1 = testofsub1;
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

        TestofSub1 test = testofsub1.get(position);
        textViewtopic.setText(test.getSubject());
        textViewmessage.setText(test.getTopic());

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
