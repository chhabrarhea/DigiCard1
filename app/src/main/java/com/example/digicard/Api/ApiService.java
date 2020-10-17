package com.example.digicard.Api;


import com.example.digicard.model.CardPostResponse;
import com.example.digicard.model.Login;
import com.example.digicard.model.card_attributes;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {



    @FormUrlEncoded
    @POST("api/")
    Call <List<CardPostResponse>> saveCard(@FieldMap Map<String, Object> fields);

    @GET("api/")
    Call<List<card_attributes>> getCards(@Query("username") String username);

    @GET("api/")
    Call<ResponseBody> deletecard(@Query("id") int id);

    @GET("users/")
    Call<Login> getUser(@Query("email") String email, @Query("password") String password);

    @FormUrlEncoded
    @POST("users/")
    Call<List<Login>> putUser(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("users/")
    Call<List<Login>> declarePremium(@Field("premium") String email,@Field("order_id") String order,@Field("transaction_id") String trans);

    @GET("users/")
    Call<Login> changePassword(@Query("email") String email, @Query("password") String password,@Query("newpass") String pass);




}
