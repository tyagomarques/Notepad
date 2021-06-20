package tiago.ubi.notepad.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tiago.ubi.notepad.model.Category;
import tiago.ubi.notepad.model.Note;

@Database(entities = {Note.class, Category.class}, version = 1)
public abstract class NoteDB extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract CategoryDao categoryDao();

    public static final String DATABASE_NAME = "noteDB";
    private static NoteDB instance;

    public static NoteDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, NoteDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
