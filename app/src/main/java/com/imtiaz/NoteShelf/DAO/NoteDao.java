package com.imtiaz.NoteShelf.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.imtiaz.NoteShelf.Entity.Note;

import java.util.List;

//NOTE: It's typically good practice to make one DAO per entity. Depends on the case though.
//Room uses this to auto-generates the code files.

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table WHERE id=:id")
    void deleteNotes(int id);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY notes_priority DESC")
    LiveData<List<Note>> highToLow();

    @Query("SELECT * FROM note_table ORDER BY notes_priority ASC")
    LiveData<List<Note>> lowToHigh();

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getallNNotes();


}
