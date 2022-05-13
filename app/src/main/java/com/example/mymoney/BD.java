package com.example.mymoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class BD {
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
}
