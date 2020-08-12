package com.example.digicard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digicard.Database.AppController;
import com.example.digicard.R;

public class ConnectivityError extends AppCompatActivity {
TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity_error);
        if(getIntent().getStringExtra("error")!=null)
        {
            message=findViewById(R.id.textView);
            message.setText(getIntent().getStringExtra("error"));
        }
    }

    public void tryAgain(View view) {

    }


}


