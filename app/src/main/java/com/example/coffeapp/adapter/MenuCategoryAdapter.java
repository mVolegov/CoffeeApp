package com.example.coffeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuCategory;

import java.util.List;

public class MenuCategoryAdapter
        extends RecyclerView.Adapter<MenuCategoryAdapter.MenuCategoryViewHolder> {

    private List<MenuCategory> menuCategories;
    private ItemClickListener itemClickListener;

    public MenuCategoryAdapter(List<MenuCategory> menuCategories,
                               ItemClickListener itemClickListener) {
        this.menuCategories = menuCategories;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuCategoryItems = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.menu_category_item, parent, false);

        return new MenuCategoryViewHolder(menuCategoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCategoryViewHolder holder, int position) {
        holder.menuCategoryLabel.setText(menuCategories.get(position).getTitle());

        holder.itemView.setOnClickListener(view ->
                itemClickListener.onMenuCategoryClick(menuCategories.get(position)));
    }

    @Override
    public int getItemCount() {
        return menuCategories.size();
    }

    public void setMenuCategories(List<MenuCategory> menuCategories) {
        this.menuCategories = menuCategories;

        notifyDataSetChanged();
    }

    static final class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView menuCategoryLabel;

        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            menuCategoryLabel = itemView.findViewById(R.id.menu_category_label);
        }
    }

    public interface ItemClickListener {
        void onMenuCategoryClick(MenuCategory menuCategory);
    }
}
