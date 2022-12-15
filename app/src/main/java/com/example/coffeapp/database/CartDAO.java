package com.example.coffeapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insertCartItem(Cart cart);

    @Query("select * from `cart`")
    LiveData<List<Cart>> getAllCartItems();

    @Delete
    void deleteCartItem(Cart cart);

    @Query("update `cart` set amount = :quantity where id = :id")
    void updateQuantity(int id, int quantity);

    @Query("update `cart` set totalMenuElementPrice = :totalItemPrice where id = :id")
    void updatePrice(int id, Double totalItemPrice);

    @Query("delete from `cart`")
    void deleteAllItems();
}
