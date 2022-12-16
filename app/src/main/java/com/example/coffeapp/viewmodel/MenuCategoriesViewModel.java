package com.example.coffeapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.repository.MenuCategoriesRepository;

import java.util.List;

public class MenuCategoriesViewModel extends ViewModel {

    private MenuCategoriesRepository menuCategoriesRepository;

    public MenuCategoriesViewModel() {
        menuCategoriesRepository = MenuCategoriesRepository.getInstance();
    }

    public LiveData<List<MenuCategory>> getMenuCategories() {
        return menuCategoriesRepository.getMenuCategories();
    }
}
