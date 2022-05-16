package com.example.mymoney;


//класс для вывода суммарных трат по категории
public class ExpenceByCategory {
    private int id;
    private String name;
    private double summa;

    public ExpenceByCategory(int id, String name, double summa) {
        this.id = id;
        this.name = name;
        this.summa = summa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSumma() {
        return summa;
    }
}
