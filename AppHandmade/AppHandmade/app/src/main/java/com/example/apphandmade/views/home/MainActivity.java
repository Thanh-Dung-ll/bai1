package com.example.apphandmade.views.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphandmade.Account_layout;
import com.example.apphandmade.Danhmuc;
import com.example.apphandmade.R;
import com.example.apphandmade.Recycler;
import com.example.apphandmade.base.BaseActivity;
import com.example.apphandmade.databinding.ActivityMainBinding;
import com.example.apphandmade.di.DataSingleton;
import com.example.apphandmade.search_layout;
import com.example.apphandmade.views.auth.ui.login.LoginActivity;
import com.example.apphandmade.views.home.adapter.ProductAdapter;
import com.example.apphandmade.views.home.model.Product;
import com.google.android.material.navigation.NavigationView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    DrawerLayout drawerLayout;
    NavigationView navigationv;
    RecyclerView revProduct;
    ProductAdapter productAdapter;
    private ActivityResultLauncher<Intent> addProductLauncher;
    private static final int REQUEST_CODE = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProductLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        addProduct();
                    }
                });

        // Populate products

        // Populate products
//        addProduct();
    }

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        drawerLayout = findViewById(R.id.drawelayout);
        navigationv = findViewById(R.id.navigationvieww);
        revProduct = findViewById(R.id.revProduct);

        // Set up RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.revProduct.setLayoutManager(gridLayoutManager);

        MenuItem menuItemToHide = navigationv.getMenu().findItem(R.id.logout);
        MenuItem menuItemToHide2 = navigationv.getMenu().findItem(R.id.add_product);
        menuItemToHide.setVisible(false);
        menuItemToHide2.setVisible(false);
        View headerView = navigationv.getHeaderView(0);

        if (getIsLoggedIn()) {
            menuItemToHide.setVisible(true);
            menuItemToHide2.setVisible(true);
            DataSingleton.getInstance().setAccountInfo(userDao.getUserInfo(dataShared.getToken()));
            TextView tvName = headerView.findViewById(R.id.tvName);
            if (DataSingleton.getInstance().getAccountInfo() != null) {
                tvName.setText(DataSingleton.getInstance().getAccountInfo().getName());
                Log.e("AAA", "Data name: " + DataSingleton.getInstance().getAccountInfo().getName());
            }
        }

        ImageButton imgMenu = findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawelayout.open();
            }
        });

        navigationv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.homee) {
                    Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();

                } else if (itemID == R.id.gioithieu) {
                    Toast.makeText(MainActivity.this, "Information clicked", Toast.LENGTH_SHORT).show();
                    Intent gioithieu = new Intent(MainActivity.this, search_layout.class);
                    startActivity(gioithieu);
                } else if (itemID == R.id.danhmuc) {
                    Toast.makeText(MainActivity.this, "Catalouge clicked", Toast.LENGTH_SHORT).show();
                    Intent danhmucint = new Intent(MainActivity.this, Danhmuc.class);
                    startActivity(danhmucint);
                } else if (itemID == R.id.account) {
                    if (getIsLoggedIn())
                        startActivity(Account_layout.class);
                    else
                        startActivity(LoginActivity.class);
                } else if (itemID == R.id.logout) {
                    dataShared.removeDataLoggOut();
                    startActivity(LoginActivity.class);
                } else if (itemID == R.id.add_product) {
                    Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                    addProductLauncher.launch(intent);
                } else if (itemID== R.id.trotruyen) {
                    Intent intent2 = new Intent(MainActivity.this, Recycler.class);
                    startActivity(intent2);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    protected void initAction() {
        binding.layoutHeader.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CartActivity.class);
            }
        });


        binding.layoutHeader.srview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (productAdapter != null)
                    productAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { // No action needed
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void addProduct() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.adopt);
        byte[] imageBytes = getBytesFromBitmap(bitmap);
        productDao.addProduct(imageBytes, "Meomeo", 10.99, 2.5f, " 1.", 100);
        productDao.addProduct(imageBytes, "Meomeo", 49.99, 1.5f, " 2.", 100);
//        productDao.addProduct(imageBytes, "Easy Product 3", 59.99, 5f, "This is a sample product 3.", 100);
//        productDao.addProduct(imageBytes, "Easy Product 4", 69.99, 4.0f, "This is a sample product 4.", 100);
//        productDao.addProduct(imageBytes, "Easy Product 5", 79.99, 0.5f, "This is a sample product 5.", 100);

        List<Product> productList = new ArrayList<>();
        Cursor cursor = productDao.getAllProducts();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                String productDetail = cursor.getString(cursor.getColumnIndexOrThrow("product_detail"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                int isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow("is_favorite"));
                productList.add(new Product(id, image, productName, price, rating, productDetail, quantity, isFavorite == 1));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Set up the RecyclerView with the ProductAdapter
        productAdapter = new ProductAdapter(this, productList, productDao);
        binding.revProduct.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addProduct();
            }
        }
    }
}
