package com.example.productviewer.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.productviewer.utils.Constant;

import java.util.HashMap;

public class ContentProviderDb extends ContentProvider {

    public static final String PROVIDER_NAME ="com.example.productviewer.provider";
    public static final String URL = "content://"+ PROVIDER_NAME + "/products";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String ID = "ID";
    public static final String NAME = "PRODUCT_NAME";
    public static final String PRICE = "PRODUCT_PRICE";
    public static final String DESCRIPTION = "PRODUCT_DESCRIPTION";
    public static final String IMAGE = "PRODUCT_IMAGE_URL";

    static final int PRODUCTS = 1;

    private static HashMap<String, String> values;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCTS);

    }

    private ProductDatabase productDatabase;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        productDatabase = new ProductDatabase(context);

        db = productDatabase.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Constant.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on price
             */
            sortOrder = NAME;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    //match url
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)){
            /**
             * Get all product records (dir)
             * http://developer.android.com/reference/android/content/UriMatcher.html
             */
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.example.products";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = db.insert(Constant.TABLE_NAME, "", values);
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int count = 0;

        switch (uriMatcher.match(uri)){
            case PRODUCTS:
                count = db.delete(Constant.TABLE_NAME, selection, selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case PRODUCTS:
                count = db.update(Constant.TABLE_NAME, values, selection, selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
