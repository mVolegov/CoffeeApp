package com.example.coffeapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String menuElementName;

    private Double menuElementPrice;

    private int amount;

    private Double totalMenuElementPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuElementName() {
        return menuElementName;
    }

    public void setMenuElementName(String menuElementName) {
        this.menuElementName = menuElementName;
    }

    public Double getMenuElementPrice() {
        return menuElementPrice;
    }

    public void setMenuElementPrice(Double menuElementPrice) {
        this.menuElementPrice = menuElementPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getTotalMenuElementPrice() {
        return totalMenuElementPrice;
    }

    public void setTotalMenuElementPrice(Double totalMenuElementPrice) {
        this.totalMenuElementPrice = totalMenuElementPrice;
    }
}
