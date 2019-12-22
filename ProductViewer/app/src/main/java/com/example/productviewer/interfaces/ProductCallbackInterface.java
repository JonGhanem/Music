package com.example.productviewer.interfaces;

import com.example.productviewer.model.Product;

import java.util.ArrayList;

public interface ProductCallbackInterface {
    // sending ArrayList from Gson to MainActivity
    void successCallback(ArrayList<Product> productList);
    void failedCallback(String s);
}
