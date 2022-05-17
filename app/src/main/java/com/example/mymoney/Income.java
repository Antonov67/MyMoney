package com.example.mymoney;

public class Income {
    private int id;
    private String date;
    private double summa;
    private String item;
    private int id_cat;

    public Income(int id, String date, double summa, String item, int id_cat) {
        this.id = id;
        this.date = date;
        this.summa = summa;
        this.item = item;
        this.id_cat = id_cat;
    }

    @Override
    public String toString() {
        return "Будет добавлено: " +
                "id=" + id +
                ", date='" + date + '\'' +
                ", summa=" + summa +
                ", item='" + item + '\'' +
                ", id_cat=" + id_cat;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getSumma() {
        return summa;
    }

    public String getItem() {
        return item;
    }

    public int getId_cat() {
        return id_cat;
    }
}

