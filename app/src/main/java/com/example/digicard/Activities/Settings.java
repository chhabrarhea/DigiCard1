package com.example.digicard.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digicard.Api.ApiService;
import com.example.digicard.Api.Client;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.R;
import com.example.digicard.model.Login;
import com.example.digicard.model.card_attributes;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings  extends AppCompatActivity implements PaymentResultListener {
    AppController appController;
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        appController=new AppController(Settings.this);
        Toolbar toolbar=findViewById(R.id.toolbar);
        if(appController.getPremium()==1){
            TextView textview=findViewById(R.id.premium);
        textview.setText("Premium Member");}

    }

    public void changePassword(View view){
        EditText editText=findViewById(R.id.old);
        EditText editText1=findViewById(R.id.newP);
        EditText editText2=findViewById(R.id.confirmNew);
        if(editText.getText().toString().equals(""))
            editText.setError("Cannot be Empty");
        else if(editText1.getText().toString().equals(""))
            editText1.setError("Cannot be Empty");
        else if(!editText2.getText().toString().equals(editText1.getText().toString()))
            editText2.setError("Passwords must match");
        else
        {
             ApiService apiService=Client.getClient().create(ApiService.class);
             apiService.changePassword(appController.getUsername(),editText.getText().toString(),editText1.getText().toString()).enqueue(new Callback<Login>() {
                 @Override
                 public void onResponse(Call<Login> call, Response<Login> response) {
                     if(response.isSuccessful() && response.body()!=null)
                     {

                         if(response.body().getMessage().equals("true")){
                             Toast.makeText(Settings.this, "Password Changed!", Toast.LENGTH_SHORT).show();
                             return;
                         }
                         else if(response.body().getMessage().equals("false")){
                             Toast.makeText(Settings.this, "Please enter correct Password!", Toast.LENGTH_SHORT).show();
                             return;
                         }

                     }
                     Toast.makeText(Settings.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure(Call<Login> call, Throwable t) {
                     Toast.makeText(Settings.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                 }
             });
        }


    }
    public void aboutUs(View view){  Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_VIEW);
        intent1.addCategory(Intent.CATEGORY_BROWSABLE);
        intent1.setData(Uri.parse("https://kohli.company/KOHLI/"));
        startActivity(intent1);}
    public void rateUs(View view){}
    public void logout(View view)
    {
        appController.setMyCards(new ArrayList<card_attributes>());
        appController.writeStatus(null);
        Intent intent=new Intent(Settings.this,login_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public void goPremium(View view){
        if(appController.getPremium()==0)
        {
            AlertDialog.Builder alb=new AlertDialog.Builder(this);
            alb.setTitle("Go Premium")
                    .setMessage("Get Unlimited Custom Digital Business Cards for just 100 INR")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setPositiveButton("GO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    createOrder();
                }
            }).show();}
        }


    private void createOrder(){
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    JSONObject orderRequest = new JSONObject();
                    orderRequest.put("amount", 10000); // amount in the smallest currency unit
                    orderRequest.put("currency", "INR");
                    orderRequest.put("receipt", "order_rcptid_11");
                    orderRequest.put("payment_capture", false);
                    RazorpayClient razorpay=new RazorpayClient("rzp_test_dHMptmJ9G5HM1O","RoEgKoG1N6VXsIZqvtz9YwAm");
                    Order order = razorpay.Orders.create(orderRequest);
                    order_id=order.get("id");
                    startPayment(order_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }}};
        thread.start();
    }
    private void startPayment(String id){
        try {
            final Activity activity = this;
            final Checkout co = new Checkout();
            JSONObject options = new JSONObject();
            options.put("name", "Kohli Media LLP");
            options.put("description", "Test Payment");
            options.put("order_id",id);
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount","10000");
            JSONObject preFill = new JSONObject();
            preFill.put("email", appController.getUsername());
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        ApiService apiService= Client.getClient().create(ApiService.class);
        apiService.declarePremium(appController.getUsername(),order_id,s).enqueue(new Callback<List<Login>>() {
            @Override
            public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {

                if (response.isSuccessful() && response.body().get(0).getMessage().equals("true"))
                {
                    Toast.makeText(getApplicationContext(),"Enjoy Your Custom Cards!",Toast.LENGTH_SHORT).show();
                    appController.writePremium(1);
                    TextView textview=findViewById(R.id.premium);
                    textview.setText("Premium Member");
                }

                else{
                    //Make a refund


                }
            }

            @Override
            public void onFailure(Call<List<Login>> call, Throwable t) {
                //Make a Refund
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed! :(", Toast.LENGTH_SHORT).show();

    }

    public void refundPayment(String pay_id)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    RazorpayClient razorpay = new RazorpayClient("rzp_test_dHMptmJ9G5HM1O", "RoEgKoG1N6VXsIZqvtz9YwAm");
                    Refund refund = razorpay.Payments.refund("pay_FWHkBzLuKr0VpO");

                } catch (RazorpayException e) {
                    e.printStackTrace();}
            }
        });
    }

    @Override
    public void onBackPressed() {
         Intent intent=new Intent(Settings.this,myCardsActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
         finish();
    }
}
