package com.example.apphandmade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Account_layout extends AppCompatActivity {
    ImageButton imgMenu;
    ImageView imgNguoiMua;

    ImageView imgLienHe;
    ImageView imgQlDH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgMenu = findViewById(R.id.igmbuttonMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgNguoiMua = findViewById(R.id.listnguoimua);
        imgNguoiMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Account_layout.this, DetailListCustomer.class);
                startActivity(intent1);
            }
        });
        imgLienHe = findViewById(R.id.igmbuttonaccoun);
        imgLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Account_layout.this,chat_layout.class);
                startActivity(intent2);
            }
        });

        imgQlDH= findViewById(R.id.quanlidonhang);
        imgQlDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQL = new Intent(Account_layout.this,ordermanager_layout.class);
                startActivity(intentQL);
            }
        });



    }
}