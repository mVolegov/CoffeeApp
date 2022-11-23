package com.example.coffeapp.api;

import com.example.coffeapp.model.MenuCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMenuCategories {

    private final CoffeeAPI coffeeAPI;
    private final List<MenuCategory> menuCategories;

    public ApiMenuCategories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102/coffee/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coffeeAPI = retrofit.create(CoffeeAPI.class);

        menuCategories = new ArrayList<>();
    }

    public List<MenuCategory> getAllMenuCategories() {
        Call<List<MenuCategory>> menuCategoriesCall = coffeeAPI.getAllMenuCategories();
        //menuCategories.clear();   // Если убрать, то категории будут через раз появляться

        menuCategoriesCall.enqueue(new Callback<List<MenuCategory>>() {
            @Override
            public void onResponse(Call<List<MenuCategory>> call, Response<List<MenuCategory>> response) {
                if (!response.isSuccessful()) {
                    menuCategories.add(new MenuCategory(1, String.valueOf(response.code())));

                    return;
                }

                menuCategories.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuCategory>> call, Throwable t) {
                menuCategories.add(new MenuCategory(1, "Возникла ошибка"));
            }
        });

        return menuCategories;
    }
}