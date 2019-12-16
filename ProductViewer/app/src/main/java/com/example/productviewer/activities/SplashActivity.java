package com.example.productviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productviewer.R;
import com.example.productviewer.interfaces.ProductCallback;
import com.example.productviewer.api.FetchHttpConnection;
import com.example.productviewer.api.FetchRetrofitConnection;
import com.example.productviewer.model.Product;

import java.util.ArrayList;
import java.util.List;

import static com.example.productviewer.App.COMMUNICATION_TYPE;
import static com.example.productviewer.App.SHARED_PREFERENCE;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Thread myThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(10000);
//
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        myThread.start();

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
        fetchHttpConnection.setCallBack(new ProductCallback() {
            @Override
            public void successCallback(List<Product> productList) {
                loadMainActivity("HTTP", productList);
            }

            @Override
            public void failedCallback(String s) {
                displayToast(s);
            }
        }).execute();
    }


    public void retrofitRun() {

        FetchRetrofitConnection fetchRetrofitConnection = new FetchRetrofitConnection();
        fetchRetrofitConnection.runFetchRetrofitConnection(new ProductCallback() {
            @Override
            public void successCallback(List<Product> productList) {
                loadMainActivity("Retrofit", productList);
            }

            @Override
            public void failedCallback(String s) {

                displayToast("Retrofit failed");
            }
        });
    }

    private void loadMainActivity(String messageToast, List<Product> productList) {

        Intent intent = new Intent(this,MainActivity.class);

        displayToast(messageToast);
        intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) productList);
        startActivity(intent);
        finish();
    }

    private void displayToast(String messageToast) {
        Toast.makeText(SplashActivity.this, messageToast, Toast.LENGTH_SHORT).show();
    }


}
