package com.example.digicard.LocalDatabase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.digicard.R;
import com.example.digicard.model.card_attributes;

import java.util.ArrayList;
import java.util.List;

public class AppController {

    private List<card_attributes> myCards;
    private  String username;
    private static AppController instance=null;
    private Context context;
    private SharedPreferences sp;
    private int premium;


    public AppController(Context c) {
            context=c;
            sp=context.getSharedPreferences(context.getString(R.string.pref_config),Context.MODE_PRIVATE);
            username=sp.getString("current_user",null);
            premium=sp.getInt(this.username,0);
            myCards=new ArrayList<>();
    }


    public int getPremium() {
        return this.premium;
    }

    public static synchronized AppController getInstance(Context c){
        if(instance==null) {
            instance = new AppController(c);
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }


    public List<card_attributes> getMyCards() {
        return myCards;
    }

    public void setMyCards(List<card_attributes> myCards) {
        this.myCards = myCards;
    }

    public void writeStatus(String user)
    {
        sp.edit().putString("current_user",user).apply();
        this.username=user;
    }
    public void writePremium(int i)
    {
        this.premium=i;
        sp.edit().putInt(this.username,premium).apply();
    }

}




