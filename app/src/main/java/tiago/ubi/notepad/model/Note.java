package tiago.ubi.notepad.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "text")
    private String noteText;
    @ColumnInfo(name = "date")
    private long noteDate;
    @ColumnInfo(name = "category")
    private int category;

    public Note(String noteText, long noteDate, int category) {
        this.noteText = noteText;
        this.noteDate = noteDate;
        this.category = category;
    }

    public Note() {
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public long getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(long noteDate) {
        this.noteDate = noteDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteDate=" + noteDate +
                '}';
    }
}
