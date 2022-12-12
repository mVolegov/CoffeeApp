package com.example.coffeapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffeapp.model.CartItem;
import com.example.coffeapp.model.MenuElement;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();

    public LiveData<List<CartItem>> getCart() {
        if (mutableCart.getValue() == null) initCart();

        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<>());
    }

    public boolean addMenuElementToCart(MenuElement menuElementToAdd) {
        if (mutableCart.getValue() == null) initCart();

        List<CartItem> cartItems = new ArrayList<>(mutableCart.getValue());
        CartItem cartItemToAdd = new CartItem(menuElementToAdd, 1);
        cartItems.add(cartItemToAdd);

        mutableCart.setValue(cartItems);

        return true;
    }
}
