package com.example.coffeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeapp.database.Cart;
import com.example.coffeapp.repository.CartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepository cartRepository;

    public CartViewModel(@NonNull Application application) {
        super(application);

        cartRepository = new CartRepository(application);
    }

    public LiveData<List<Cart>> getAllCartItems() {
        return cartRepository.getAllCartItemsLiveData();
    }

    public void insertCartItem(Cart cart) {
        cartRepository.insertCartItem(cart);
    }

    public void updateCartItemQuantity(int id, int quantity) {
        cartRepository.updateCartItemQuantity(id, quantity);
    }

    public void updateCartItemPrice(int id, Double price) {
        cartRepository.updateCartItemPrice(id, price);
    }

    public void deleteCartItem(Cart cart) {
        cartRepository.deleteCartItem(cart);
    }

    public void deleteAllCartItems() {
        cartRepository.deleteAllCartItems();
    }
}
