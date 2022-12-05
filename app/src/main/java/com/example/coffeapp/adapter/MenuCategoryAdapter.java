package com.example.coffeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.MainActivity;
import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuCategory;

import java.util.List;

public class MenuCategoryAdapter
        extends RecyclerView.Adapter<MenuCategoryAdapter.MenuCategoryViewHolder> {

    private final Context context;
    private final List<MenuCategory> menuCategories;

    public MenuCategoryAdapter(Context context, List<MenuCategory> menuCategories) {
        this.context = context;
        this.menuCategories = menuCategories;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuCategoryItems = LayoutInflater
                .from(context)
                .inflate(R.layout.menu_category_item, parent, false);

        return new MenuCategoryViewHolder(menuCategoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCategoryViewHolder holder, int position) {
        holder.menuCategoryLabel.setText(menuCategories.get(position).getTitle());

//        holder.itemView.setOnClickListener(view ->
//                MainActivity.showMenuElementsByCategory(menuCategories.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return menuCategories.size();
    }

    static final class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView menuCategoryLabel;

        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            menuCategoryLabel = itemView.findViewById(R.id.menu_category_label);
        }
    }
}
