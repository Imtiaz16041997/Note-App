package com.imtiaz.NoteShelf.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.imtiaz.NoteShelf.DAO.NoteDao;
import com.imtiaz.NoteShelf.DB.NoteDatabase;
import com.imtiaz.NoteShelf.Entity.Note;

import java.util.List;


/*
  NOTE: A repository allows the viewModels to get the data they need without caring about whether that data came from the database or an API
        * Base on given criteria, the repository pull from one or the other to abstract the different components so they don't need to rely on any
        knowledge of the
*/
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    public LiveData<List<Note>> hightolow;
    public LiveData<List<Note>> lowtohigh;

    public NoteRepository(Application application) {
        NoteDatabase  database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
//        allNotes = noteDao.getAllNotes();
        allNotes = noteDao.getallNNotes();
        hightolow = noteDao.highToLow();
        lowtohigh = noteDao.lowToHigh();
    }

    public void insert(Note note){
        noteDao.insert(note);
    }

    public void update(Note note){
        noteDao.update(note);
    }

    public void delete(Note note){
        noteDao.delete(note);
    }

    public void deleteNotes(int id){
        noteDao.deleteNotes(id);
    }

    public void deleteAllNotes(){
        noteDao.deleteAllNotes();
    }

//    public LiveData<List<Note>> getAllNotes(){
//        return allNotes;
//    }

    public LiveData<List<Note>> getallNNotes(){
        return allNotes;
    }





}
