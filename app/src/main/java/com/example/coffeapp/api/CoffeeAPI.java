package com.example.coffeapp.api;

import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoffeeAPI {

    @GET("v1/menu-categories")
    Call<List<MenuCategory>> getAllMenuCategories();

    @GET("v1/menu-items")
    Call<List<MenuElement>> getAllMenuElements();
}
