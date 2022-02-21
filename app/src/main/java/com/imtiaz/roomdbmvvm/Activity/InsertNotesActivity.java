package com.imtiaz.roomdbmvvm.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.imtiaz.roomdbmvvm.Entity.Note;

import com.imtiaz.roomdbmvvm.R;
import com.imtiaz.roomdbmvvm.ViewModel.NotesViewModel;
import com.imtiaz.roomdbmvvm.databinding.ActivityInsertNoteBinding;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    NotesViewModel notesViewModel;
    String title,subTitle, notes;
    String priority = "1";
    private String selectedNoteColor;
    private String selectedImagePath;
    private AlertDialog dialogAddURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);


//        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.greenPriority.setImageResource(R.drawable.ic_done);
//                binding.redPriority.setImageResource(0);
//                binding.yellowPriority.setImageResource(0);
//
//                Toast.makeText(InsertNotesActivity.this, "You listed priority Low", Toast.LENGTH_SHORT).show();
//
//                priority = "1";
//            }
//        });
//
//        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.yellowPriority.setImageResource(R.drawable.ic_done);
//                binding.redPriority.setImageResource(0);
//                binding.greenPriority.setImageResource(0);
//
//                Toast.makeText(InsertNotesActivity.this, "You listed priority Medium", Toast.LENGTH_SHORT).show();
//
//
//                priority = "2";
//            }
//        });
//
//        binding.redPriority.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.redPriority.setImageResource(R.drawable.ic_done);
//                binding.yellowPriority.setImageResource(0);
//                binding.greenPriority.setImageResource(0);
//
//                Toast.makeText(InsertNotesActivity.this, "You listed priority High", Toast.LENGTH_SHORT).show();
//
//
//                priority = "3";
//            }
//        });




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

        selectedNoteColor = "#333333"; //default note color
        selectedImagePath =  "" ;





        //backbutton
        binding.imageBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Date
        binding.textDateTime.setText(
                //Pattern = Wednesday,14 June 2021
                new SimpleDateFormat("EEEE, dd MMMM yyyy 'at' hh:mm a", Locale.getDefault())
                        .format(new Date())
        );

        initMiscellaneous();
        setSubtitleIndicatorColor();

    }

    private void setSubtitleIndicatorColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) binding.viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }

    private void initMiscellaneous() {

        final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);

        layoutMiscellaneous.findViewById(R.id.textMiscellaneous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        final ImageView imageColor1 = layoutMiscellaneous.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layoutMiscellaneous.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layoutMiscellaneous.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layoutMiscellaneous.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = layoutMiscellaneous.findViewById(R.id.imageColor5);

        layoutMiscellaneous.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedNoteColor = "#333333";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedNoteColor = "#FDBE3B";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedNoteColor = "#FF4842";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedNoteColor = "#3A52Fc";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedNoteColor = "#000000";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);
                setSubtitleIndicatorColor();
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
         notes1.setColor(selectedNoteColor);
         notesViewModel.insert(notes1);
        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            this.finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}