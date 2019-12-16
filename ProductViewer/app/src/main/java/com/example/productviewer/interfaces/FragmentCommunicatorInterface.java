package com.example.productviewer.interfaces;

import com.example.productviewer.model.Product;

import java.util.List;

public interface FragmentCommunicatorInterface {
    void passProductList(List<Product> productList);
}
