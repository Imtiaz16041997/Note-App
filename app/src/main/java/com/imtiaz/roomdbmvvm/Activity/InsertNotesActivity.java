package com.imtiaz.roomdbmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.imtiaz.roomdbmvvm.Entity.Note;

import com.imtiaz.roomdbmvvm.R;
import com.imtiaz.roomdbmvvm.ViewModel.NotesViewModel;
import com.imtiaz.roomdbmvvm.databinding.ActivityInsertNoteBinding;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    NotesViewModel notesViewModel;
    String title,subTitle, notes;
    String priority = "1";
    private String selectedNoteColor;
    private String selectedNoteColorPriority;
    private String selectedImagePath;
    private AlertDialog dialogAddURL;
    private  static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private  static final int REQUEST_CODE_SELECT_IMAGE = 2;
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

        priorityFunction();

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
        selectedNoteColorPriority = "#FF5151"; //default note color
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

    private void priorityFunction() {

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

        final ImageView redPriority = layoutMiscellaneous.findViewById(R.id.redPriority);
        final ImageView yellowPriority = layoutMiscellaneous.findViewById(R.id.yellowPriority);
        final ImageView greenPriority = layoutMiscellaneous.findViewById(R.id.greenPriority);

        layoutMiscellaneous.findViewById(R.id.viewColorRed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColorPriority = "#FF5151";
                redPriority.setImageResource(R.drawable.ic_done);
                yellowPriority.setImageResource(0);
                greenPriority.setImageResource(0);



                Toast.makeText(InsertNotesActivity.this, "You listed priority High", Toast.LENGTH_SHORT).show();

                priority = "1";
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColorYellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColorPriority = "#FFFF00";
                redPriority.setImageResource(0);
                yellowPriority.setImageResource(R.drawable.ic_done);
                greenPriority.setImageResource(0);



                Toast.makeText(InsertNotesActivity.this, "You listed priority Medium", Toast.LENGTH_SHORT).show();


                priority = "2";
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColorGreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColorPriority = "#11D99B";
                redPriority.setImageResource(0);
                yellowPriority.setImageResource(0);
                greenPriority.setImageResource(R.drawable.ic_done);



                Toast.makeText(InsertNotesActivity.this, "You listed priority Low", Toast.LENGTH_SHORT).show();


                priority = "3";
            }
        });
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

        //image

        layoutMiscellaneous.findViewById(R.id.layoutAddImage).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            InsertNotesActivity.this,
                            new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );

                }else{
                    selectImage();
                }

            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{

                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imageNote.setImageBitmap(bitmap);
                        binding.imageNote.setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromUri(selectedImageUri);

                    }catch(Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri){
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null,null,null,null);
        if(cursor == null){
            filePath = contentUri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
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
        notes1.setImagePath(selectedImagePath);
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