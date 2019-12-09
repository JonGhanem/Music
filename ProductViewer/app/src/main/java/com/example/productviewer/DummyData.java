package com.example.productviewer;

public class DummyData {

    private String productName;
    private String productDetails;

    public  DummyData (String name, String details){
        this.productName = name;
        this.productDetails = details;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }
}
