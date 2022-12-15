package com.example.coffeapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.coffeapp.database.Cart;
import com.example.coffeapp.database.CartDAO;
import com.example.coffeapp.database.CartDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepository {

    private CartDAO cartDAO;
    private LiveData<List<Cart>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CartRepository(Application application) {
        cartDAO = CartDatabase.getInstance(application).cartDAO();
        allCartItemsLiveData = cartDAO.getAllCartItems();
    }

    public LiveData<List<Cart>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public void insertCartItem(Cart cart) {
        executor.execute(() -> cartDAO.insertCartItem(cart));
    }

    public void deleteCartItem(Cart cart) {
        executor.execute(() -> cartDAO.deleteCartItem(cart));
    }

    public void updateCartItemQuantity(int id, int quantity) {
        executor.execute(() -> cartDAO.updateQuantity(id, quantity));
    }

    public void updateCartItemPrice(int id, Double price) {
        executor.execute(() -> cartDAO.updatePrice(id, price));
    }

    public void deleteAllCartItems() {
        executor.execute(() -> cartDAO.deleteAllItems());
    }
}
