package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    TextView emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton imageButton = findViewById(R.id.imageButton2);
        emailEditText= findViewById(R.id.Text4);

        imageButton .setOnClickListener(click -> {
            dispatchTakePictureIntent();
        });
        Intent dataSent = getIntent();
        emailEditText.setText(dataSent.getStringExtra("EMAIL"));
       Log.e(ACTIVITY_NAME,"In onCreate()") ;

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageButton imageButton = findViewById(R.id.imageButton2);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
            Log.e(ACTIVITY_NAME,"In onActivityResult()") ;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In onStart()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"In onResume()") ;
    }
@Override
    protected void onStop(){
        super.onStop();
        Log.e(ACTIVITY_NAME,"In onStop()") ;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"In onDestroy()") ;
    }

}
