package com.example.productviewer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressLint("Registered")
public class App extends Application {

    public static final String SHARED_PREFERENCE = "shared_preference";
    public static final String COMMUNICATION_TYPE = "communication_type";

    //database
    public static final String DATABASE_NAME = "product_Database";
    public static final String TABLE_NAME = "product_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PRODUCT_NAME";
    public static final String COL_3 = "PRODUCT_PRICE";
    public static final String COL_4 = "PRODUCT_DESCRIPTION";
    public static final String COL_5 = "PRODUCT_IMAGE_URL";
    public static final String COL_6 = "PRODUCT_ID";

// SQL Statments
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "" +
            " ( " + COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " TEXT, " +
            COL_5 + " TEXT )" ;

    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String SELECT_ALL_PRODUCTS = " SELECT * FROM " + TABLE_NAME;

}
