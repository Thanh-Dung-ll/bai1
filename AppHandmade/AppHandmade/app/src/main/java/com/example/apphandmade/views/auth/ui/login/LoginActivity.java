package com.example.apphandmade.views.auth.ui.login;

import android.view.View;

import com.example.apphandmade.views.home.MainActivity;
import com.example.apphandmade.base.BaseActivity;
import com.example.apphandmade.databinding.ActivityLoginBinding;
import com.example.apphandmade.views.auth.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {
        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterActivity.class);
            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                userDao.getAllUsers();
                if (userDao.checkUser(getApplicationContext(), binding.edtUsername.getText().toString().trim(), binding.edtPassword.getText().toString().trim())) {
                    dataShared.setToken("1");
                    startActivity(MainActivity.class);
                }
                else
                    showToast("Login failed!");
            }
        });
    }
}
