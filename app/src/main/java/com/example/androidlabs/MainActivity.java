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
        setContentView(R.layout.activity_main_linear);


        Button button= findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Here is more information",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
         CheckBox checkBox=findViewById(R.id.checkBox) ;
        checkBox.setOnCheckedChangeListener((buttonView, isChecked )-> {
                       if(checkBox.isChecked()){
                           Snackbar.make(checkBox, "Checkbox is on", Snackbar.LENGTH_LONG)
                                   .setAction("Undo",
                                           click -> buttonView.setChecked(!isChecked) ).show();
                       }
                       else{
                           Snackbar.make(checkBox, "Checkbox is off",
                                   Snackbar.LENGTH_LONG).setAction("Undo",
                                   click -> buttonView.setChecked(!isChecked) ).show();
                       }

                    });


        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener((buttonView,isChecked)->  {
            if (switch1.isChecked()){
                Snackbar.make(switch1, "Switch is on", Snackbar.LENGTH_LONG)
                        .setAction("Undo",
                                click -> buttonView.setChecked(!isChecked) ).show();
                }
                else{
                    Snackbar.make(switch1, "Switch is off",
                            Snackbar.LENGTH_LONG).setAction("Undo",
                            click -> buttonView.setChecked(!isChecked) ).show();
                }
                }

    );
    }
}

