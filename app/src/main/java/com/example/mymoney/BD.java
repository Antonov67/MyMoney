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

    //1. Select запрос к базе в виде объекта Cursor
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
    //2. INSERT запрос для таблицы пользователь к базе данных
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

    //3. метод проверки существования юзера в системе по логину
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

    //4. проверка уникальности пользователя
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

    //5. запрос расходов определенной категории
    public static ArrayList<String> allExpenceIzCategory(Context context, int id) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT users.login, expence.summa, expence.item, expence.data FROM users INNER JOIN expence ON users.id = expence.id  INNER JOIN category_expence ON expence.id_cat = category_expence.id WHERE users.id = " + USER_ID + " AND expence.id_cat = " + id  + " ORDER BY expence.data DESC";
       // Log.d("money777", "траты");

        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
           // Log.d("money777", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            list.add(cursor.getString(3) + ": " + cursor.getString(2) + ", " + cursor.getString(1) + " руб.");
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<String>) list;
    }

    //6. выборка расходов сгруппированных по категории
    public static ArrayList<ExpenceByCategory> allExpenceGroupByCategory(Context context) {

        List<ExpenceByCategory> list = new ArrayList<>();

       // Log.d("money777", "траты");
        String sql = "SELECT users.login, SUM(expence.summa), expence.id_cat, category_expence.name FROM users INNER JOIN expence ON users.id = expence.id  INNER JOIN category_expence ON expence.id_cat = category_expence.id WHERE users.id = " + USER_ID + " GROUP BY category_expence.name";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ExpenceByCategory expenceByCategory = new ExpenceByCategory(Integer.parseInt(cursor.getString(2)),cursor.getString(3),Double.parseDouble(cursor.getString(1)));
            list.add(expenceByCategory);
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<ExpenceByCategory>) list;
    }

    //7.общие расходы
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

    //8.общие доходы
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

    //9.все категори расходов
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

    //10. INSERT запрос для таблицы expence
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


    //11.запрос доходов определенной категории
    public static ArrayList<String> allIncomeIzCategory(Context context, int id) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT users.login, income.summa, income.item, income.data FROM users INNER JOIN income ON users.id = income.id  INNER JOIN category_income ON income.id_cat = category_income.id WHERE users.id = " + USER_ID + " AND income.id_cat = " + id  + " ORDER BY income.data DESC";

        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            // Log.d("money777", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            list.add(cursor.getString(3) + ": " + cursor.getString(2) + ", " + cursor.getString(1) + " руб.");
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<String>) list;
    }

    //12.выборка доходов сгруппированных по категории
    public static ArrayList<IncomeByCategory> allIncomeGroupByCategory(Context context) {

        List<IncomeByCategory> list = new ArrayList<>();

        // Log.d("money777", "траты");
        String sql = "SELECT users.login, SUM(income.summa), income.id_cat, category_income.name FROM users INNER JOIN income ON users.id = income.id  INNER JOIN category_income ON income.id_cat = category_income.id WHERE users.id = " + USER_ID + " GROUP BY category_income.name";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            IncomeByCategory incomeByCategory = new IncomeByCategory(Integer.parseInt(cursor.getString(2)),cursor.getString(3),Double.parseDouble(cursor.getString(1)));
            list.add(incomeByCategory);
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<IncomeByCategory>) list;
    }

    //13.все категории доходов
    public static ArrayList<CategoryIncome> allCatIncome(Context context) {

        List<CategoryIncome> list = new ArrayList<>();

        String sql = "SELECT * FROM category_income";
        Cursor cursor = getDataFromBD(sql, context);
        cursor.moveToFirst();


        while (!cursor.isAfterLast()) {
            CategoryIncome categoryIncome = new CategoryIncome(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
            list.add(categoryIncome);
            cursor.moveToNext();
        }
        cursor.close();
        return (ArrayList<CategoryIncome>) list;
    }

    //14. INSERT запрос для таблицы income
    public static void addIncome(Income income, Context context){
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
        newValues.put("id", income.getId());
        newValues.put("summa", income.getSumma());
        newValues.put("data", income.getDate());
        newValues.put("item", income.getItem());
        newValues.put("id_cat", income.getId_cat());
        bd.insert("income", null, newValues);
    }

}
