package com.example.apphandmade.views.auth.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.apphandmade.R;
import com.example.apphandmade.base.BaseActivity;
import com.example.apphandmade.databinding.ActivityRegisterBinding;
import com.example.apphandmade.views.auth.ui.login.LoginActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {
        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString().trim();
                String username = binding.edtUsername.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    showToast(getString(R.string.text_all_fields_are_required));
                } else if (!password.equals(confirmPassword)) {
                    showToast(getString(R.string.text_passwords_do_not_match));
                } else {
                    boolean isInserted = userDao.registerUser(email, username, password);
                    if (isInserted) {
                        showToast(getString(R.string.text_registration_successful));
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        showToast(getString(R.string.text_registration_failed));
                    }
                }
            }
        });
    }
}