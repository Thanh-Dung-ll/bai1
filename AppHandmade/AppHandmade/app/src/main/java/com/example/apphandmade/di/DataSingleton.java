package com.example.apphandmade.di;

import com.example.apphandmade.views.auth.model.Account;
import com.example.apphandmade.views.home.model.Product;

public class DataSingleton {
    private static DataSingleton instance;
    private Account account;
    private Product product;

    private DataSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    public void setAccountInfo(Account account) {
        this.account = account;
    }

    public Account getAccountInfo() {
        return account;
    }

    public void setProductDetail(Product productDetail) {
        this.product = productDetail;
    }

    public Product getProductDetail() {
        return product;
    }
}
