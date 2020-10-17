package com.example.digicard.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.digicard.R;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static com.example.digicard.Activities.DashboardActivity.card;

public class get_photo_Activity extends AppCompatActivity {
    View root;
    Bitmap bitmap;
    ImageView image;
    Button discard;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photo);
        root=findViewById(R.id.getPhotoRoot);
        image=findViewById(R.id.custom_icon);
        setSupportActionBar((Toolbar) findViewById(R.id.custom_toolbar));
        discard=findViewById(R.id.custom_remove);
        save=findViewById(R.id.custom_ok);

        if(card.getImage_path()!=null && !card.getImage_path().equals(""))
        {
            byte data[]=Base64.decode(card.getImage_path(),Base64.DEFAULT);
            bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
            image.setImageBitmap(bitmap);
            image.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
            discard.setVisibility(View.VISIBLE);
        }

    }
    public void save(View view)  {
        if(bitmap!=null){
        ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        card.setImage_path(Base64.encodeToString(byteArrayVar, Base64.DEFAULT));
        discard.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);}
    }
    public void capture(View view){
        if (ContextCompat.checkSelfPermission(get_photo_Activity.this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 1888);
        }
        else{
            Dexter.withContext(get_photo_Activity.this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1888);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    if(permissionDeniedResponse.isPermanentlyDenied()){
                        Snackbar.make(root,"Change app setings to continue",Snackbar.LENGTH_LONG)
                                .setAction("Settings", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.parse("package:" + getPackageName()));
                                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                    else
                        Toast.makeText(get_photo_Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();
    }}
    public void upload(View view){
 if (ContextCompat.checkSelfPermission(get_photo_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
                else{
        Dexter.withContext(get_photo_Activity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                if(permissionDeniedResponse.isPermanentlyDenied())
                {

                   Snackbar.make(root,"Change app settings to continue",Snackbar.LENGTH_LONG)
                            .setAction("Settings", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.parse("package:" + getPackageName()));
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }).show();
                }
                else
                    Toast.makeText(get_photo_Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
                }
}
    public void discard(View view){
//        DashboardActivity.card.setPhoto(null);
        discard.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
        card.setImage_path(null);
        bitmap=null;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==1 || requestCode==1888) && resultCode == RESULT_OK && data != null) {

            try {

                if(requestCode==1) {
                    Uri uri = data.getData();

                    Glide.with(get_photo_Activity.this)
                     .load(uri)
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(80, 100) {
                                @Override
                                public void onResourceReady(Bitmap bit, GlideAnimation anim) {
                                    bitmap=bit;
                                    image.setImageBitmap(bit);
                                }
                            });
                }
                else {
                    bitmap = (Bitmap) (Objects.requireNonNull(data.getExtras())).get("data");
                    image.setImageBitmap(bitmap);
                }
                image.setVisibility(View.VISIBLE);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    else
    bitmap=null;}


}

