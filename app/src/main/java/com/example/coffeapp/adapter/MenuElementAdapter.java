package com.example.coffeapp.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.MenuElementActivity;

import java.util.List;

public class MenuElementAdapter
        extends RecyclerView.Adapter<MenuElementAdapter.MenuElementViewHolder> {

    private Context context;
    private List<MenuElement> menuElements;

    public MenuElementAdapter(Context context, List<MenuElement> menuElements) {
        this.context = context;
        this.menuElements = menuElements;
    }

    @NonNull
    @Override
    public MenuElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuElements = LayoutInflater
                .from(context)
                .inflate(R.layout.menu_element, parent,false);

        return new MenuElementAdapter.MenuElementViewHolder(menuElements);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuElementViewHolder holder, int position) {
        holder.menuElementTitle.setText(menuElements.get(position).getName());
        holder.menuElementPrice.setText(
                String.format("%.2f .руб", menuElements.get(position).getPrice().doubleValue())
        );
//        holder.menuElementPrice.setText(menuElements.get(position).getPrice().toString());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MenuElementActivity.class);

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(
                            (Activity) context,
                            new Pair<>(holder.menuElementCard, "menuElement")
                    );

            intent.putExtra("menuElementName",
                    menuElements.get(position).getName());
            intent.putExtra("menuElementPrice",
                    menuElements.get(position).getPrice().toString());
            intent.putExtra("menuElementDescription",
                    menuElements.get(position).getDescription());
            intent.putExtra("menuElementId",
                    menuElements.get(position).getId());

            context.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return menuElements.size();
    }

    static final class MenuElementViewHolder extends RecyclerView.ViewHolder {

        private TextView menuElementTitle;
        private TextView menuElementPrice;
        private View menuElementCard;

        public MenuElementViewHolder(@NonNull View itemView) {
            super(itemView);

            menuElementTitle = itemView.findViewById(R.id.menu_element_label);
            menuElementPrice = itemView.findViewById(R.id.menu_element_price_label);
            menuElementCard = itemView.findViewById(R.id.menu_element_card_view);
        }
    }
}
