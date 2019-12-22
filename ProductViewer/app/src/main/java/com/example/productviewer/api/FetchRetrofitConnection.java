package com.example.productviewer.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.productviewer.activities.MainActivity;
import com.example.productviewer.database.ProductDatabase;
import com.example.productviewer.interfaces.ProductCallbackInterface;
import com.example.productviewer.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRetrofitConnection {

    public void runFetchRetrofitConnection(final ProductCallbackInterface productcallback, final Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nweave.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolderApi jsonHolderApi = retrofit.create(JsonHolderApi.class);

        Call<List<Product>> call = jsonHolderApi.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,@NonNull Response<List<Product>> response) {


                List<Product> productList = response.body();
                if (productList != null) {

                    productcallback.successCallback((ArrayList<Product>) productList);
                    Log.d("ay7aga", "onResponse: ");
//                    ProductDatabase productDatabase = new ProductDatabase(context);
//                    productDatabase.insertData(productList);


                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,@NonNull Throwable t) {
                Log.d("ay7aga", "onFailure: ");
                productcallback.failedCallback(t.getLocalizedMessage());
            }
        });

    }

}
