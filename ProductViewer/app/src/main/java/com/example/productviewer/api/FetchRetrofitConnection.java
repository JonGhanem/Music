package com.example.productviewer.api;

import androidx.annotation.NonNull;

import com.example.productviewer.interfaces.ProductCallback;
import com.example.productviewer.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRetrofitConnection {

    public void runFetchRetrofitConnection(final ProductCallback productcallback) {

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

                    productcallback.successCallback(productList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,@NonNull Throwable t) {
                productcallback.failedCallback(t.getLocalizedMessage());
            }
        });

    }

}
