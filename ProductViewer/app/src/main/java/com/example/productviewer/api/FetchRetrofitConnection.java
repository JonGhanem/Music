package com.example.productviewer.api;

import android.os.AsyncTask;
import android.util.Log;

import com.example.productviewer.activities.SplashActivity;
import com.example.productviewer.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRetrofitConnection  {

    private JsonHolderApi jsonHolderApi;

    public void runFetchRetrofitConnection (){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nweave.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonHolderApi = retrofit.create(JsonHolderApi.class);

        Call<List<Product>> call = jsonHolderApi.getPoducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (!response.isSuccessful()) {
                    SplashActivity.data.setText("code: " + response.code());
                    return;
                }

                List<Product> productList = response.body();

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                SplashActivity.data.setText(t.getMessage());
            }
        });

    }

}
