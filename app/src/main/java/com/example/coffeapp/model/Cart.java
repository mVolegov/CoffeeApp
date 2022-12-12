package com.example.coffeapp.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<MenuElement, Integer> menuElementHasAmountMap = new HashMap<>();

    public void addMenuElementToCart(MenuElement menuElementToAdd) {
        if (menuElementHasAmountMap.containsKey(menuElementToAdd)) {
            Integer amount = menuElementHasAmountMap.get(menuElementToAdd);
            menuElementHasAmountMap.put(menuElementToAdd, ++amount);
        } else {
            menuElementHasAmountMap.put(menuElementToAdd, 1);
        }
    }

    public Map<MenuElement, Integer> getMenuElementHasAmountMap() {
        return menuElementHasAmountMap;
    }

    public void setMenuElementHasAmountMap(Map<MenuElement, Integer> menuElementHasAmountMap) {
        this.menuElementHasAmountMap = menuElementHasAmountMap;
    }
}
