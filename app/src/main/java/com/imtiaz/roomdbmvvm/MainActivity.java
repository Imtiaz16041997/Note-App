package com.imtiaz.roomdbmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.imtiaz.roomdbmvvm.Activity.InsertNotesActivity;
import com.imtiaz.roomdbmvvm.Activity.UpdateNotesActivity;
import com.imtiaz.roomdbmvvm.Adapter.NotesAdapter;
import com.imtiaz.roomdbmvvm.Entity.Note;
import com.imtiaz.roomdbmvvm.ViewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;
    SearchView search_View;
    LinearLayout hideOutId,filter;

    private  boolean button1IsVisible = true;

    TextView noFilter,highToLow,lowToHigh;
    List<Note> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        setContentView(R.layout.activity_main);


        newNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecycler = findViewById(R.id.notesRecycler);
        noFilter = findViewById(R.id.noFilter);
        highToLow = findViewById(R.id.highToLow);
        lowToHigh = findViewById(R.id.lowToHigh);
        search_View = findViewById(R.id.search_View);
        hideOutId = findViewById(R.id.hideOutId);
        filter = findViewById(R.id.filter);



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button1IsVisible == true){
                    hideOutId.setVisibility(View.GONE);
                    button1IsVisible = false;
                }
                else if(button1IsVisible==false)
                {
                    hideOutId.setVisibility(View.VISIBLE);
                    button1IsVisible = true;
                }
//                hideOutId.setVisibility(View.VISIBLE);
//                return;
            }
        });


        search_View.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchFilter(newText);
                return false;
            }
        });

        noFilter.setBackgroundResource(R.drawable.filter_selected_shape);

        noFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFilterData(0);
                highToLow.setBackgroundResource(R.drawable.filter_unselected_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_unselected_shape);
                noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
            }
        });

        highToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFilterData(1);
                highToLow.setBackgroundResource(R.drawable.filter_selected_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_unselected_shape);
                noFilter.setBackgroundResource(R.drawable.filter_unselected_shape);
            }
        });

        lowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFilterData(2);
                highToLow.setBackgroundResource(R.drawable.filter_unselected_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_selected_shape);
                noFilter.setBackgroundResource(R.drawable.filter_unselected_shape);
            }
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);



        newNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
            }
        });


        notesViewModel.getallNNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                setAdapter(notes);
                filterNotesAllList = notes;
            }
        });

    }

    private void loadFilterData(int i) {
        if(i == 0){
            notesViewModel.getallNNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
        else if (i == 1){
            notesViewModel.hightolow.observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }

        else if (i == 2){
            notesViewModel.lowtohigh.observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
    }

    private void setAdapter(List<Note> notes){
        notesRecycler.setHasFixedSize(true);
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this,notes);
        notesRecycler.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_notes,menu);
//
//        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
//
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setQueryHint("Search Notes here...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                NotesFilter(s);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void NotesFilter(String newText) {
//
////        Log.e("@@@@","NotesFilter:"+newText);
//
//        ArrayList<Note> FilterNames = new ArrayList<>();
//
//        for(Note notes : this.filterNotesAllList){
//            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)){
//                FilterNames.add(notes);
//            }
//        }
//        this.adapter.searchNotes(FilterNames);
//    }

    private void SearchFilter(String newText) {
        ArrayList<Note> filtered = new ArrayList<>();

        for(Note notes : this.filterNotesAllList){
            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)){
                filtered.add(notes);
            }
        }
        this.adapter.searchNotes(filtered);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.ic_delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetStyle);

            View view = LayoutInflater.from(MainActivity.this).
                    inflate(R.layout.delete_bottom_sheet,(LinearLayout) findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes,no;
            yes = view.findViewById(R.id.deleteYes);
            no = view.findViewById(R.id.deleteNo);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesViewModel.deleteAllNotes();
                    return;

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
}