package com.imtiaz.roomdbmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.imtiaz.roomdbmvvm.Entity.Note;
import com.imtiaz.roomdbmvvm.R;
import com.imtiaz.roomdbmvvm.ViewModel.NotesViewModel;
import com.imtiaz.roomdbmvvm.databinding.ActivityUpdateNotesBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateNotesActivity extends AppCompatActivity {
    ActivityUpdateNotesBinding binding;
    String priority = "1";
    String stitle,ssubtitle,snotes,spriority;
    private String selectedNoteColor;
    private String selectedImagePath;
    private AlertDialog dialogAddURL;
    int sid;
    NotesViewModel notesViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(binding.getRoot());

        //get the data through strings
        sid = getIntent().getIntExtra("id",0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        snotes = getIntent().getStringExtra("note");
        spriority = getIntent().getStringExtra("priority");

        binding.textViewNoteTitleUpdate.setText(stitle);
        binding.textViewNoteSubTitleUpdate.setText(ssubtitle);
        binding.textViewNotesUpdate.setText(snotes);

//        if(spriority.equals("1")) {
//            binding.imageViewHighUpdate.setImageResource(R.drawable.ic_done);
//        }
//        else if (spriority.equals("2")) {
//            binding.imageViewMediumUpdate.setImageResource(R.drawable.ic_done);
//        }
//        else if (spriority.equals("3")) {
//            binding.imageViewLowUpdate.setImageResource(R.drawable.ic_done);
//        }

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //For the Priority Part
//        binding.imageViewHighUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.imageViewHighUpdate.setImageResource(R.drawable.ic_done);
//                binding.imageViewMediumUpdate.setImageResource(0);
//                binding.imageViewLowUpdate.setImageResource(0);
//
//                priority = "1";
//            }
//        });
//
//        binding.imageViewMediumUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.imageViewMediumUpdate.setImageResource(R.drawable.ic_done);
//                binding.imageViewHighUpdate.setImageResource(0);
//                binding.imageViewLowUpdate.setImageResource(0);
//
//                priority = "2";
//            }
//        });
//
//        binding.imageViewLowUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.imageViewLowUpdate.setImageResource(R.drawable.ic_done);
//                binding.imageViewHighUpdate.setImageResource(0);
//                binding.imageViewMediumUpdate.setImageResource(0);
//
//                priority = "3";
//            }
//        });

        //For Update Part
        binding.updateNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stitle = binding.textViewNoteTitleUpdate.getText().toString();
                ssubtitle = binding.textViewNoteSubTitleUpdate.getText().toString();
                snotes = binding.textViewNotesUpdate.getText().toString();

                UpdateNotes(stitle,ssubtitle,snotes);
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

    private void UpdateNotes(String stitle, String ssubtitle, String snotes) {
        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());

        Note updateNotes = new Note();
        updateNotes.id = sid;
        updateNotes.notesTitle = stitle;
        updateNotes.notesSubtitle = ssubtitle;
        updateNotes.notes = snotes;
        updateNotes.notesDate = date;
        updateNotes.notesPriority = priority;
        updateNotes.setColor(selectedNoteColor);
        notesViewModel.update(updateNotes);
        Toast.makeText(this, "Notes Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }

        if(item.getItemId() == R.id.ic_delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this,R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_bottom_sheet,(LinearLayout) findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes,no;
            yes = view.findViewById(R.id.deleteYes);
            no = view.findViewById(R.id.deleteNo);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesViewModel.deleteNotes(sid);
                    finish();
                }
            });


            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetDialog.dismiss();
                }
            });

            sheetDialog.show();
        }
        return true;
    }

    //new miscellaneous

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


}