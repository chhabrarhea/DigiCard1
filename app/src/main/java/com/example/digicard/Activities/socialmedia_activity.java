package com.example.digicard.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.digicard.R;
import static com.example.digicard.Activities.DashboardActivity.card;
import static com.example.digicard.Activities.DashboardActivity.postCard;


public class socialmedia_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialmedia_activity);
    }

    public void discard(View view){}
    public void instagram(View view){
        AlertDialog.Builder alb= new AlertDialog.Builder(socialmedia_activity.this);
        final AlertDialog alertDialog= alb.create();
        LayoutInflater inflater=this.getLayoutInflater();
        View custom_alert= (inflater.inflate(R.layout.dialog_1,null));
        alertDialog.setView(custom_alert);
        final EditText editText1=custom_alert.findViewById(R.id.custom_editText1);
        Button ok=custom_alert.findViewById(R.id.custom_ok);
        Toolbar toolbar=custom_alert.findViewById(R.id.custom_toolbar);
        Button cancel=custom_alert.findViewById(R.id.custom_cancel);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Instagram");
        if(!card.getInstagram().equals(""))
            editText1.setText(card.getInstagram());
        else
            editText1.setText("https://www.instagram.com/");
        editText1.setSelection(editText1.getText().length());
        ok.setText("OK");
        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setInstagram(editText1.getText().toString());
                if(!card.getInstagram().equals(""))
                    postCard.put("instagram",card.getInstagram());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void facebook(View view){
        AlertDialog.Builder alb= new AlertDialog.Builder(socialmedia_activity.this);
        final AlertDialog alertDialog= alb.create();
        LayoutInflater inflater=this.getLayoutInflater();
        View custom_alert= (inflater.inflate(R.layout.dialog_1,null));
        alertDialog.setView(custom_alert);
        final EditText editText1=custom_alert.findViewById(R.id.custom_editText1);
        Button ok=custom_alert.findViewById(R.id.custom_ok);
        Toolbar toolbar=custom_alert.findViewById(R.id.custom_toolbar);
        Button cancel=custom_alert.findViewById(R.id.custom_cancel);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Facebook");

        if(!card.getFacebook().equals(""))
            editText1.setText(card.getFacebook());
        else
            editText1.setText("https://www.facebook.com/");
        editText1.setSelection(editText1.getText().length());
        ok.setText("OK");

        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setFacebook(editText1.getText().toString());
                if(!card.getFacebook().equals(""))
                    postCard.put("facebook",card.getFacebook());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void youtube(View view){
        AlertDialog.Builder alb= new AlertDialog.Builder(socialmedia_activity.this);
        final AlertDialog alertDialog= alb.create();
        LayoutInflater inflater=this.getLayoutInflater();
        View custom_alert= (inflater.inflate(R.layout.dialog_1,null));
        alertDialog.setView(custom_alert);
        final EditText editText1=custom_alert.findViewById(R.id.custom_editText1);
        Button ok=custom_alert.findViewById(R.id.custom_ok);
        Toolbar toolbar=custom_alert.findViewById(R.id.custom_toolbar);
        Button cancel=custom_alert.findViewById(R.id.custom_cancel);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Youtube Channel");

        if(!card.getYoutube().equals(""))
            editText1.setText(card.getGithub());
        else
            editText1.setText("https://youtube.com/");
        editText1.setSelection(editText1.getText().length());
        ok.setText("OK");

        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setYoutube(editText1.getText().toString());
                if(!card.getYoutube().equals(""))
                    postCard.put("youtube",card.getYoutube());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
    public void linkedin(View view){
        AlertDialog.Builder alb=new AlertDialog.Builder(socialmedia_activity.this);
        final AlertDialog alertDialog=alb.create();
        View custom=getLayoutInflater().inflate(R.layout.dialog_3,null);
        alertDialog.setView(custom);
        final EditText editText1=custom.findViewById(R.id.custom_editText1);
        final EditText editText2=custom.findViewById(R.id.custom_editText2);
        final EditText editText3=custom.findViewById(R.id.custom_editText3);
        editText3.setVisibility(View.GONE);
        editText1.setText("https://linkedin.com/in/");
        editText2.setText("https://linkedin.com/company/");
        Toolbar toolbar=custom.findViewById(R.id.custom_toolbar);
        toolbar.setTitle("Add Linkedin");
        if(!card.getLinkedin().equals(""))
            editText1.setText(card.getLinkedin());
        if(!card.getLinkedinBsn().equals(""))
            editText2.setText(card.getLinkedinBsn());
        editText1.setSelection(editText1.getText().length());
        editText2.setSelection(editText2.getText().length());
        Button ok=custom.findViewById(R.id.custom_ok);
        Button cancel=custom.findViewById(R.id.custom_cancel);
        cancel.setText("Cancel");
        ok.setText("OK");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setLinkedin(editText1.getText().toString());
                card.setLinkedinBsn(editText2.getText().toString());
                if(!card.getLinkedin().equals("") && card.getLinkedin()!=null)
                    postCard.put("linkedin",card.getLinkedin());
                if(!card.getLinkedinBsn().equals("") && card.getLinkedinBsn()!=null)
                    postCard.put("linkedin_bsn",card.getLinkedinBsn());
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
    public void github(View view){
        AlertDialog.Builder alb= new AlertDialog.Builder(socialmedia_activity.this);
        final AlertDialog alertDialog= alb.create();
        LayoutInflater inflater=this.getLayoutInflater();
        View custom_alert= (inflater.inflate(R.layout.dialog_1,null));
        alertDialog.setView(custom_alert);
        final EditText editText1=custom_alert.findViewById(R.id.custom_editText1);
        Button ok=custom_alert.findViewById(R.id.custom_ok);
        Toolbar toolbar=custom_alert.findViewById(R.id.custom_toolbar);
        Button cancel=custom_alert.findViewById(R.id.custom_cancel);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Github Profile");
        if(!card.getGithub().equals(""))
            editText1.setText(card.getGithub());
        else
            editText1.setText("https://github.com/");
        editText1.setSelection(editText1.getText().length());
        ok.setText("OK");
        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setGithub(editText1.getText().toString());
                if(!card.getGithub().equals(""))
                    postCard.put("github",card.getGithub());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void twitter(View view){
        AlertDialog.Builder alb= new AlertDialog.Builder(socialmedia_activity.this);
        final AlertDialog alertDialog= alb.create();
        LayoutInflater inflater=this.getLayoutInflater();
        View custom_alert= (inflater.inflate(R.layout.dialog_1,null));
        alertDialog.setView(custom_alert);
        final EditText editText1=custom_alert.findViewById(R.id.custom_editText1);
        Button ok=custom_alert.findViewById(R.id.custom_ok);
        Toolbar toolbar=custom_alert.findViewById(R.id.custom_toolbar);
        Button cancel=custom_alert.findViewById(R.id.custom_cancel);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Twitter Profile");
        if(!card.getTwitter().equals(""))
            editText1.setText(card.getTwitter());
        else
            editText1.setText("https://twitter.com/");
        editText1.setSelection(editText1.getText().length());
        ok.setText("OK");
        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setTwitter(editText1.getText().toString());
                if(!card.getTwitter().equals(""))
                    postCard.put("twitter",card.getTwitter());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}