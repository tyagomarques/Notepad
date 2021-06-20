package tiago.ubi.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import tiago.ubi.notepad.database.NoteDB;
import tiago.ubi.notepad.database.NoteDao;
import tiago.ubi.notepad.model.Note;

import static tiago.ubi.notepad.CategoryActivity.EXTRA_CATEGORY_KEY;

public class EditActivity extends AppCompatActivity {
    private EditText input;
    private NoteDao dao;
    private Note temp;
    public static final String EXTRA_NOTE_KEY = "note_id";
    private int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        input = findViewById(R.id.input);
        dao = NoteDB.getInstance(this).noteDao();
        getSupportActionBar().setTitle("Edit Note");
        if (getIntent().getExtras() != null) {
            int id = getIntent().getExtras().getInt(EXTRA_NOTE_KEY, -1);
            if (id != -1) {
                temp = dao.getNoteByID(id);
                input.setText(temp.getNoteText());
            }
            category_id = getIntent().getExtras().getInt(EXTRA_CATEGORY_KEY,-1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note)
            saveNote();
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String text = input.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();
            if (temp == null) {
                temp = new Note(text, date, category_id);
                dao.addNote(temp);
            } else {
                temp.setNoteDate(date);
                temp.setNoteText(text);
                dao.updateNote(temp);
            }
            finish();
        }
    }
}