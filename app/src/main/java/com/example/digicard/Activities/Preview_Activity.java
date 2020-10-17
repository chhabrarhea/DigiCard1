package com.example.digicard.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.digicard.Api.ApiService;
import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.Api.Client;
import com.example.digicard.R;
import com.example.digicard.model.card_attributes;

import java.io.IOException;
import java.util.List;



public class Preview_Activity extends AppCompatActivity {
    card_attributes card1;
    TextView name;
    TextView company;
    TextView designation;
    TextView comma;
    TextView email;
    TextView number;
    TextView address;
    TextView about_me;
    CircleImageView dp;
    Button payment;
    GridLayout relative3;
    LinearLayout relative2;
    int pos;
    Boolean saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_);
        Toolbar toolbar = findViewById(R.id.mToolbar);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();


        card1 = intent.getParcelableExtra("Preview_Card");


        pos=intent.getIntExtra("position",-1);
        saved=intent.getBooleanExtra("isSaved",true);
        dp=findViewById(R.id.dp);
        name = findViewById(R.id.name);
        company = findViewById(R.id.company);
        comma = findViewById(R.id.comma);
        designation = findViewById(R.id.designation);
        relative2=findViewById(R.id.relative2);
        about_me = findViewById(R.id.about);
        number = findViewById(R.id.number);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        payment=findViewById(R.id.bank);
        relative3=findViewById(R.id.myGrid);
        setTitle(card1.getCardname());
        payment.setBackgroundColor((card1.getColor()));
        relative3.setColumnCount(6);
        relative3.setRowCount(1);

        Drawable drawable1=getDrawable(R.drawable.ic_email);
        drawable1.setTint(card1.getColor());
        Drawable drawable2=getDrawable(R.drawable.ic_phone);
        drawable2.setTint(card1.getColor());
        Drawable drawable3=getDrawable(R.drawable.ic_pin);
        drawable3.setTint(card1.getColor());

        name.setText(card1.getName());
        if (!card1.getCompany().equals("")) {
            company.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            company.setText(card1.getCompany());
        }
        if (!card1.getDesignation().equals("")) {
            designation.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            designation.setText(card1.getDesignation());
        }
        if(!card1.getCompany().equals("") && !card1.getDesignation().equals(""))
            comma.setVisibility(View.VISIBLE);

         if(card1.getImage_path()!=null && !card1.getImage_path().equals(""))
         {
             dp.setVisibility(View.VISIBLE);
             if(saved)
             Glide.with(Preview_Activity.this).load(card1.getImage_path()).asBitmap().centerCrop().into(dp);
             else
             {
                 byte[] data= Base64.decode(card1.getImage_path(),Base64.DEFAULT);
                 Bitmap b= BitmapFactory.decodeByteArray(data,0,data.length);
                 dp.setImageBitmap(b);
             }
         }
        if (!card1.getEmail().equals("")) {
            email.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            email.setVisibility(View.VISIBLE);
            email.setTextColor(Color.BLACK);
            email.setCompoundDrawablesWithIntrinsicBounds(drawable1,null,null,null);
            email.setText(card1.getEmail());
            email.setOnTouchListener(textTouch);

        }
        if (!card1.getAddress().equals("")) {
            address.setVisibility(View.VISIBLE);
            address.setText(card1.getAddress());
            address.setCompoundDrawablesWithIntrinsicBounds(drawable3,null,null,null);

        }
        if (!card1.getPhone().equals("")) {
            number.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            number.setTextColor(Color.BLACK);
            number.setOnTouchListener(textTouch);
            number.setVisibility(View.VISIBLE);
            number.setCompoundDrawablesWithIntrinsicBounds(drawable2,null,null,null);
            number.setText(card1.getPhone());

        }
        if (!card1.getAbout().equals("")) {
            about_me.setVisibility(View.VISIBLE);
            about_me.setText(card1.getAbout());

        }
        if (!card1.getGithub().equals("")) {

            relative3.setVisibility(View.VISIBLE);
            addView(R.drawable.github);
        }
        if (!card1.getLinkedin().equals("")) {
            relative3.setVisibility(View.VISIBLE);
            addView(R.drawable.linkedin);
        }

        if (!card1.getFacebook().equals("")) {

            relative3.setVisibility(View.VISIBLE);
            addView(R.drawable.facebook);
        }
        if (!card1.getTwitter().equals("")) {
            relative3.setVisibility(View.VISIBLE);
            addView(R.drawable.twitter);
        }
        if (!card1.getYoutube().equals("")) {

            relative3.setVisibility(View.VISIBLE);
            addView(R.drawable.utube);
        }
        if (!card1.getInstagram().equals("")) {
            relative3.setVisibility(View.VISIBLE);
            addView((R.drawable.ic_insta));
        }
        if (!card1.getAccount().equals("") ||!card1.getGst().equals("") || !card1.getUpi().equals("") )  { payment.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(saved){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;}
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.save)
        {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi!Please click on the link below to view my business card.\n" + Html.fromHtml(getString(R.string.share_domain) + "\n" +card1.getId()+ "\n Made with Digicard"));
                sendIntent.setType("text/html");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
        }
        else if (item.getItemId()==R.id.delete)
        {
                ApiService apiService= Client.getClient().create(ApiService.class);
                apiService.deletecard(card1.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                                AppController myInstance=AppController.getInstance(Preview_Activity.this);
                                List<card_attributes> cards=myInstance.getMyCards();
                                cards.remove(pos);
                                Intent intent=new Intent(Preview_Activity.this,myCardsActivity.class);
                                myInstance.setMyCards(cards);
                                startActivity(intent);
                                finish();
                        }
                        else
                            Toast.makeText(Preview_Activity.this, "Error Occured, Try Again!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Preview_Activity.this, "Error Occured, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });


        }

        return false;
    }

    public void bankDetails(View view)
    {
    AlertDialog.Builder alert=new AlertDialog.Builder(Preview_Activity.this);
    alert.setTitle("Bank Details").setMessage("UPI ID: "+card1.getUpi()+"\nGST: "+card1.getGst()+"\nAccount Number: "+card1.getAccount())
            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

    }


    @Override
    public void onBackPressed() {
        if(saved){
        Intent intent=new Intent(Preview_Activity.this,myCardsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);}
        else
        {
             super.onBackPressed();
             finish();
        }
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getTag().toString())
            {
                case(""+R.drawable.ic_insta):
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(card1.getInstagram()));
                    startActivity(intent);
                    break;
                case(""+R.drawable.linkedin):

                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(card1.getLinkedin()));
                    startActivity(intent);
                    break;

                case(""+R.drawable.twitter):
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(card1.getTwitter()));
                    startActivity(intent);
                    break;
                case(""+R.drawable.utube):
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(card1.getYoutube()));
                    startActivity(intent);
                    break;
                case(""+R.drawable.facebook):
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(card1.getFacebook()));
                    startActivity(intent);
                    break;
                case(""+R.drawable.github):
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(card1.getGithub()));
                    startActivity(intent);
                    break;
            }
            }
        };


    public void addView(int drawable)
    {
        ImageView oImageView = new ImageView(this);
        oImageView.setImageResource(drawable);
        oImageView.setOnClickListener(clickListener);
        oImageView.setTag(""+drawable);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
        param.height = 50;
        param.width =  50;
        param.setMargins(25,5,25,25);
        param.setGravity(Gravity.CENTER);
        oImageView.setLayoutParams (param);
        oImageView.setOnTouchListener(imageTouch);
        relative3.addView(oImageView);

    }

    public void composeEmail(View view)
    {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, card1.getEmail());
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email Client "));
    }
    public void getDialer(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + card1.getPhone()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

View.OnTouchListener textTouch=new View.OnTouchListener() {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final TextView textView=(TextView) view;
        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                textView.setTextColor(card1.getColor());
                break;
            case MotionEvent.ACTION_UP:
                textView.setTextColor(Color.BLACK);
                break;
        }
        return false;
    }};

    View.OnTouchListener imageTouch= new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final ImageView imageView=(ImageView) view;
            switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    imageView.setColorFilter(card1.getColor());
                    break;
                case MotionEvent.ACTION_UP:
                    imageView.setColorFilter(getResources().getColor(R.color.gray));
                    break;
            }
            return false;
        }
    };

    }
