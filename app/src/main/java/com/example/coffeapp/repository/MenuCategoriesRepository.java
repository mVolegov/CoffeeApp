package com.example.coffeapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.network.CoffeeAPI;
import com.example.coffeapp.network.RetrofitInstance;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCategoriesRepository {

    private static MenuCategoriesRepository instance;

    private MutableLiveData<List<MenuCategory>> menuCategories;

    public static MenuCategoriesRepository getInstance() {
        if (instance == null) instance = new MenuCategoriesRepository();

        return instance;
    }

    private MenuCategoriesRepository() {
        menuCategories = new MutableLiveData<>();
        makeApiCall();
    }

    public MutableLiveData<List<MenuCategory>> getMenuCategories() {
        return menuCategories;
    }

    private void makeApiCall() {
        CoffeeAPI coffeeAPI = RetrofitInstance.getRetrofitClient().create(CoffeeAPI.class);

        Call<List<MenuCategory>> call = coffeeAPI.getAllMenuCategories();
        call.enqueue(new Callback<List<MenuCategory>>() {
            @Override
            public void onResponse(Call<List<MenuCategory>> call,
                                   Response<List<MenuCategory>> response) {
                if (!response.isSuccessful()) {
                    menuCategories.postValue(Collections.singletonList(new MenuCategory(
                            1,
                                String.valueOf(response.code())
                            )
                        )
                    );
                }

                menuCategories.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuCategory>> call, Throwable t) {
                menuCategories.postValue(Collections.singletonList(new MenuCategory(
                        1,
                        "Возникла ошибка" + t.getMessage()
                        )
                    )
                );
            }
        });
    }
}
