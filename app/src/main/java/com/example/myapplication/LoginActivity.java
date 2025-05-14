package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.AuthResponse;
import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.ProfileUpdate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout emailLayout, passwordLayout, usernameLayout;
    TextInputEditText emailEditText, passwordEditText, usernameEditText;
    Button loginBtn, authBtn;

    boolean correctEmail = false, countEmail = false, isNullEmail = false;
    boolean countPassword = false, isNullPassword = false;
    boolean countUserName = false, isNullUserName = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        TextFieldsValidate();

        loginBtn.setOnClickListener(listener);
        authBtn.setOnClickListener(listener);
    }

    public void init() {
        emailLayout = findViewById(R.id.emailTextLayout);
        emailEditText = findViewById(R.id.emailText);
        passwordLayout = findViewById(R.id.passwordTextLayout);
        passwordEditText = findViewById(R.id.passwordText);
        usernameLayout = findViewById(R.id.usernameTextLayout);
        usernameEditText = findViewById(R.id.usernameText);
        loginBtn = findViewById(R.id.loginBtn);
        authBtn = findViewById(R.id.authBtn);
    }

    public void TextFieldsValidate() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailLayout.setError("Введите корректный email");
                    correctEmail = false;
                } else {
                    emailLayout.setError(null);
                    correctEmail = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > emailLayout.getCounterMaxLength()) {
                    emailLayout.setError("Электронная почта не может содержать больше "
                            + emailLayout.getCounterMaxLength() + " символов");
                    countEmail = false;
                } else {
                    countEmail = true;
                }
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = passwordEditText.getText().toString();
                if (password.length() < 6) {
                    passwordLayout.setError("Пароль должен содержать минимум 6 символов");
                    countPassword = false;
                } else if (password.length() >= 10) {
                    passwordLayout.setError("Пароль не должен содержать больше 10 символов");
                    countPassword = false;
                } else {
                    passwordLayout.setError(null);
                    countPassword = true;
                }
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > usernameLayout.getCounterMaxLength()) {
                    usernameLayout.setError("Имя пользователя не может содержать больше "
                            + usernameLayout.getCounterMaxLength() + " символов");
                    countUserName = false;
                } else {
                    usernameLayout.setError(null);
                    countUserName = true;
                }
            }
        });
    }

    View.OnClickListener listener = v -> {
        boolean validPassword = checkPassword();
        boolean validEmail = checkEmail();
        boolean validUserName = checkUserName();

        if (validPassword && validEmail && validUserName) {
            String userName = usernameLayout.getEditText().getText().toString();
            String email = emailLayout.getEditText().getText().toString();
            String password = passwordLayout.getEditText().getText().toString();

            if (v.getId() == R.id.loginBtn) {
                loginUser(userName, email, password);
            } else if (v.getId() == R.id.authBtn) {
                authUser(userName, email, password);
            }
        }
    };

    public void updateProfile(String userName){
        SupabaseClient supabaseClient = new SupabaseClient();
        ProfileUpdate profileUpdate = new ProfileUpdate(
                userName,
                "profile1.png"
        );

        supabaseClient.updateProfile(profileUpdate,new SupabaseClient.SBC_Callback() {

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> {
                    Log.e("updateProfile:onFailure", e.getLocalizedMessage());
                });
            }

            @Override
            public void onResponse(String responseBody) {
                runOnUiThread(() -> {
                    Log.e("updateProfile:onResponse", responseBody);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                });
            }
        });
    }
    public void authUser(String userName, String email, String password) {
        SupabaseClient supabaseClient = new SupabaseClient();
        LoginRequest loginRequest = new LoginRequest(
                email,
                password
        );
        supabaseClient.registr(loginRequest,new SupabaseClient.SBC_Callback() {

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> {
                    Log.e("authUser:onFailure", e.getLocalizedMessage());
                });
            }
            @Override
            public void onResponse(String responseBody) {
                runOnUiThread(() -> {
                    Log.e("authUser:onResponse", responseBody);
                    Gson gson = new Gson();
                    AuthResponse auth = gson.fromJson(responseBody, AuthResponse.class);

                    DataBinding.saveBearerToken("Bearer " + auth.getAccess_token());
                    DataBinding.saveUuidUser(auth.getUser().getId());
                    updateProfile(userName);
                    Log.e("authUser:onResponse", auth.getUser().getId());

                });
            }
        });
    }


            public void loginUser(String userName, String email, String password) {
                SupabaseClient supabaseClient = new SupabaseClient();
                LoginRequest loginRequest = new LoginRequest(
                        email,
                        password
                );
                supabaseClient.login(loginRequest,new SupabaseClient.SBC_Callback() {

                    @Override
                    public void onFailure(IOException e) {
                        runOnUiThread(() -> {
                            Log.e("LoginUser:onFailure", e.getLocalizedMessage());
                        });
                    }

                    @Override
                    public void onResponse(String responseBody) {
                        runOnUiThread(() -> {
                            Log.e("loginUser:onResponse", responseBody);
                            Gson gson = new Gson();
                            AuthResponse auth = gson.fromJson(responseBody, AuthResponse.class);

                            DataBinding.saveBearerToken("Bearer " + auth.getAccess_token());
                            DataBinding.saveUuidUser(auth.getUser().getId());

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Log.e("loginUser:onResponse", auth.getUser().getId());

                        });
                    }
                });
    }

    public boolean checkUserName() {
        if (usernameEditText.getText().toString().isEmpty()) {
            isNullUserName = false;
            usernameLayout.setError("Обязательное поле");
        } else {
            isNullUserName = true;
        }

        if (countUserName && isNullUserName) {
            return true;
        }
        return false;
    }

    public boolean checkPassword() {

        String password = passwordEditText.getText().toString();
        return password.length() >= 6 && password.length() <= 10;
    }

    public boolean checkEmail() {

        String email = emailEditText.getText().toString();
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}