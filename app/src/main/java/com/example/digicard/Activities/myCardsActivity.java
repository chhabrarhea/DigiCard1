package com.example.digicard.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.digicard.Api.ApiService;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.Api.Client;
import com.example.digicard.R;
import com.example.digicard.model.Login;
import com.example.digicard.model.card_attributes;
import com.example.digicard.model.myCardAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class myCardsActivity extends AppCompatActivity  {
RecyclerView recyclerView;
myCardAdapter adapter;
AlertDialog alertDialog;
Toolbar toolbar;
MaterialSearchView searchView;
ArrayList<card_attributes> list;
AppController myInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        String error=getIntent().getStringExtra("error");
        if(error!=null && !error.equals("")) {Toast.makeText(myCardsActivity.this, "Error loading cards.", Toast.LENGTH_SHORT).show(); }
        toolbar=findViewById(R.id.logo);
        setSupportActionBar(toolbar);
        searchView=findViewById(R.id.search_view);
        myInstance=AppController.getInstance(myCardsActivity.this);
        list=(ArrayList<card_attributes>) myInstance.getMyCards();
        adapter = new myCardAdapter(myCardsActivity.this, list);
        recyclerView = findViewById(R.id.myRecycle);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(myCardsActivity.this, 1));
        adapter.notifyDataSetChanged();
        if (list.size()==0) {
          showGif();
        }

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });




    }

    public void dashboard(View view)
   {
       if(myInstance.getPremium()==1 || myInstance.getMyCards().size()<2){
       AlertDialog.Builder alb=new AlertDialog.Builder(myCardsActivity.this);
                alertDialog= alb.create();
                LayoutInflater inflater=myCardsActivity.this.getLayoutInflater();
                View custom_alert= (inflater.inflate(R.layout.dialog_1,null));
                alertDialog.setView(custom_alert);
                final EditText editText1=custom_alert.findViewById(R.id.custom_editText1);
                Button ok=custom_alert.findViewById(R.id.custom_ok);
                Toolbar toolbar=custom_alert.findViewById(R.id.custom_toolbar);
                Button cancel=custom_alert.findViewById(R.id.custom_cancel);
                toolbar.setTitle("Add Card Name");
                editText1.setHint("Card Name");
                ok.setText("OK");
                cancel.setText("Cancel");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss(); }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!editText1.getText().toString().equals("")) {
                            Intent intent = new Intent(myCardsActivity.this, DashboardActivity.class);
                            intent.putExtra(getString(R.string.Key_cardName), editText1.getText().toString());
                            startActivity(intent);
                            alertDialog.dismiss();
                        } else
                            editText1.setError("Cannot be Empty");
                    } });
                alertDialog.show();}
       else
       {
           Toast.makeText(getApplicationContext(),"Go Premium and Enjoy Unlimited Cards!",Toast.LENGTH_SHORT).show();
       }

   }

   public void showGif()
   {
       RelativeLayout relativeLayout2=findViewById(R.id.parent1);
       RelativeLayout relativeLayout3=findViewById(R.id.parent2);
       relativeLayout2.setVisibility(GONE);
       relativeLayout3.setVisibility(View.VISIBLE);
   }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.cards_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        MenuItem item1 = menu.findItem(R.id.crown);
        if(myInstance.getPremium()==1)
            item1.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId()==R.id.set)
         {
             Intent intent=new Intent(myCardsActivity.this,Settings.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intent);
             finish();
         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
        else
            super.onBackPressed();
    }


}

