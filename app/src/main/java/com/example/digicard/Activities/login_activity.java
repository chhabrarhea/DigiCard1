package com.example.digicard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.digicard.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        usernameLayout=findViewById(R.id.loginUsernameLayout);
        username=findViewById(R.id.loginUsername);
        password=findViewById(R.id.loginPassword);
        passwordLayout=findViewById(R.id.loginPasswordLayout);

    }
    public void login(View view)
    {

        if(username.getText().toString().equals("")){
            usernameLayout.setErrorEnabled(true);

            usernameLayout.setError("Username cannot be empty.");}
        else{
            usernameLayout.setErrorEnabled(false);}
       if(password.getText().toString().equals("")){
           passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("Password cannot be empty.");}
            else{
           passwordLayout.setErrorEnabled(false);}
           if(!checkLogin()){
               Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show();}

           //set current user using shared preferences

    }
    public boolean checkLogin()
    {return false;}
}