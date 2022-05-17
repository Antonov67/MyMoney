package com.example.mymoney;


//класс для вывода суммарных трат по категории
public class IncomeByCategory {
    private int id;
    private String name;
    private double summa;

    public IncomeByCategory(int id, String name, double summa) {
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
