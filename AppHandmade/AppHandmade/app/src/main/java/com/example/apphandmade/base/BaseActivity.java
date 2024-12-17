package com.example.apphandmade.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.apphandmade.helper.CartItemDao;
import com.example.apphandmade.helper.DataBaseHelper;
import com.example.apphandmade.helper.ProductDao;
import com.example.apphandmade.helper.SharedPreferencesHelper;
import com.example.apphandmade.helper.UserDao;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T binding;
    protected DataBaseHelper db;
    protected UserDao userDao;
    protected ProductDao productDao;
    protected CartItemDao cartItemDao;

    private Boolean isLoggedIn;
    protected SharedPreferencesHelper dataShared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        // Common functionality can be added here
        db = new DataBaseHelper(this);
        userDao = new UserDao(this);
        productDao = new ProductDao(this);
        cartItemDao = new CartItemDao(this);
        dataShared = new SharedPreferencesHelper(this);
        initViews();
        initAction();
    }

    // Abstract method to initialize View Binding
    protected abstract T getViewBinding();

    // Method to show a Toast message
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    } // Method to handle common initialization logic

    // Method to start an activity without data
    protected void startActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    // Method to start an activity with data
    protected void startActivityWithData(Class<?> targetActivity, Bundle data) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtras(data);
        startActivity(intent);
    }

    public boolean getIsLoggedIn(){
        return !dataShared.getToken().isEmpty();
    }

    // Method to get data from intent
    protected Bundle getIntentData() {
        return getIntent().getExtras();
    }

    protected abstract void initViews();

    protected abstract void initAction();
}