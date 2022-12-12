package com.example.coffeapp.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class CartItem {

    private MenuElement menuElement;
    private int amount;

    public CartItem(MenuElement menuElement, int amount) {
        this.menuElement = menuElement;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "menuElement=" + menuElement +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        return amount == cartItem.amount && Objects.equals(menuElement, cartItem.menuElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuElement, amount);
    }

    public MenuElement getMenuElement() {
        return menuElement;
    }

    public void setMenuElement(MenuElement menuElement) {
        this.menuElement = menuElement;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static DiffUtil.ItemCallback<CartItem> itemCallback = new DiffUtil.ItemCallback<CartItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getAmount() == newItem.getAmount();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.equals(newItem);
        }
    };
}
