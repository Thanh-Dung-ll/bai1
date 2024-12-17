package com.example.apphandmade.views.home;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.apphandmade.base.BaseActivity;
import com.example.apphandmade.databinding.ActivityAddProductBinding;
import com.example.apphandmade.views.home.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddProductActivity extends BaseActivity<ActivityAddProductBinding> {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private byte[] productImage;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo ActivityResultLauncher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri dataUri = result.getData().getData();
                            imageUri = dataUri;
                            binding.productImageView.setImageURI(imageUri);
                            productImage = uriToByteArray(imageUri);
                        }
                    }
                });

    }

    @Override
    protected ActivityAddProductBinding getViewBinding() {
        return ActivityAddProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initAction() {
        binding.productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToDatabase();
                setResult(AddProductActivity.RESULT_OK);
                finish();
            }
        });
    }

    private void addProductToDatabase() {
        String productName = binding.productNameInput.getText().toString();
        double price = Double.parseDouble(binding.priceInput.getText().toString());
        float rating = Float.parseFloat(binding.ratingInput.getText().toString());
        String productDetail = binding.productDetailInput.getText().toString();
        int quantity = Integer.parseInt(binding.quantityInput.getText().toString());
        boolean isFavorite = binding.favoriteCheckBox.isChecked();
        Product newProduct = new Product(0, productImage, productName, price, rating, productDetail, quantity, isFavorite);
        productDao.addProduct(newProduct.getImage(), newProduct.getProductName(), newProduct.getPrice(), newProduct.getRating(), newProduct.getProductDetail(), newProduct.getQuantity());
        showToast("Product added!");
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private byte[] uriToByteArray(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(AddProductActivity.RESULT_OK);
    }
}
