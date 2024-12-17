package com.example.apphandmade.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphandmade.views.home.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItemDao {
    private DataBaseHelper dbHelper;
    SQLiteDatabase db;

    public CartItemDao(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Add a new product
    public boolean addCartItem(CartItem item) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_CART_ITEM_ID, item.getId());
        values.put(DataBaseHelper.COLUMN_CART_IMAGE, item.getImage());
        values.put(DataBaseHelper.COLUMN_CART_PRODUCT_NAME, item.getProductName());
        values.put(DataBaseHelper.COLUMN_CART_PRODUCT_PRICE, item.getPrice());
        values.put(DataBaseHelper.COLUMN_CART_PRODUCT_QUANTITY, item.getQuantity());
        long result = db.insert(DataBaseHelper.TABLE_CART, null, values);
        return result != -1;
    }


    public void removeItem(int id) {
        db.delete(DataBaseHelper.TABLE_CART, DataBaseHelper.COLUMN_CART_ITEM_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_CART, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow((DataBaseHelper.COLUMN_CART_ITEM_ID)));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_CART_IMAGE));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_CART_PRODUCT_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_CART_PRODUCT_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_CART_PRODUCT_QUANTITY));
                cartItems.add(new CartItem(id, image, productName, price, quantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartItems;
    }

    public void clearCart() {
        db.execSQL("DELETE FROM cart");
    }

}
