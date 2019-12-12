package com.example.productviewer.api;

import com.example.productviewer.model.Product;

import java.util.List;

public interface ProductCallback {
    void successCallback(List<Product> productList);
    void failedCallback(String s);
}
