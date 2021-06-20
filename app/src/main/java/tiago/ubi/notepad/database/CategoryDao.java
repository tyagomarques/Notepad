package tiago.ubi.notepad.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tiago.ubi.notepad.model.Category;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Category category);

    @Delete
    void removeCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getCategory();

    @Query("SELECT * FROM categories WHERE id = :categoryID")
    Category getCategoryByID(int categoryID);

    @Query("DELETE FROM categories WHERE id = :categoryID")
    void removeCategoryByID(int categoryID);
}
