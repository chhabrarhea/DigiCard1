package com.example.digicard.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digicard.Api.ApiService;

import com.example.digicard.LocalDatabase.AppController;
import com.example.digicard.R;
import com.example.digicard.Api.Client;
import com.example.digicard.model.CardPostResponse;
import com.example.digicard.model.card_attributes;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;


public class DashboardActivity extends AppCompatActivity {
    Toolbar toolbar;
    AlertDialog.Builder alb;
    View custom_alert;
    Button ok;
    Button cancel;
    AlertDialog alertDialog;
    public static card_attributes card;
    public static Map<String, Object> postCard;    //For POST request
    private View root;
    private Long time;
    TextView Cardname;
    EditText editText2;
    EditText editText3;
    EditText editText1;
    String line1;
    String line2;
    String line3;
    AppController myInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        card = new card_attributes();
        postCard = new HashMap<String, Object>();
        root=findViewById(R.id.dashboardRoot);
        myInstance=AppController.getInstance(DashboardActivity.this);
        card.setCardname(getIntent().getStringExtra(getString(R.string.Key_cardName)));
        Cardname = findViewById(R.id.CardName);
        Cardname.setText(card.getCardname());
        card.setUsername(myInstance.getUsername());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.getMenu().findItem(R.id.preview).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.preview:
                        Intent intent1 = new Intent(DashboardActivity.this, Preview_Activity.class);
                        intent1.putExtra("Preview_Card",card);
                        intent1.putExtra("isSaved",false);
                        startActivity(intent1);
                        break;
                    case R.id.save:
                            save();
                        break;

                    case R.id.PickColor:
                        colorPick();
                        break;

                    case R.id.Reset:
                        card=new card_attributes();
                        postCard=new HashMap<>();
                        Toast.makeText(DashboardActivity.this,"Card Reset",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


    }

