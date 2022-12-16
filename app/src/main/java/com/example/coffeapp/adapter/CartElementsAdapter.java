package com.example.coffeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.database.Cart;

import java.util.List;

public class CartElementsAdapter
        extends RecyclerView.Adapter<CartElementsAdapter.CartViewHolder> {

    private final CartClickedListener cartClickedListener;
    private List<Cart> cartList;

    public CartElementsAdapter(CartClickedListener cartClickedListener) {
        this.cartClickedListener = cartClickedListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_cart_row, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        holder.bind(cart);

        holder.deleteButton.setOnClickListener(view ->
                cartClickedListener.onDeleteClick(cart));

        holder.addAmountButton.setOnClickListener(view ->
                cartClickedListener.onPlusClicked(cart));

        holder.removeAmountButton.setOnClickListener(view ->
                cartClickedListener.onMinusClicked(cart));
    }

    @Override
    public int getItemCount() {
        if (cartList == null) return 0;

        return cartList.size();
    }

    public void setMenuElementsCartList(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView menuElementName;
        TextView menuElementAmount;
        TextView menuElementPrice;
        ImageButton deleteButton;
        ImageButton addAmountButton;
        ImageButton removeAmountButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            menuElementName = itemView.findViewById(R.id.cart_menu_element_name_label);
            menuElementAmount = itemView.findViewById(R.id.cart_menu_element_amount_label);
            menuElementPrice = itemView.findViewById(R.id.cart_menu_element_price_label);
            deleteButton = itemView.findViewById(R.id.cart_delete_button);
            addAmountButton = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            removeAmountButton = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }

        public void bind(Cart cart) {
            menuElementName.setText(cart.getMenuElementName());
            menuElementAmount.setText(String.valueOf(cart.getAmount()));
            menuElementPrice.setText(cart.getMenuElementPrice() + " руб");
        }
    }

    public interface CartClickedListener {
        void onDeleteClick(Cart cart);

        void onPlusClicked(Cart cart);

        void onMinusClicked(Cart cart);
    }
}
