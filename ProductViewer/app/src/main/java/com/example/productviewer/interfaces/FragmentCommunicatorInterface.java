package com.example.productviewer.interfaces;

import com.example.productviewer.model.Product;

import java.util.List;

public interface FragmentCommunicatorInterface {
    // sending List of products from Main Activity to different Fragments
    void passProductList(List<Product> productList);
}
