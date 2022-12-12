package com.example.coffeapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeapp.model.CartItem;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.repository.CartRepository;

import java.util.List;

public class CartViewModel extends ViewModel {

    CartRepository cartRepository = new CartRepository();

    public LiveData<List<CartItem>> getCart() {
        return cartRepository.getCart();
    }

    public boolean addMenuElementToCart(MenuElement menuElementToAdd) {
        return cartRepository.addMenuElementToCart(menuElementToAdd);
    }
}
