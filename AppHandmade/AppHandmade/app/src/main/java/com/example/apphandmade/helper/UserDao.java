package com.example.apphandmade.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apphandmade.di.DataSingleton;
import com.example.apphandmade.views.auth.model.Account;

public class UserDao {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public UserDao(Context context) {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean registerUser(String email, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EMAIL, email);
        values.put(DataBaseHelper.USERNAME, username);
        values.put(DataBaseHelper.PASSWORD, password);
        long result = database.insert(DataBaseHelper.TABLE_USER, null, values);
        return result != -1; // Returns true if insertion is successful
    }


    // Validate user credentials
    // Method to check user credentials
    public boolean checkUser(Context context, String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = "SELECT * FROM " + DataBaseHelper.TABLE_USER;

        String query = "SELECT * FROM " + DataBaseHelper.TABLE_USER + " WHERE " + DataBaseHelper.USERNAME + " = ? AND " + DataBaseHelper.PASSWORD + " = ?";
        Log.e("SQL Query", query);
        Log.e("Parameters", "Username: " + username + ", Password: " + password);
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        int count = cursor.getCount();
        Log.e("AAA", count + " Count");

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("USERNAME"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                new SharedPreferencesHelper(context).setToken(String.valueOf(id));
                DataSingleton.getInstance().setAccountInfo(new Account(id, name, email));
                Log.e("Retrieved User", "Username: " + username + ", Password: " + password);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count > 0;
    }

    public Account getUserInfo(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        Account user = null;
        if (cursor.moveToFirst()) {
            int idAccount = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.EMAIL));
            String retrievedUsername = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.USERNAME));
            user = new Account(idAccount, email, retrievedUsername);
        }
        cursor.close();
        db.close();
        return user;
    }


    public void getAllUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("USERNAME"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("PASSWORD"));
                Log.e("AAA", "ID " + id);
                Log.e("AAA", "EMAIL " + email);
                Log.e("AAA", "USERNAME " + username);
                Log.e("AAA", "PASSWORD " + password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}
