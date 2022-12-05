package com.example.coffeapp.mocks;

import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockedDataInitializer {

    private List<MenuCategory> menuCategories;
    private List<MenuElement> menuElements;

    public List<MenuCategory> getMockedMenuCategories() {
        menuCategories = new ArrayList<MenuCategory>() {{
            add(new MenuCategory(1, "Еда"));
            add(new MenuCategory(2, "Напитки"));
            add(new MenuCategory(3, "Горячее"));
            add(new MenuCategory(4, "Холодное"));
            add(new MenuCategory(5, "Летнее меню"));
            add(new MenuCategory(6, "Зимнее меню"));
        }};

        return menuCategories;
    }

    public List<MenuElement> getMockedMenuElements() {
        getMockedMenuCategories();

//        menuElements = new ArrayList<MenuElement>() {{
//            add(new MenuElement(1, "Какао", BigDecimal.valueOf(100.00), "ssdfsf", menuCategories.get(0)));
//            add(new MenuElement(2, "Допио", BigDecimal.valueOf(120.20), "ssdfsf", menuCategories.get(1)));
//            add(new MenuElement(3, "Что-то ", BigDecimal.valueOf(121.20), "ssdfsf", menuCategories.get(2)));
//            add(new MenuElement(4, "Что-то 1", BigDecimal.valueOf(121.20), "ssdfsf", menuCategories.get(3)));
//            add(new MenuElement(5, "Что-то 2", BigDecimal.valueOf(121.20), "ssdfsf", menuCategories.get(4)));
//            add(new MenuElement(6, "Что-то 3", BigDecimal.valueOf(121.20), "ssdfsf", menuCategories.get(0)));
//            add(new MenuElement(7, "Что-то 4", BigDecimal.valueOf(121.20), "ssdfsf", menuCategories.get(1)));
//            add(new MenuElement(8, "Что-то 5", BigDecimal.valueOf(121.20), "ssdfsf", menuCategories.get(2)));
//            add(new MenuElement(9, "", BigDecimal.ZERO, "ssdfsf", menuCategories.get(3)));
//        }};

        return menuElements;
    }
}
