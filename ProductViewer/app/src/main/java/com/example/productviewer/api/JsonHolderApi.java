package com.example.productviewer.api;

import com.example.productviewer.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonHolderApi {

    @GET("/wp-content/uploads/2012/09/featured.txt")
    Call<List<Product>> getProducts();
}
