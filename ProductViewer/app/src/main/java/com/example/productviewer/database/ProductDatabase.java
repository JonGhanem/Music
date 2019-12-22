package com.example.productviewer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.productviewer.App;
import com.example.productviewer.interfaces.DatabaseFetching;
import com.example.productviewer.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase extends SQLiteOpenHelper {

    public ProductDatabase(@Nullable Context context) {
        super(context, App.DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(App.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(App.DROP_TABLE);
        onCreate(db);
    }

    public void insertData(List<Product> product) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("11", "insertData: " + product.size());
        for (int i = 0; i < product.size(); i++) {
            contentValues.put(App.COL_2, product.get(i).getProduct().getName());
            contentValues.put(App.COL_3, product.get(i).getProduct().getPrice());
            contentValues.put(App.COL_4, product.get(i).getProduct().getDescription());
            contentValues.put(App.COL_5, product.get(i).getProduct().getImageUrl());
//            contentValues.put(App.COL_6, product.get(i).getProduct().getId());
            Log.d("111", "insertData: " + contentValues.get(App.COL_2) + "\n");
            db.insert(App.TABLE_NAME, null, contentValues);
        }

        db.close();
    }

    // change by id
    public boolean updateData(String id, String productName, String productPrice, String productDescription, String productImageUrl) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(App.COL_1, id);
        contentValues.put(App.COL_2, productName);
        contentValues.put(App.COL_3, productPrice);
        contentValues.put(App.COL_4, productDescription);
        contentValues.put(App.COL_5, productImageUrl);
        db.update(App.TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(App.TABLE_NAME, "ID = ?", new String[]{id});
    }

    public void fetchProducts(DatabaseFetching databaseFetching) {
        ProductFetcher productFetcher = new ProductFetcher(databaseFetching, this.getWritableDatabase());
        productFetcher.start();
    }


    public class ProductFetcher extends Thread {

        private DatabaseFetching fetching;
        private SQLiteDatabase mDatabase;


        public ProductFetcher(DatabaseFetching databaseFetching, SQLiteDatabase db) {
            this.fetching = databaseFetching;
            this.mDatabase = db;
        }

        @Override
        public void run() {
            Cursor cursor = mDatabase.rawQuery(App.SELECT_ALL_PRODUCTS, null);

            final List<Product> productList = new ArrayList<>();

            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        Product.ProductBean productBean = new Product.ProductBean();

                        productBean.setName(cursor.getString(cursor.getColumnIndex(App.COL_2)));
                        productBean.setPrice(cursor.getString(cursor.getColumnIndex(App.COL_3)));
                        productBean.setDescription(cursor.getString(cursor.getColumnIndex(App.COL_4)));
                        productBean.setImageUrl(cursor.getString(cursor.getColumnIndex(App.COL_5)));

                        Product mproduct = new Product();
                        mproduct.setProduct(productBean);

                        productList.add(mproduct);

                    } while (cursor.moveToNext());

                }
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fetching.onDeliverAllProduct(productList);
                    }
                });
            }
        }
    }
}
