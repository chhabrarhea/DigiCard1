package com.example.digicard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digicard.Api.ApiService;

import com.example.digicard.Api.Client;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.R;
import com.example.digicard.model.Login;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class signup_activity extends AppCompatActivity {
EditText email;
EditText password;
EditText confirmPassword;
TextInputLayout emailLayout;
TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);
        emailLayout=findViewById(R.id.signEmailLayout);
        passwordLayout=findViewById(R.id.signPasswordLayout);
        email=findViewById(R.id.signEmail);
        password=findViewById(R.id.signPassword);
        confirmPassword=findViewById(R.id.signConfirmPassword);

    }

    public void signup(View view)
    {
        if(email.getText().toString().equals("") || !isValidEmail(email.getText().toString())){
            emailLayout.setErrorEnabled(true);
        emailLayout.setError("Invalid Email");
        return;}
        else
        {
            emailLayout.setErrorEnabled(false);
        }

         if (password.getText().toString().equals("")){
             passwordLayout.setError("Invalid Password");
             passwordLayout.setErrorEnabled(true);
             return;
        }
         else
         {
             passwordLayout.setErrorEnabled(false);
         }
        if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            passwordLayout.setError("Passwords must match");
            passwordLayout.setErrorEnabled(true);
            return;
        }
        else
        {
            passwordLayout.setErrorEnabled(false);
            ApiService apiService= Client.getClient().create(ApiService.class);
            apiService.putUser(email.getText().toString(),password.getText().toString()).enqueue(new Callback<List<Login>>() {
                @Override
                public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                    if (response.isSuccessful())
                    {
                        if(response.body().get(0).getMessage().equals("true"))
                        {
                            AppController myInstance=new AppController(signup_activity.this);
                            myInstance.writeStatus(email.getText().toString());
                            myInstance.writePremium(0);
                            Intent intent=new Intent(signup_activity.this,myCardsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else if(response.body().get(0).getMessage().equals("registered")){
                            Toast.makeText(signup_activity.this, "Email already registered!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(signup_activity.this, "Error Occured,Try Again!s", Toast.LENGTH_SHORT).show();


                    }
                    else
                        Toast.makeText(signup_activity.this, "Error Occured,Try Again!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<Login>> call, Throwable t) {
                    Toast.makeText(signup_activity.this, "Error Occured,Try Again!"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null || target.equals(""))
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void login(View view)
    {
        Intent intent=new Intent(signup_activity.this,login_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}