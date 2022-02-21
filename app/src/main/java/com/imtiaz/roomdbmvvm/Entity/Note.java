package com.imtiaz.roomdbmvvm.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/*  By default, Room uses the class name as the database table name.
    If you want the table to have a different name,
    set the tableName property of the @Entity annotation.*/


@Entity(tableName = "note_table")
public class Note {
/*
    Room uses the field names as column names in the database by default.
    If you want a column to have a different name,
    add the @ColumnInfo annotation to the field and set the name property.
*/
    @PrimaryKey(autoGenerate = true)
    public  int id;

    @ColumnInfo(name = "notes_title")
    public String notesTitle;

    @ColumnInfo(name = "notes_subtitle")
    public String notesSubtitle;

    @ColumnInfo(name = "notes_date")
    public String notesDate;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "notes_priority")
    public String notesPriority;

    public int getId() {
        return id;
    }

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "web_link")
    public String webLink;


    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    //    public Note(String title, String description, int priority) {
//        this.title = title;
//        this.description = description;
//        this.priority = priority;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public int getPriority() {
//        return priority;
//    }
}
