package com.imtiaz.roomdbmvvm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imtiaz.roomdbmvvm.Activity.UpdateNotesActivity;
import com.imtiaz.roomdbmvvm.Entity.Note;
import com.imtiaz.roomdbmvvm.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Note> notes;

    //new list for searchview

    List<Note> allnotesItem;

    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        allnotesItem = new ArrayList<>(notes);
    }

    public  void searchNotes(List<Note> filteredNotes){
        this.notes = filteredNotes;
        notifyDataSetChanged();
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);

//        switch (note.notesPriority) {
//            case "1":
//                holder.notesPriority.setBackgroundResource(R.drawable.high_priority);
//                break;
//            case "2":
//                holder.notesPriority.setBackgroundResource(R.drawable.medium_priority);
//                break;
//            case "3":
//                holder.notesPriority.setBackgroundResource(R.drawable.low_priority);
//                break;
//        }

        holder.notesTitle.setText(note.notesTitle);
        holder.notesSubtitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        GradientDrawable gradientDrawable = (GradientDrawable) holder.layoutNote.getBackground();
        if(note.getColor() != null){
            gradientDrawable.setColor(Color.parseColor(note.getColor()));
        }else{
            gradientDrawable.setColor(Color.parseColor("#333333"));
        }

        //update data send to edit activity

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(context, UpdateNotesActivity.class);

                 intent.putExtra("id",note.id);
                 intent.putExtra("title",note.notesTitle);
                 intent.putExtra("subtitle",note.notesSubtitle);
                 intent.putExtra("note",note.notes);
                 intent.putExtra("priority",note.notesPriority);
                 context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}


 class NotesViewHolder extends RecyclerView.ViewHolder {
    View notesPriority;
    TextView notesTitle,notesSubtitle,notesDate;
    LinearLayout layoutNote;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
//        notesPriority = itemView.findViewById(R.id.notesPriority);
        notesTitle = itemView.findViewById(R.id.notesTitle);
        notesSubtitle = itemView.findViewById(R.id.notesSubtitle);
        notesDate = itemView.findViewById(R.id.notesDate);
        layoutNote = itemView.findViewById(R.id.layoutNote);
    }
}