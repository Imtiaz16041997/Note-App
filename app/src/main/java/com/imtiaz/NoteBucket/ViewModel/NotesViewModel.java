package com.imtiaz.NoteBucket.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.imtiaz.NoteBucket.Entity.Note;
import com.imtiaz.NoteBucket.Repository.NoteRepository;

import java.util.List;


public class NotesViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;


    public LiveData<List<Note>> hightolow;
    public LiveData<List<Note>> lowtohigh;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
//        allNotes = repository.getAllNotes();
        allNotes = repository.getallNNotes();
        hightolow = repository.hightolow;
        lowtohigh = repository.lowtohigh;
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteNotes(int id){
        repository.deleteNotes(id);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

//    public LiveData<List<Note>> getAllNotes(){
//        return allNotes;
//    }

    public LiveData<List<Note>> getallNNotes(){
        return allNotes;
    }





}
