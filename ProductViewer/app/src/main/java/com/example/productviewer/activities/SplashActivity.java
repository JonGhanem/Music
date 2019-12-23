package com.example.productviewer.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productviewer.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
//        checkConnectionMethod();
    }

//    private void checkConnectionMethod() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
//        String sharedValue = sharedPreferences.getString(COMMUNICATION_TYPE, "");
//
//        if (sharedValue.equals("http")) {
//            httpRun();
//
//        } else if (sharedValue.equals("retrofit")) {
//            retrofitRun();
//
//        } else {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(COMMUNICATION_TYPE, "retrofit");
//            editor.apply();
//            Toast.makeText(this, "first time", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void httpRun() {
//
//        FetchHttpConnection fetchHttpConnection = new FetchHttpConnection();
//        fetchHttpConnection.setCallBack(new ProductCallbackInterface() {
//            @Override
//            public void successCallback(ArrayList<Product> productList) {
//                loadMainActivity("HTTP", productList);
//            }
//
//            @Override
//            public void failedCallback(String s) {
//                displayToast(s);
//            }
//        }).execute();
//    }
//
//
//    public void retrofitRun() {
//
//        FetchRetrofitConnection fetchRetrofitConnection = new FetchRetrofitConnection();
//        fetchRetrofitConnection.runFetchRetrofitConnection(new ProductCallbackInterface() {
//            @Override
//            public void successCallback(ArrayList<Product> productList) {
//
//                loadMainActivity("Retrofit", productList);
//            }
//
//            @Override
//            public void failedCallback(String s) {
//
//                displayToast("Retrofit failed");
//            }
//        });
//    }
//
//    private void loadMainActivity(String messageToast, List<Product> productList) {
//
//        Intent intent = new Intent(this,MainActivity.class);
//
//        displayToast(messageToast);
//        intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) productList);
//        startActivity(intent);
//        finish();
//    }
//
//    private void displayToast(String messageToast) {
//        Toast.makeText(SplashActivity.this, messageToast, Toast.LENGTH_SHORT).show();
//    }
}
