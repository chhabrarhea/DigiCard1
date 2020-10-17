package com.example.digicard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;


import com.example.digicard.Api.ApiService;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.Api.Client;
import com.example.digicard.R;
import com.example.digicard.model.card_attributes;

import java.util.ArrayList;
import java.util.List;

public class ConnectivityError extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity_error);

    }

    public void tryAgain(final View view) {
        final boolean notWorking=true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Splash.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
                {
                    final AppController myInstance = AppController.getInstance(ConnectivityError.this);
                    if(myInstance.getUsername()==null)
                    {
                        Intent intent=new Intent(ConnectivityError.this,login_activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        ApiService apiService;
                        apiService=Client.getClient().create(ApiService.class);
                        apiService.getCards(myInstance.getUsername()).enqueue(new Callback<List<card_attributes>>() {
                            @Override
                            public void onResponse(Call<List<card_attributes>> call, Response<List<card_attributes>> response) {
                                String error="";
                                if(response.isSuccessful() && response.body()!=null){
                                    myInstance.setMyCards(response.body());
                                    Intent intent=new Intent(ConnectivityError.this,myCardsActivity.class);
                                    intent.putExtra("error",error);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    myInstance.setMyCards(new ArrayList<card_attributes>());
                                    Intent intent=new Intent(ConnectivityError.this,myCardsActivity.class);
                                    intent.putExtra("error",error);
                                    startActivity(intent);
                                    finish();}
                            }

                            @Override
                            public void onFailure(Call<List<card_attributes>> call, Throwable t) {
                                Toast.makeText(ConnectivityError.this, "No Active Connection Found!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(ConnectivityError.this, "No Active Connection Found!", Toast.LENGTH_SHORT).show();
                }
                }

    });
}}

