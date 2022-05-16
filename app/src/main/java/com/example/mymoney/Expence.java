package com.example.mymoney;

public class Expence {
    private int id;
    private String date;
    private double summa;
    private String item;
    private int id_cat;

    public Expence(int id, String date, double summa, String item, int id_cat) {
        this.id = id;
        this.date = date;
        this.summa = summa;
        this.item = item;
        this.id_cat = id_cat;
    }
}
