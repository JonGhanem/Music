package com.example.productviewer.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.productviewer.R;
import com.google.android.material.navigation.NavigationView;

import static com.example.productviewer.App.COMMUNICATION_TYPE;
import static com.example.productviewer.App.SHARED_PREFERENCE;

public class MainActivity extends AppCompatActivity {



    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.




        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_all_products, R.id.nav_most_cheapest_products,
                R.id.nav_most_expensive_products)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }





    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void retrofitRetrieve(MenuItem item) {

        SaveDate("retrofit");

        Toast.makeText(this, "Change to Retrofit", Toast.LENGTH_SHORT).show();
    }

    public void httpRetrieve(MenuItem item) {

        SaveDate("http");

        Toast.makeText(this, "Change to HTTP", Toast.LENGTH_SHORT).show();
    }

    public void SaveDate(String s){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(COMMUNICATION_TYPE,s);

        editor.apply();

    }
}
