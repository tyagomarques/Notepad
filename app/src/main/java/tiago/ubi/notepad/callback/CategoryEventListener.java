package tiago.ubi.notepad.callback;

import tiago.ubi.notepad.model.Category;

public interface CategoryEventListener {

    void onCategoryClick(Category category);

    void onCategoryLongClick(Category category);
}
