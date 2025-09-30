package com.kingcrab.notes.editor.init.di;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kingcrab.notes.editor.data.entity.Notes;


@Database(entities = {
        Notes.class
}, views = {},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

}