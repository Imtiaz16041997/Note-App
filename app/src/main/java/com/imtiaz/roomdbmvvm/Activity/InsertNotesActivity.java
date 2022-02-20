package com.imtiaz.roomdbmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.imtiaz.roomdbmvvm.Entity.Note;

import com.imtiaz.roomdbmvvm.R;
import com.imtiaz.roomdbmvvm.ViewModel.NotesViewModel;
import com.imtiaz.roomdbmvvm.databinding.ActivityInsertNoteBinding;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    NotesViewModel notesViewModel;
    String title,subTitle, notes;
    String priority = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriority.setImageResource(R.drawable.ic_done);
                binding.redPriority.setImageResource(0);
                binding.yellowPriority.setImageResource(0);

                Toast.makeText(InsertNotesActivity.this, "You listed priority Low", Toast.LENGTH_SHORT).show();

                priority = "1";
            }
        });

        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.yellowPriority.setImageResource(R.drawable.ic_done);
                binding.redPriority.setImageResource(0);
                binding.greenPriority.setImageResource(0);

                Toast.makeText(InsertNotesActivity.this, "You listed priority Medium", Toast.LENGTH_SHORT).show();


                priority = "2";
            }
        });

        binding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.redPriority.setImageResource(R.drawable.ic_done);
                binding.yellowPriority.setImageResource(0);
                binding.greenPriority.setImageResource(0);

                Toast.makeText(InsertNotesActivity.this, "You listed priority High", Toast.LENGTH_SHORT).show();


                priority = "3";
            }
        });




        binding.doneNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = binding.textViewNoteTitle.getText().toString().trim();
                subTitle = binding.textViewNoteSubTitle.getText().toString().trim();
                notes = binding.textViewNotes.getText().toString().trim();

                if(title.isEmpty() || subTitle.isEmpty() || notes.isEmpty()){
                    Toast.makeText(InsertNotesActivity.this, "Note can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                CreateNotes(title,subTitle,notes);


            }
        });

    }

    private void CreateNotes(String title, String subTitle, String notes) {

        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());

         Note notes1 = new Note();
         notes1.notesTitle = title;
         notes1.notesSubtitle = subTitle;
         notes1.notes = notes;
         notes1.notesDate = date;
         notes1.notesPriority = priority;
         notesViewModel.insert(notes1);
        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}