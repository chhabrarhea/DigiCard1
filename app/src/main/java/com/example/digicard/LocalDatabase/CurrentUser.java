package com.example.digicard.LocalDatabase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.digicard.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class CurrentUser {
    private Context context;
    private SharedPreferences sp;
    public CurrentUser(Context c)
    {
        this.context=c;
        this.sp=context.getSharedPreferences(context.getString(R.string.pref_config),Context.MODE_PRIVATE);
    }



    public List<Integer> savedCards() throws IOException {
     return (List<Integer>) ObjectSerializer.deserialize(sp.getString("DigiCard", ObjectSerializer.serialize(new ArrayList<Integer>())));
    }
    public void saveMyCards(List<Integer> cardId) throws IOException {
        sp.edit().putString("DigiCard",ObjectSerializer.serialize((Serializable) cardId)).apply();
    }
}
