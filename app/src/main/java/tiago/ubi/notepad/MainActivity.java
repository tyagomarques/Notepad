package tiago.ubi.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tiago.ubi.notepad.adapter.NoteAdapter;
import tiago.ubi.notepad.callback.NoteEventListener;
import tiago.ubi.notepad.database.CategoryDao;
import tiago.ubi.notepad.database.NoteDB;
import tiago.ubi.notepad.database.NoteDao;
import tiago.ubi.notepad.model.Category;
import tiago.ubi.notepad.model.Note;

import static tiago.ubi.notepad.CategoryActivity.EXTRA_CATEGORY_KEY;
import static tiago.ubi.notepad.EditActivity.EXTRA_NOTE_KEY;

public class MainActivity extends AppCompatActivity implements NoteEventListener {
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NoteAdapter adapter;
    private NoteDao dao;
    private CategoryDao cdao;
    private int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle("Note List");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
        dao = NoteDB.getInstance(this).noteDao();
        cdao = NoteDB.getInstance(this).categoryDao();
        if (getIntent().getExtras() != null) {
            category_id = getIntent().getExtras().getInt(EXTRA_CATEGORY_KEY, 0);
            String temp = cdao.getCategoryByID(category_id).getCategory();
            getSupportActionBar().setTitle(temp);
        }


    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotesByCategory(category_id);
        this.notes.addAll(list);
        this.adapter = new NoteAdapter(notes,this);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
    }

    private void addNewNote() {
        Intent add = new Intent(this,EditActivity.class);
        add.putExtra(EXTRA_CATEGORY_KEY,category_id);
        startActivity(add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.category) {
            Intent category = new Intent(this,CategoryActivity.class);
            startActivity(category);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        Intent edit = new Intent(this,EditActivity.class);
        edit.putExtra(EXTRA_NOTE_KEY,note.getId());
        edit.putExtra(EXTRA_CATEGORY_KEY,category_id);
        startActivity(edit);
    }

    @Override
    public void onNoteLongClick(Note note) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.removeNote(note);
                        loadNotes();
                    }
                })
                .create()
                .show();
    }
}