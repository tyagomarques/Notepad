package tiago.ubi.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tiago.ubi.notepad.database.CategoryDao;
import tiago.ubi.notepad.database.NoteDB;
import tiago.ubi.notepad.database.NoteDao;
import tiago.ubi.notepad.model.Category;

import static tiago.ubi.notepad.CategoryActivity.EXTRA_CATEGORY_KEY;

public class EditCategory extends AppCompatActivity {

    private Button bt_name, bt_pass, bt_remove, bt_exit;
    private EditText et_name, et_pass;
    private CategoryDao dao;
    private int category_id;
    private Category temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        dao = NoteDB.getInstance(this).categoryDao();
        getSupportActionBar().setTitle("Edit Category");
        bt_name = findViewById(R.id.save_name);
        bt_pass = findViewById(R.id.save_password);
        bt_remove = findViewById(R.id.remove);
        bt_exit = findViewById(R.id.exit);
        et_name = findViewById(R.id.new_name);
        et_pass = findViewById(R.id.new_password);
        if (getIntent().getExtras() != null) {
            category_id = getIntent().getExtras().getInt(EXTRA_CATEGORY_KEY,-1);
            if (category_id != -1) {
                temp = dao.getCategoryByID(category_id);
                et_name.setText(temp.getCategory());
            }
        }
        bt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.setCategory(et_name.getText().toString());
                dao.updateCategory(temp);
            }
        });
        bt_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.setPassword(et_pass.getText().toString());
                dao.updateCategory(temp);
            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safeRemoveCategory(temp);
                finish();
            }
        });
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void safeRemoveCategory(Category category) {
        NoteDao noteDao = NoteDB.getInstance(this).noteDao();
        dao.removeCategory(category);
        noteDao.updateNoteByCategory(category.getId());
    }
}