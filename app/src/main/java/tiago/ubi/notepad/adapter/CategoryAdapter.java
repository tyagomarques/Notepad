package tiago.ubi.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tiago.ubi.notepad.R;
import tiago.ubi.notepad.callback.CategoryEventListener;
import tiago.ubi.notepad.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private ArrayList<Category> categories;
    private CategoryEventListener listener;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public CategoryHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_layout, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Category category = getCategory(position);
        if (category != null) {
            holder.categoryName.setText(category.getCategory());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCategoryClick(category);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onCategoryLongClick(category);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private Category getCategory(int position) {
        return categories.get(position);
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.category_name);
        }
    }

    public void setListener(CategoryEventListener listener) {
        this.listener = listener;
    }
}
