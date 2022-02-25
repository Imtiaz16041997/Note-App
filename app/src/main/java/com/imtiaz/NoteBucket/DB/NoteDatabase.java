package com.imtiaz.NoteBucket.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.imtiaz.NoteBucket.DAO.NoteDao;
import com.imtiaz.NoteBucket.Entity.Note;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance = null;

    public abstract NoteDao noteDao();

    //Using singleton, synchronized ->  only one thread  access  at a time
    public static  synchronized  NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
            ,NoteDatabase.class,"note_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build();
        }
        return instance;
    }


}
