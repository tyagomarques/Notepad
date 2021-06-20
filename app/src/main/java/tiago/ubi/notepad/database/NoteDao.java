package tiago.ubi.notepad.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tiago.ubi.notepad.model.Note;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(Note note);

    @Delete
    void removeNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Query("SELECT * FROM notes WHERE category = :categoryID")
    List<Note> getNotesByCategory(int categoryID);

    @Query("SELECT * FROM notes WHERE id = :noteID")
    Note getNoteByID(int noteID);

    @Query("DELETE FROM notes WHERE id = :noteID")
    void removeNoteByID(int noteID);

    @Query("UPDATE notes SET category = 1 WHERE category = :categoryID")
    void updateNoteByCategory(int categoryID);
}
