package com.example.mymoney;

import java.io.Serializable;

public class CategoryIncome implements Serializable {
    private int id;
    private String name;

    public CategoryIncome(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}