package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.support.design.widget.Snackbar;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
        setContentView(R.layout.activity_main_grid);
        setContentView(R.layout.activity_main_relative);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Here is more information",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
        final CheckBox checkBox=(CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked)-> {
            if (checkBox.isChecked()){
                Snackbar.make(checkBox, getResources().getString(R.string.snackbar_message_true), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.snackbar_setAction_message),
                                click -> buttonView.setChecked(!isChecked) ).show();
            }else{
                Snackbar.make(switch1, getResources().getString(R.string.snackbar_message_false), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
