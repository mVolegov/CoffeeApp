package com.example.coffeapp;

import android.app.Application;

import com.example.coffeapp.api.ApiMenuCategories;
import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;

import java.util.List;

public class MainApp extends Application {

    private static volatile List<MenuCategory> menuCategories;
    private static volatile List<MenuElement> menuElements;

    @Override
    public void onCreate() {
        super.onCreate();

//        Cart localCart = cart;
//
//        if (localCart == null) {
//            synchronized (Cart.class) {
//                localCart = cart;
//
//                if (localCart == null) {
//                    cart = localCart = new Cart();
//                }
//            }
//        }

        ApiMenuCategories apiMenuCategories = new ApiMenuCategories();
        menuCategories = apiMenuCategories.getAllMenuCategories();
//
//        ApiMenuElements apiMenuElements = new ApiMenuElements();
//        menuElements = apiMenuElements.getAllMenuElements();
    }

    public List<MenuCategory> getMenuCategories() {
        return menuCategories;
    }
//
//    public List<MenuElement> getMenuElements() {
//        return menuElements;
//    }
}
