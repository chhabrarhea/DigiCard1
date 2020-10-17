package com.example.digicard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.digicard.Api.ApiService;
import com.example.digicard.Api.Client;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.R;
import com.example.digicard.model.Login;
import com.example.digicard.model.card_attributes;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class login_activity extends AppCompatActivity {
    TextInputLayout usernameLayout;
    TextInputEditText username;
    TextInputEditText password;
    TextInputLayout passwordLayout;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        usernameLayout = findViewById(R.id.loginUsernameLayout);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        passwordLayout = findViewById(R.id.loginPasswordLayout);

    }

    public void login(View view) {
        if (!isValidEmail(username.getText().toString())) {
            usernameLayout.setError("Invalid Email.");
            usernameLayout.setErrorEnabled(true);
            return;
        } else {
            usernameLayout.setErrorEnabled(false);
        }
        if ((password.getText()).toString().equals("")) {
            passwordLayout.setError("Password cannot be empty.");
            passwordLayout.setErrorEnabled(true);
            return;
        } else {
            passwordLayout.setErrorEnabled(false);
        }
        checkLogin();
    }

    public void loadCards(String user) {
        ApiService apiService = Client.getClient().create(ApiService.class);
        final AppController instance = AppController.getInstance(login_activity.this);
        apiService.getCards(user).enqueue(new Callback<List<card_attributes>>() {
            @Override
            public void onResponse(Call<List<card_attributes>> call, Response<List<card_attributes>> response) {
                String error = "";
                if (response.isSuccessful()) {
                    if(response.body()!=null)
                    instance.setMyCards(response.body());
                    else
                        instance.setMyCards(new ArrayList<card_attributes>());

                } else  {
                    instance.setMyCards(new ArrayList<card_attributes>());
                    error = String.valueOf(response.code());
                }

                Intent intent = new Intent(login_activity.this, myCardsActivity.class);
                intent.putExtra("error", error);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<List<card_attributes>> call, Throwable t) {
                instance.setMyCards(new ArrayList<card_attributes>());
                String error = t.getMessage();
                Intent intent = new Intent(login_activity.this, myCardsActivity.class);
                intent.putExtra("error", error);
                startActivity(intent);
                finish();
            }
        });
    }

    public void checkLogin() {
        ApiService apiService = Client.getClient().create(ApiService.class);
        apiService.getUser((username.getText()).toString(), (password.getText()).toString()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("true")) {
                    Toast.makeText(login_activity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                    AppController appController = AppController.getInstance(login_activity.this);
                    appController.writeStatus(username.getText().toString());
                    appController.writePremium(response.body().getPremium());
                    loadCards(appController.getUsername());
                } else
                    Toast.makeText(login_activity.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(login_activity.this, "Error Occured,Try Again!", Toast.LENGTH_LONG).show();
                Log.i("error",t.getMessage());
            }
        });
    }

    public void signup(View v) {
        Intent intent = new Intent(login_activity.this, signup_activity.class);
        startActivity(intent);
        finish();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null || target.equals(""))
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}