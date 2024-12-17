package com.example.apphandmade.helper;

import static com.example.apphandmade.helper.DataBaseHelper.COLUMN_ID;
import static com.example.apphandmade.helper.DataBaseHelper.COLUMN_IS_FAVORITE;
import static com.example.apphandmade.helper.DataBaseHelper.TABLE_PRODUCTS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProductDao {
    private DataBaseHelper dbHelper;

    public ProductDao(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    // Add a new product
    public void addProduct(byte[] image, String productName, double price, float rating, String productDetail, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_IMAGE, image);
        values.put(DataBaseHelper.COLUMN_PRODUCT_NAME, productName);
        values.put(DataBaseHelper.COLUMN_PRICE, price);
        values.put(DataBaseHelper.COLUMN_RATING, rating);
        values.put(DataBaseHelper.COLUMN_PRODUCT_DETAIL, productDetail);
        values.put(DataBaseHelper.COLUMN_QUANTITY, quantity);
        values.put(COLUMN_IS_FAVORITE, 0);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }


    // Method to update isFavorite status
    public void updateFavoriteStatus(int productId, boolean isFavorite) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_FAVORITE, isFavorite ? 1 : 0);
        db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
    }


    // Retrieve all products
    public Cursor getAllProducts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM products", null);
    }
}
