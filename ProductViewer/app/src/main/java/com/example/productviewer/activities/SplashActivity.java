package com.example.productviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productviewer.R;
import com.example.productviewer.api.FetchHttpConnection;
import com.example.productviewer.api.FetchRetrofitConnection;

public class SplashActivity extends AppCompatActivity {

    public static TextView data;

    private String sharedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        data = (TextView) findViewById(R.id.fetched_data);

        Thread myThread = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(10000);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };


        myThread.start();


        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
        sharedValue = sharedPreferences.getString(MainActivity.COMMUNICATION_TYPE, "");

        if(sharedValue.equals("http")) {

            httpRun();

        }else if(sharedValue.equals("retrofit")){

            retrofitRun();

        }else{
            SharedPreferences.Editor editor= sharedPreferences.edit();

            editor.putString(MainActivity.COMMUNICATION_TYPE,"retrofit");

            Toast.makeText(this, "first time", Toast.LENGTH_SHORT).show();

        }


    }

    public void httpRun(){

        FetchHttpConnection fetchHttpConnection = new FetchHttpConnection();
        fetchHttpConnection.execute();
        Toast.makeText(this, "Change to HTTP", Toast.LENGTH_SHORT).show();

    }


    public void retrofitRun() {

        FetchRetrofitConnection fetchRetrofitConnection = new FetchRetrofitConnection();
        fetchRetrofitConnection.runFetchRetrofitConnection();
        Toast.makeText(this, "Change to Retrofit", Toast.LENGTH_SHORT).show();

    }
}
