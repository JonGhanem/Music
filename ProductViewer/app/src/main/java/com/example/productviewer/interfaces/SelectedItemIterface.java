package com.example.productviewer.interfaces;

import com.example.productviewer.model.Product;

public interface SelectedItemIterface {
    // Sending Selected Item from Adapter to Details Fragment
    void onItemClickListener(Product product);
}
