package com.example.digicard.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import com.example.digicard.Api.ApiService;

import com.example.digicard.Api.Client;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.R;
import com.example.digicard.model.card_attributes;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread back=new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Splash.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
                    {
                    final AppController myInstance = AppController.getInstance(Splash.this);
                        if(myInstance.getUsername()==null)
                        {
                            Intent intent=new Intent(Splash.this,login_activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                                ApiService apiService= Client.getClient().create(ApiService.class);
                                apiService.getCards(myInstance.getUsername()).enqueue(new Callback<List<card_attributes>>() {
                                    @Override
                                    public void onResponse(Call<List<card_attributes>> call, Response<List<card_attributes>> response) {
                                        String error="";
                                        if(response.isSuccessful() && response.body()!=null){
                                            myInstance.setMyCards(response.body());
                                            Intent intent=new Intent(Splash.this,myCardsActivity.class);
                                            intent.putExtra("error",error);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            myInstance.setMyCards(new ArrayList<card_attributes>());
                                            Intent intent=new Intent(Splash.this,myCardsActivity.class);
                                            intent.putExtra("error",error);
                                            startActivity(intent);
                                            finish();}
                                    }

                                    @Override
                                    public void onFailure(Call<List<card_attributes>> call, Throwable t) {
                                        Intent intent=new Intent(Splash.this,ConnectivityError.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        String error=t.getMessage();
                                        intent.putExtra("error",error);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }


                    }
                    else{
                          Intent intent=new Intent(Splash.this,ConnectivityError.class);
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