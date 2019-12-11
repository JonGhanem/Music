package com.example.productviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productviewer.R;
import com.example.productviewer.api.CallbackProduct;
import com.example.productviewer.api.FetchHttpConnection;
import com.example.productviewer.api.FetchRetrofitConnection;
import com.example.productviewer.model.Product;

import java.util.List;

import static com.example.productviewer.App.COMMUNICATION_TYPE;
import static com.example.productviewer.App.SHARED_PREFERENCE;

public class SplashActivity extends AppCompatActivity {

    public TextView data;

    List<Product> calledProductList;

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


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        String sharedValue = sharedPreferences.getString(COMMUNICATION_TYPE, "");

        if (sharedValue.equals("http")) {

            httpRun();

        } else if (sharedValue.equals("retrofit")) {

            retrofitRun();

        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(COMMUNICATION_TYPE, "retrofit");

            editor.apply();

            Toast.makeText(this, "first time", Toast.LENGTH_SHORT).show();

        }


    }

    public void httpRun() {

        FetchHttpConnection fetchHttpConnection = new FetchHttpConnection();
        fetchHttpConnection.setCallBack(new CallbackProduct() {
            @Override
            public void callback(List<Product> productList) {


                data.setText(productList.get(0).getProduct().getDescription());

                Toast.makeText(getApplicationContext(), "HTTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failedCallback(String s) {

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            }
        }).execute();


    }


    public void retrofitRun() {

        FetchRetrofitConnection fetchRetrofitConnection = new FetchRetrofitConnection();
        fetchRetrofitConnection.runFetchRetrofitConnection(new CallbackProduct() {
            @Override
            public void callback(List<Product> productList) {
                calledProductList = productList;
                data.setText(calledProductList.get(0).getProduct().getName());
                Toast.makeText(SplashActivity.this, "Retrofit", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failedCallback(String s) {

                Toast.makeText(SplashActivity.this, "Retrofit failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