    public void setName(View v) {
        alb = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog = alb.create();
        setCustomView();
        editText3.setVisibility(View.GONE);
        toolbar.setTitle("Add Bio");
        editText1.setHint("Name");
        editText2.setHint("About Me/Skills");
        editText1.setText(card.getName());
        editText2.setText(card.getAbout());
        editText2.setSelection(editText2.getText().length());
        editText1.setSelection(editText1.getText().length());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
                alertDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setName(editText1.getText().toString());
                card.setAbout(editText2.getText().toString());
                alertDialog.dismiss();
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
            }
        });
        alertDialog.setView(custom_alert);
        alertDialog.setOnCancelListener(cancelListener);
        alertDialog.show();

    }

    public void setBio(View v) {
        Intent intent = new Intent(DashboardActivity.this, get_photo_Activity.class);
        startActivity(intent);
    }

    public void setNumber(View v) {
        alb = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog = alb.create();
        final View numberPicker = (this.getLayoutInflater().inflate(R.layout.number_dialog, null));
        final EditText editText1 = numberPicker.findViewById(R.id.custom_editText1);
        final CountryCodePicker cpp = numberPicker.findViewById(R.id.countryCodeHolder);
        cpp.registerCarrierNumberEditText(editText1);
        ok = numberPicker.findViewById(R.id.custom_ok);
        cancel = numberPicker.findViewById(R.id.custom_cancel);
        editText1.setHint("Number");
        if(!card.getPhone().equals(""))
        cpp.setFullNumber(card.getPhone());
        editText1.setSelection(editText1.getText().length());
        editText1.setInputType(InputType.TYPE_CLASS_PHONE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                ((ViewGroup)numberPicker.getParent()).removeView(numberPicker);

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setPhone(cpp.getFormattedFullNumber());
                alertDialog.dismiss();
                ((ViewGroup)numberPicker.getParent()).removeView(numberPicker);
            }
        });
        alertDialog.setView(numberPicker);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alertDialog.dismiss();
                ((ViewGroup)numberPicker.getParent()).removeView(numberPicker);
            }
        });

        alertDialog.show();



    }

    public void setEmail(View v) {
        alb = new AlertDialog.Builder(DashboardActivity.this);
        final AlertDialog alertDialog = alb.create();
        setCustomView();
        alertDialog.setView(custom_alert);
        editText1.setVisibility(View.GONE);
        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText3.setVisibility(View.GONE);
        toolbar.setTitle("Add Email");
        editText2.setHint("Email");
        editText2.setText(card.getEmail());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmail(editText2.getText().toString()))
                {
                    card.setEmail(editText2.getText().toString());
                    alertDialog.dismiss();
                }
                else if(editText2.getText().toString().equals(""))
                    alertDialog.dismiss();
                else
                    editText2.setError("Invalid Email");

            }
        });
        alertDialog.setOnCancelListener(cancelListener);
        alertDialog.show();
    }

    public void setCompany(View v) {
        alb = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog = alb.create();
        setCustomView();
        toolbar.setTitle("Add Work Details");
        editText2.setText(card.getDesignation());
        editText1.setText(card.getCompany());
        editText1.setSelection(editText1.getText().length());
        editText2.setSelection(editText2.getText().length());
        editText1.setHint("Company");
        editText2.setHint("Designation");
        editText3.setVisibility(View.GONE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setCompany(editText1.getText().toString());
                card.setDesignation(editText2.getText().toString());
                alertDialog.dismiss();
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
            }
        });
        alertDialog.setView(custom_alert);
        alertDialog.setOnCancelListener(cancelListener);
        alertDialog.show();

    }

    public void setSocial(View v) {
        Intent intent = new Intent(DashboardActivity.this, socialmedia_activity.class);
        startActivity(intent);
    }

    public void setBank(View v) {
        alb = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog = alb.create();
        setCustomView();
        toolbar.setTitle("Add Bank Details");
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText3.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText1.setText(card.getAccount());
        editText2.setText(card.getUpi());
        editText3.setText(card.getGst());
        editText1.setSelection(editText1.getText().length());
        editText2.setSelection(editText2.getText().length());
        editText3.setSelection(editText3.getText().length());
        editText1.setHint("Account Number");
        editText2.setHint("UPI Number");
        editText3.setHint("GST Number");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setAccount(editText1.getText().toString());
                card.setGst(editText3.getText().toString());
                card.setUpi(editText2.getText().toString());
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(custom_alert);
        alertDialog.setOnCancelListener(cancelListener);
        alertDialog.show();
    }

    public void setAddress(View v) {
        alb = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog = alb.create();
        setCustomView();
        alertDialog.setView(custom_alert);
        toolbar.setTitle("Add Address");
        editText1.setHint("Line 1");
        editText2.setHint("Line 2");
        editText3.setHint("Line 3");
        if(line1!=null)
            editText1.setText(line1);
        if(line2!=null)
            editText2.setText(line2);
        if(line3!=null)
            editText3.setText(line3);
        editText1.setSelection(editText1.getText().length());
        editText2.setSelection(editText2.getText().length());
        editText3.setSelection(editText3.getText().length());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (custom_alert!=null)
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1=editText1.getText().toString();
                line2=editText2.getText().toString();
                line3=editText3.getText().toString();
                if(!line1.equals("") && !line2.equals("") && !line3.equals(""))
                    card.setAddress(line1+", "+line2+", "+line3);
                else if (!line1.equals("") && !line2.equals(""))
                    card.setAddress(line1+", "+line2);
                else if(!line1.equals(""))
                    card.setAddress(line1);
                if (!card.getAddress().equals(""))
                    postCard.put("address", card.getAddress());
                ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
                alertDialog.dismiss();

            }
        });
        alertDialog.setOnCancelListener(cancelListener);
        alertDialog.show();
    }

    public void save() {
        if(card.getImage_path()!=null && !card.getImage_path().equals(""))
        {
            Toast.makeText(DashboardActivity.this, "saving", Toast.LENGTH_SHORT).show();
            postCard.put("image_path",card.getImage_path());
        }
        if (!card.getGst().equals("")) {
            postCard.put("gst", card.getGst());
        }
        if (!card.getAccount().equals("")) {
            postCard.put("account", card.getAccount());
        }
        if (!card.getUpi().equals("")) {
            postCard.put("upi", card.getUpi());
        }
        if (!card.getDesignation().equals(""))
            postCard.put("designation", card.getDesignation());
        if (!card.getCompany().equals(""))
            postCard.put("company", card.getCompany());

        postCard.put("color",card.getColor());
        postCard.put("cardname",card.getCardname());
        postCard.put("username",card.getUsername());
        if(!card.getEmail().equals(""))
            postCard.put("email", card.getEmail());
        if (!card.getPhone().equals("")) {
            postCard.put("phone", card.getPhone());
        }
        if (!card.getAbout().equals(""))
            postCard.put("about", card.getAbout());
        if (!card.getName().equals("")) {
            postCard.put("name", card.getName());
        }
        ApiService apiService = Client.getClient().create(ApiService.class);
        apiService.saveCard(postCard).enqueue(new Callback<List<CardPostResponse>>() {
            @Override
            public void onResponse(Call<List<CardPostResponse>> call, Response<List<CardPostResponse>> response) {
                if (response.isSuccessful()) {
                        card.setImage_path(response.body().get(0).getImage_path());
                        card.setId(response.body().get(0).getId());
                        List<card_attributes> temp=myInstance.getMyCards();
                        temp.add(card);
                        myInstance.setMyCards(temp);
                        Intent intent=new Intent(DashboardActivity.this,Preview_Activity.class);
                        intent.putExtra("Preview_Card",card);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                } else {
                    Toast.makeText(DashboardActivity.this, "Error Occured,Try Again.", Toast.LENGTH_LONG).show();
                    try {
                        Log.i("saving error:", " " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CardPostResponse>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error Occured, "+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("saving error:", " " + t.getMessage());
            }
        });
    }



    @Override
    public void onBackPressed() {
            if (time != null && time + 3000 > System.currentTimeMillis()) {
                Intent intent = new Intent(DashboardActivity.this, myCardsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(root, "Card not saved, press back again to exit.", Snackbar.LENGTH_LONG)
                        .setAction("Save", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                save();
                            }
                        }).show();
            }
            time = System.currentTimeMillis();

    }
    
    public void colorPick()
    {
        ColorPickerDialogBuilder
                .with(DashboardActivity.this)
                .setTitle("Choose a color")
                .showAlphaSlider(false)
                .initialColor(card.getColor())
                .showBorder(true)
                .showLightnessSlider(true)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(DashboardActivity.this, ""+Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        card.setColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    AlertDialog.OnCancelListener cancelListener=new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if(custom_alert!=null)
            ((ViewGroup)custom_alert.getParent()).removeView(custom_alert);
            alertDialog.dismiss();
        }
    };

    public void setCustomView(){
        custom_alert = this.getLayoutInflater().inflate(R.layout.dialog_3, null);
        editText1 = custom_alert.findViewById(R.id.custom_editText1);
        editText2 = custom_alert.findViewById(R.id.custom_editText2);
        editText3 = custom_alert.findViewById(R.id.custom_editText3);
        ok = custom_alert.findViewById(R.id.custom_ok);
        toolbar = custom_alert.findViewById(R.id.custom_toolbar);
        cancel = custom_alert.findViewById(R.id.custom_cancel);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null || target.equals(""))
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onDestroy() {
        if(alertDialog!=null)
            alertDialog.dismiss();
        if(card!=null)
            card=null;
        if(postCard!=null)
            postCard=null;
        super.onDestroy();
    }

}


