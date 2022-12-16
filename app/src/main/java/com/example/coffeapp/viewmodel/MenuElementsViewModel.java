package com.example.coffeapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.repository.MenuElementsRepository;

import java.util.List;

public class MenuElementsViewModel extends ViewModel {

    private MenuElementsRepository menuElementsRepository;

    public MenuElementsViewModel() {
        menuElementsRepository = MenuElementsRepository.getInstance();
    }

    public LiveData<List<MenuElement>> getMenuElements() {
        return menuElementsRepository.getMenuElements();
    }
}
