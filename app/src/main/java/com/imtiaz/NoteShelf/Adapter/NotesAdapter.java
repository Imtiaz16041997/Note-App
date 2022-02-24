package com.imtiaz.NoteShelf.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.imtiaz.NoteShelf.Activity.UpdateNotesActivity;
import com.imtiaz.NoteShelf.Entity.Note;
import com.imtiaz.NoteShelf.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.background_note_color_high);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.background_note_color_medium);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.background_note_color_3);
                break;
        }

        holder.notesTitle.setText(note.notesTitle);
        holder.notesSubtitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);
        holder.randomColor.setCardBackgroundColor(holder.itemView.getResources().getColor(getRandomColor(),null));

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

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.Silver);
        colorCode.add(R.color.colorAccent);
        colorCode.add(R.color.nron2);
        colorCode.add(R.color.bluegrey);
        colorCode.add(R.color.silver2);
        colorCode.add(R.color.dark_late_gray);
        colorCode.add(R.color.dark_late_gray2);
        colorCode.add(R.color.light_blue);
        colorCode.add(R.color.cadet_blue);
        colorCode.add(R.color.Gray);
        colorCode.add(R.color.black);

        Random random  = new Random();
        int color = random.nextInt(colorCode.size());
        return colorCode.get(color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}


 class NotesViewHolder extends RecyclerView.ViewHolder {
    View notesPriority;
    TextView notesTitle,notesSubtitle,notesDate;
    CardView randomColor;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notesPriority = itemView.findViewById(R.id.notesPriority);
        notesTitle = itemView.findViewById(R.id.notesTitle);
        notesSubtitle = itemView.findViewById(R.id.notesSubtitle);
        notesDate = itemView.findViewById(R.id.notesDate);
        randomColor = itemView.findViewById(R.id.randomColor);
    }
}