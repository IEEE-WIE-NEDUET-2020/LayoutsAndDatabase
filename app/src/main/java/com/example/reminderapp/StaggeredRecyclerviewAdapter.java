package com.example.reminderapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SelectableHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StaggeredRecyclerviewAdapter extends RecyclerView.Adapter<StaggeredRecyclerviewAdapter.ViewHolder> {

    private Context mContext;
    private List<NotesDB> note_list;
    private onNoteListener mOnNoteListener;



    public StaggeredRecyclerviewAdapter(Context mContext, List<NotesDB> note_list, onNoteListener onNoteListener) {
        this.mContext = mContext;
        this.note_list = note_list;
        this.mOnNoteListener = onNoteListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.imageview_layout, parent, false);
//        ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotesDB note = note_list.get(position);
        String mtitle = note.getTitle();
        String mnote = note.getNote();
        String mURL = note.getImageURL();
        int mycolor = note.getColor();




        if (note.getImageURL().equals("null")) {
            holder.image.setVisibility(View.GONE);
            Log.d("TAG", "url is empty");
        } else {
            Log.d("TAG", "url is not empty");
            Glide.with(mContext)
                    .load(note.getImageURL())
                    .centerCrop()
                    .into(holder.image);
        }


//
        if (!TextUtils.isEmpty(mtitle)) {
            holder.Title.setText(mtitle);
            holder.Title.setVisibility(View.VISIBLE);
            Log.d("invisible", mtitle);
        } else {
            holder.Title.setVisibility(View.GONE);
        }
//
        if (!TextUtils.isEmpty(mnote)) {
            holder.Note.setText(mnote);
            holder.Note.setVisibility(View.VISIBLE);
        } else {
            holder.Note.setVisibility(View.GONE);
        }
        if (mycolor != 0){
            holder.itemView.setBackgroundColor(mycolor);

        }
    }

    @Override
    public int getItemCount() {
        return note_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image;
        public TextView Title;
        public TextView Note;
        onNoteListener onNoteListener;



        public ViewHolder(@NonNull View itemView, onNoteListener onNoteListener) {
            super(itemView);


            this.image = itemView.findViewById(R.id.myimage);
            this.Title = itemView.findViewById(R.id.textOne);
            this.Note = itemView.findViewById(R.id.textTwo);
            this.onNoteListener = onNoteListener;


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClicked(getAdapterPosition());

        }
    }
    public interface onNoteListener{
        public void onNoteClicked(int position);
    }


}

