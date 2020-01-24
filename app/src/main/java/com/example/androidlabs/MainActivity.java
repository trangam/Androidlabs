package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main_linear);
        //setContentView(R.layout.activity_main_grid);
        setContentView(R.layout.activity_main_relative);
        Button button= findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_message) ,
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
        CheckBox checkBox=findViewById(R.id.checkBox) ;
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkBox.isChecked()) {
                Snackbar.make(checkBox, getResources().getString(R.string.snackbar__true), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.action_message),
                                click -> buttonView.setChecked(!isChecked)).show();
            } else {
                Snackbar.make(checkBox, getResources().getString(R.string.snackbar_false), Snackbar.LENGTH_LONG).
                        setAction(getResources().getString(R.string.action_message),
                                click -> buttonView.setChecked(!isChecked)).show();
            }
        });

        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener((buttonView,isChecked)->  {
                    if (switch1.isChecked()){
                        Snackbar.make(switch1, getResources().getString(R.string.snackbar__true), Snackbar.LENGTH_LONG)
                                .setAction(getResources().getString(R.string.action_message),
                                        click -> buttonView.setChecked(!isChecked) ).show();
                    }
                    else{
                        Snackbar.make(switch1, getResources().getString(R.string.snackbar_false),
                                Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.action_message),
                                click -> buttonView.setChecked(!isChecked) ).show();
                    }
                }

        );
    }
}
