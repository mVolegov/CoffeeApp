package com.example.coffeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.model.CartItem;

public class CartElementsAdapter extends ListAdapter<CartItem, CartElementsAdapter.CartViewHolder> {

    protected CartElementsAdapter(@NonNull DiffUtil.ItemCallback<CartItem> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cartItems = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_cart_row, parent, false);

        return new CartElementsAdapter.CartViewHolder(cartItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = getItem(position);
        holder.bind(cartItem);
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView menuElementName;
        TextView menuElementAmount;
        TextView menuElementTotalPrice;
        ImageButton deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            menuElementName = itemView.findViewById(R.id.cart_menu_element_name_label);
            menuElementAmount = itemView.findViewById(R.id.cart_menu_element_amount_label);
            menuElementTotalPrice = itemView.findViewById(R.id.cart_menu_element_total_price_label);
            deleteButton = itemView.findViewById(R.id.cart_delete_button);
        }

        public void bind(CartItem cartItem) {
            menuElementName.setText(cartItem.getMenuElement().getName());
            menuElementAmount.setText(cartItem.getAmount());
        }
    }
}
