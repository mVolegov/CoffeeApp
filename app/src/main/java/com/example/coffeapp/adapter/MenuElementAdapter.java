package com.example.coffeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuElement;

import java.util.List;

public class MenuElementAdapter
        extends RecyclerView.Adapter<MenuElementAdapter.MenuElementViewHolder> {

    private List<MenuElement> menuElements;
    private ItemClickListener itemClickListener;

    public MenuElementAdapter(List<MenuElement> menuElements,
                              ItemClickListener clickListener) {
        this.menuElements = menuElements;
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public MenuElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuElements = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.menu_element, parent,false);

        return new MenuElementAdapter.MenuElementViewHolder(menuElements);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuElementViewHolder holder, int position) {
        holder.bind(menuElements.get(position));

        holder.itemView.setOnClickListener(view ->
                itemClickListener.onMenuElementClick(menuElements.get(position)));
    }

    @Override
    public int getItemCount() {
        return menuElements.size();
    }

    public void setMenuElements(List<MenuElement> menuElements) {
        this.menuElements = menuElements;
        notifyDataSetChanged();
    }

    final class MenuElementViewHolder extends RecyclerView.ViewHolder {

        private TextView menuElementTitle;
        private TextView menuElementPrice;
        private View menuElementCard;

        public MenuElementViewHolder(@NonNull View itemView) {
            super(itemView);

            menuElementTitle = itemView.findViewById(R.id.menu_element_label);
            menuElementPrice = itemView.findViewById(R.id.menu_element_price_label);
            menuElementCard = itemView.findViewById(R.id.menu_element_card_view);
        }

        public void bind(final MenuElement data) {
            menuElementTitle.setText(data.getName());
            menuElementPrice.setText(String.format("%.2f .руб", data.getPrice().doubleValue()));
        }
    }

    public interface ItemClickListener {
        void onMenuElementClick(MenuElement menuElement);
    }
}
