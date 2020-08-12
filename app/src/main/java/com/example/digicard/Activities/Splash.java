package com.example.digicard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.example.digicard.Database.ApiService;
import com.example.digicard.Database.AppController;
import com.example.digicard.Database.Client;
import com.example.digicard.LocalDatabase.CurrentUser;
import com.example.digicard.R;
import com.example.digicard.model.card_attributes;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread back=new Thread(){
            @Override
            public void run() {
                super.run();
                final AppController myInstance = AppController.getInstance();
                CurrentUser currentUser = new CurrentUser(Splash.this);
                try {
                    sleep(5000);
                    myInstance.setCardId(currentUser.savedCards());
                    if(myInstance.getCardId().size()>0){
                        ApiService apiService = Client.getClient().create(ApiService.class);
                        apiService.getCards(myInstance.getCardId()).enqueue(new Callback<List<card_attributes>>() {
                            @Override
                            public void onResponse(Call<List<card_attributes>> call, Response<List<card_attributes>> response) {
                                if (response.isSuccessful()) {
                                    myInstance.setMyCards(response.body());
                                    Intent intent = new Intent(Splash.this, myCardsActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(Splash.this, ConnectivityError.class);
                                    intent.putExtra("error", response.code());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<List<card_attributes>> call, Throwable t) {
                                Intent intent = new Intent(Splash.this, ConnectivityError.class);
                                intent.putExtra("error", t.getMessage());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    else {
                        myInstance.setMyCards(new ArrayList<card_attributes>());
                        Intent intent = new Intent(Splash.this, myCardsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };
        back.start();




    }
}