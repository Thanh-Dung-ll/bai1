package com.example.apphandmade.views.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apphandmade.R;
import com.example.apphandmade.base.BaseActivity;
import com.example.apphandmade.databinding.ActivityProductDetailBinding;
import com.example.apphandmade.di.DataSingleton;
import com.example.apphandmade.views.home.model.CartItem;
import com.example.apphandmade.views.home.model.Product;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ActivityProductDetailBinding getViewBinding() {
        return ActivityProductDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        findViewById(R.id.buttonAddToWishlist); // Retrieve data from intent
        int id = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("productName");
        String description = getIntent().getStringExtra("productDescription");
        double price = getIntent().getDoubleExtra("productPrice", 0.0);
        float rating = getIntent().getFloatExtra("productRating", 0.0f);
        int quantity = getIntent().getIntExtra("quantity", 0);
        int isFavorite = getIntent().getIntExtra("isFavorite", 0);
        byte[] image = getIntent().getByteArrayExtra("productImage"); // Set data to views
        if (image != null && image.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            binding.productImage.setImageBitmap(bitmap);
        }


        binding.productName.setText(name);
        binding.productDescription.setText(description);
        binding.productPrice.setText(price + " D");
        binding.productRating.setRating(rating);
        binding.productRating.setEnabled(false);
        DataSingleton.getInstance().setProductDetail(new Product(id, image, name, price, rating, description, quantity, isFavorite == 1));
    }

    @Override
    protected void initAction() {
        binding.buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product productDetail = DataSingleton.getInstance().getProductDetail();
                if (cartItemDao.addCartItem(new
                        CartItem(productDetail.getId(),
                        productDetail.getImage(),
                        productDetail.getProductName(),
                        productDetail.getPrice(),
                        productDetail.getQuantity()))
                ) {
                    showToast("Them vao gio hang thanh cong");
                }else{
                    showToast("Loi them vao gio hang!");
                }
            }
        });
    }
}