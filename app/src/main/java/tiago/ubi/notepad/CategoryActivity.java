package tiago.ubi.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tiago.ubi.notepad.adapter.CategoryAdapter;
import tiago.ubi.notepad.callback.CategoryEventListener;
import tiago.ubi.notepad.database.CategoryDao;
import tiago.ubi.notepad.database.NoteDB;
import tiago.ubi.notepad.database.NoteDao;
import tiago.ubi.notepad.dialog.GetCategoryDialog;
import tiago.ubi.notepad.model.Category;

import static tiago.ubi.notepad.utils.NoteUtils.hashPassword;
import static tiago.ubi.notepad.utils.NoteUtils.verifyPassword;

public class CategoryActivity extends AppCompatActivity implements GetCategoryDialog.GetCategoryDialogListener, CategoryEventListener {

    private RecyclerView recyclerView;
    private ArrayList<Category> categories;
    private CategoryAdapter adapter;
    private CategoryDao dao;
    private String dialogController;
    private boolean verify;
    private static final String TAG = "CategoryActivity";
    public static final String EXTRA_CATEGORY_KEY = "category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notepad");
        recyclerView = findViewById(R.id.category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab_c);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCategory();
            }
        });

        dao = NoteDB.getInstance(this).categoryDao();
    }

    private void loadCategory() {
        this.categories = new ArrayList<>();
        List<Category> list = dao.getCategory();
        if (list.size() == 0) {
            Category trash_can = new Category("Trash Can", "");
            dao.addCategory(trash_can);
            list.add(trash_can);
        }
        this.categories.addAll(list);
        /*for (int i = 0; i < 12; i++) {
            categories.add(new Category("THIS IS A CATEGORY",""));
        }*/
        this.adapter = new CategoryAdapter(this, categories);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void addNewCategory() {
        if (categories != null) {
            categories.add(new Category("New category", ""));
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void createCategory() {
        GetCategoryDialog dialog = new GetCategoryDialog();
        dialogController = "ADD";
        dialog.show(getSupportFragmentManager(), "Create Dialog");
    }

    private void updateList() {
        ArrayList<Category> temp = new ArrayList<>();
        List<Category> list = dao.getCategory();
        temp.addAll(list);
        this.categories = temp;
        adapter.notifyDataSetChanged();
    }

    private void safeRemoveCategory(Category category) {
        NoteDao noteDao = NoteDB.getInstance(this).noteDao();
        dao.removeCategory(category);
        noteDao.updateNoteByCategory(category.getId());
    }

    private void getPassword(Category category, String key) {
        verify = false;
        final EditText password_input = new EditText(this);
        password_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        password_input.setTextSize(20);
        new AlertDialog.Builder(this)
                .setTitle("Password")
                .setView(password_input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //verify = category.getPassword().equals(password_input.getText().toString());
                        verify = verifyPassword(password_input.getText().toString(),category.getPassword());
                        if (verify) {
                            if (key == "note") {
                                Intent note = new Intent(CategoryActivity.this, MainActivity.class);
                                note.putExtra(EXTRA_CATEGORY_KEY, category.getId());
                                startActivity(note);
                            }
                            else if (key == "edit"){
                                Intent note = new Intent(CategoryActivity.this, EditCategory.class);
                                note.putExtra(EXTRA_CATEGORY_KEY, category.getId());
                                startActivity(note);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategory();
    }

    @Override
    public void getDialogInput(String name, String password) {
        Category temp = new Category(name, hashPassword(password));
        if (dialogController == "ADD") {
            if (!temp.getCategory().isEmpty()) {
                dao.addCategory(temp);
            }
        }
        loadCategory();
    }

    @Override
    public void onCategoryClick(Category category) {
        if (!category.getPassword().isEmpty()) {
            getPassword(category, "note");
        } else {
            Intent note = new Intent(this, MainActivity.class);
            note.putExtra(EXTRA_CATEGORY_KEY, category.getId());
            startActivity(note);
        }
    }

    @Override
    public void onCategoryLongClick(Category category) {
        if (category.getId() != 1) {
            if (!category.getPassword().isEmpty()) {
                getPassword(category, "edit");
            } else {
                Intent note = new Intent(this, EditCategory.class);
                note.putExtra(EXTRA_CATEGORY_KEY, category.getId());
                startActivity(note);
            }
            /*new AlertDialog.Builder(this)
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
                            safeRemoveCategory(category);
                            loadCategory();
                        }
                    })
                    .create()
                    .show();
        }
          */
        }
    }

}