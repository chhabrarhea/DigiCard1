package com.example.digicard.Database;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.content.Intent;

import com.example.digicard.Activities.ConnectivityError;
import com.example.digicard.Activities.Splash;
import com.example.digicard.Activities.myCardsActivity;
import com.example.digicard.LocalDatabase.CurrentUser;
import com.example.digicard.model.card_attributes;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AppController extends Application {
    private List<Integer> cardId;
    private List<card_attributes> myCards;
    private static AppController instance=null;

    public List<Integer> getCardId() {
        return cardId;
    }
    public static synchronized AppController getInstance(){
        if(instance==null)
            instance=new AppController();
        return instance;
    }

    public void setCardId(List<Integer> cardId) {
        this.cardId = cardId;
    }

    public List<card_attributes> getMyCards() {
        return myCards;
    }

    public void setMyCards(List<card_attributes> myCards) {
        this.myCards = myCards;
    }
}




