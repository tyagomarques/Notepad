package tiago.ubi.notepad.callback;

import tiago.ubi.notepad.model.Note;

public interface NoteEventListener {

    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
