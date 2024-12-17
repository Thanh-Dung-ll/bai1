package com.example.apphandmade.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apphandmade.constants.AppConstant;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = AppConstant.DATABASE_NAME;
    private static final int DATABASE_VERSION = 4;


    //table
    public static final String Table_SanPham = "Mathang";
    public static final String Table_Danhmuc = "Danhmuc";

    public static final String TABLE_NAME = "Mathang";
    public static final String COLUMN_TITLE = "Tenmathang";
    public static final String COLUMN_SUM = "Mota";

    public static final String TABLE_USER = "users";
    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    //    Product
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_PRODUCT_DETAIL = "product_detail";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IS_FAVORITE = "is_favorite";

    //    Cart Item
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_CART_ITEM_ID = "id";
    public static final String COLUMN_CART_PRODUCT_ID = "product_id";
    public static final String COLUMN_CART_IMAGE = "image";
    public static final String COLUMN_CART_PRODUCT_NAME = "productName";
    public static final String COLUMN_CART_PRODUCT_PRICE = "price";
    public static final String COLUMN_CART_PRODUCT_QUANTITY = "quantity";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, USERNAME TEXT, PASSWORD TEXT)");

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IMAGE + " BLOB," + COLUMN_PRODUCT_NAME + " TEXT," + COLUMN_PRICE + " REAL," + COLUMN_RATING + " REAL," + COLUMN_PRODUCT_DETAIL + " TEXT," + COLUMN_QUANTITY + " INTEGER," + COLUMN_IS_FAVORITE + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " (" + COLUMN_CART_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CART_PRODUCT_ID + " INTEGER," + COLUMN_CART_IMAGE + " BLOB," + COLUMN_CART_PRODUCT_NAME + " TEXT," + COLUMN_CART_PRODUCT_PRICE + " REAL," + COLUMN_CART_PRODUCT_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public Cursor getdata() {
        SQLiteDatabase data = this.getReadableDatabase();
        Cursor cur = data.rawQuery("Select * from Mathang ", null);
        return cur;
    }

}

