package com.example.productviewer.api;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.productviewer.activities.SplashActivity;
import com.example.productviewer.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRetrofitConnection {

    public void runFetchRetrofitConnection(final CallbackProduct callbackProduct) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nweave.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolderApi jsonHolderApi = retrofit.create(JsonHolderApi.class);

        Call<List<Product>> call = jsonHolderApi.getPoducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,@NonNull Response<List<Product>> response) {


                List<Product> productList = response.body();
                if (productList != null) {

                    callbackProduct.callback(productList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,@NonNull Throwable t) {
                callbackProduct.failedCallback(t.getLocalizedMessage());
            }
        });

    }

}
