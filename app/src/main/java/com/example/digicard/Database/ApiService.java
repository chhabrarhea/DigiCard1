package com.example.digicard.Database;

import android.database.Observable;


import com.example.digicard.model.Login;
import com.example.digicard.model.card_attributes;
import com.example.digicard.model.users;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {



    @FormUrlEncoded
    @POST("api/")
    Call <ResponseBody> saveCard(@FieldMap Map<String, Object> fields);

    @GET("api/")
    Call<List<card_attributes>> getCards(@Query("id[]") List<Integer> ids);

    @GET("users/")
    Call<List<Login>> getUser(@Query("email") String email, @Query("password") String password);

    @FormUrlEncoded
    @POST("users/")
    Call<List<Login>> putUser(@Field("email") String email,@Field("password") String password);


}
