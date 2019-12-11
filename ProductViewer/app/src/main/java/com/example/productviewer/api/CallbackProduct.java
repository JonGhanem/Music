package com.example.productviewer.api;

import com.example.productviewer.model.Product;

import java.util.List;

public interface CallbackProduct {
    public void callback (List<Product> productList);
    public void failedCallback (String s);
}
