package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private DrawerLayout mDrawerLayout;
private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout=findViewById(R.id.drawer_layout);
        mToggle= new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflate=getMenuInflater();
        inflate.inflate(R.menu.example_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_weather:
                Toast toast1 = Toast.makeText(getApplicationContext(), "item weather selected", Toast.LENGTH_SHORT);
                toast1.show();
                Intent weather_act= new Intent(this, WeatherForecast.class);
                startActivity(weather_act);
                return true;
            case R.id.item_chatPage:
                    Toast toast2 = Toast.makeText(getApplicationContext(), "item chat room selected", Toast.LENGTH_SHORT);
                    toast2.show();
                    Intent chatRoom_act=new Intent(this, ChatRoomActivity.class);
                    startActivity(chatRoom_act);
                return true;
            case R.id.item_login:
                Toast toast3 = Toast.makeText(getApplicationContext(), "item login selected", Toast.LENGTH_SHORT);
                toast3.show();
                Intent login_act=new Intent(this, ProfileActivity.class);
                startActivity(login_act);
                return true;
            case R.id.overflow:
                Toast toast4 = Toast.makeText(getApplicationContext(), "You clicked overflow", Toast.LENGTH_SHORT);
                toast4.show();
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_weather:
                Toast toast1 = Toast.makeText(getApplicationContext(), "item weather selected", Toast.LENGTH_SHORT);
                toast1.show();
                Intent weather_act= new Intent(this, WeatherForecast.class);
                startActivity(weather_act);
                break;
            case R.id.item_chatPage:
                Toast toast2 = Toast.makeText(getApplicationContext(), "item chat room selected", Toast.LENGTH_SHORT);
                toast2.show();
                Intent chatRoom_act=new Intent(this, ChatRoomActivity.class);
                startActivity(chatRoom_act);
                break;
            case R.id.item_login:
                Toast toast3 = Toast.makeText(getApplicationContext(), "item login selected", Toast.LENGTH_SHORT);
                toast3.show();
                Intent login_act=new Intent(this, ProfileActivity.class);
                startActivity(login_act);
               break;
            default:
                return super.onOptionsItemSelected(item);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}

