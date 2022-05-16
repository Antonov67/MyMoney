package com.example.mymoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BD {

    public static int USER_ID = -1;

    //Select запрос к базе в виде объекта Cursor
    public static Cursor getDataFromBD(String sqlQuery, Context context){
        DataBaseHelper databaseHelper;
        SQLiteDatabase bd;
        databaseHelper = new DataBaseHelper(context);
        try {
            databaseHelper.updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bd = databaseHelper.getReadableDatabase();
        Cursor cursor;
        cursor = bd.rawQuery(sqlQuery,null);
        return cursor;
    }
    //INSERT запрос для таблицы пользователь к базе данных
    public static void addUser(User user, Context context){
        DataBaseHelper databaseHelper;
        SQLiteDatabase bd;
        databaseHelper = new DataBaseHelper(context);
        try {
            databaseHelper.updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bd = databaseHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("login", user.getLogin());
        newValues.put("pswrd", user.getPswrd());
        newValues.put("name", user.getName());
        bd.insert("users", null, newValues);
    }

    //метод проверки существования юзера в системе по логину
    public static boolean isUserExist(String login, String pswrd, Context context){
        String sql = "SELECT * FROM users";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            if (cursor.getString(1).equals(login) && cursor.getString(2).equals(pswrd)){
                USER_ID = Integer.parseInt(cursor.getString(0));
                return true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }

    public static boolean isUserUniq(String login, Context context){
        boolean isUniq = true;
        String sql = "SELECT * FROM users";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            if (cursor.getString(1).equals(login)){
                isUniq = false;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return isUniq;
    }

    public static ArrayList<String> allExpence(Context context) {

        List<String> list = new ArrayList<>();

       // String sql = "SELECT users.login, expence.summa, category_expence.name, expence.data FROM users INNER JOIN expence ON users.id = expence.id  INNER JOIN category_expence ON expence.id_cat = category_expence.id WHERE users.id = " + USER_ID + " ORDER BY expence.data DESC";
       // Log.d("money777", "траты");
        String sql = "SELECT users.login, SUM(expence.summa), category_expence.name FROM users INNER JOIN expence ON users.id = expence.id  INNER JOIN category_expence ON expence.id_cat = category_expence.id WHERE users.id = " + USER_ID + " GROUP BY category_expence.name";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
           // Log.d("money777", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
           // list.add(cursor.getString(3) + ": " + cursor.getString(1) + " руб. категория: " + cursor.getString(2));
            list.add(cursor.getString(2) + ": " + cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<String>) list;
    }

    //общие расходы
    public static double totalExpence(Context context) {
        double summa = 0;
        String sql = "SELECT SUM(expence.summa) FROM users INNER JOIN expence ON users.id = expence.id WHERE users.id = " + USER_ID;
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            summa = Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return summa;
    }

    //общие доходы
    public static double totalIncome(Context context) {
        double summa = 0;
        String sql = "SELECT SUM(income.summa) FROM users INNER JOIN income ON users.id = income.id WHERE users.id = " + USER_ID;
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            summa = Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return summa;
    }

    //все категори расходов
    public static ArrayList<CategoryExpence> allCatExpence(Context context) {

        List<CategoryExpence> list = new ArrayList<>();

        String sql = "SELECT * FROM category_expence";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();


        while (!cursor.isAfterLast()) {
            CategoryExpence categoryExpence = new CategoryExpence(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
            list.add(categoryExpence);
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<CategoryExpence>) list;
    }

    //INSERT запрос для таблицы expence
    public static void addExpence(Expence expence, Context context){
        DataBaseHelper databaseHelper;
        SQLiteDatabase bd;
        databaseHelper = new DataBaseHelper(context);
        try {
            databaseHelper.updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bd = databaseHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("id", expence.getId());
        newValues.put("summa", expence.getSumma());
        newValues.put("data", expence.getDate());
        newValues.put("item", expence.getItem());
        newValues.put("id_cat", expence.getId_cat());
        bd.insert("expence", null, newValues);
    }

}
