package com.example.productviewer.interfaces;

import com.example.productviewer.model.Product;

import java.util.List;

public interface DatabaseFetching {

    void onDeliverAllProduct(List<Product> productList);
}
