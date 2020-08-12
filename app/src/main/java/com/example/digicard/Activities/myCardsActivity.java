package com.example.digicard.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.digicard.Database.AppController;
import com.example.digicard.R;
import com.example.digicard.model.card_attributes;
import com.example.digicard.model.myCardAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.GONE;


public class myCardsActivity extends AppCompatActivity {
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
        toolbar=findViewById(R.id.logo);
        setSupportActionBar(toolbar);
        searchView=findViewById(R.id.search_view);
        myInstance=AppController.getInstance();
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
                        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(editText1.getText().toString());
                        boolean b = m.find();

                        if (b)
                          editText1.setError("No Special Characters Allowed");
                        else{
                            Intent intent=new Intent(myCardsActivity.this, DashboardActivity.class);
                        intent.putExtra(getString(R.string.Key_cardName),editText1.getText().toString());
                        startActivity(intent);
                        alertDialog.dismiss();}}

                });
                alertDialog.show();

   }

   public void showGif()
   {
       RelativeLayout relativeLayout1=findViewById(R.id.myCardsRoot);
       RelativeLayout relativeLayout2=findViewById(R.id.parent1);
       RelativeLayout relativeLayout3=findViewById(R.id.parent2);
       relativeLayout2.setVisibility(GONE);
       relativeLayout3.setVisibility(View.VISIBLE);
       relativeLayout1.setBackgroundColor(getColor(R.color.white));
   }

    public void logout(View view){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cards_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {


        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
        else{
            super.onBackPressed();
        }

    }

}