package com.example.productviewer.activities;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.productviewer.database.ProductProvider;
import com.example.productviewer.utils.Constant;
import com.example.productviewer.utils.HelperClass;
import com.example.productviewer.R;
import com.example.productviewer.api.FetchHttpConnection;
import com.example.productviewer.api.FetchRetrofitConnection;
import com.example.productviewer.database.ProductDatabase;
import com.example.productviewer.fragment.AllProductsFragment;
import com.example.productviewer.fragment.ProductDetailsFragment;
import com.example.productviewer.interfaces.DatabaseFetching;
import com.example.productviewer.interfaces.ProductCallbackInterface;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.productviewer.utils.Constant.COMMUNICATION_TYPE;
import static com.example.productviewer.utils.Constant.SHARED_PREFERENCE;

public class MainActivity extends AppCompatActivity implements SelectedItemIterface, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<Product> mProductList;
    private ProductDatabase productDatabase = new ProductDatabase(this);
    private HelperClass helper = new HelperClass();
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        showDrawerToggle();

//        if (helper.isNetworkAvailable(this) && !helper.checkIfDbExists(this)) {
//            checkConnectionMethod();
//        } else {
        getData();
//        }

        if (savedInstanceState == null) {
            bundle.putParcelableArrayList("selected item", (ArrayList<? extends Parcelable>) mProductList);
            AllProductsFragment allProductsFragment = new AllProductsFragment();
            allProductsFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new AllProductsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_all_products);
        }
    }

    private void showDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void checkConnectionMethod() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        String sharedValue = sharedPreferences.getString(COMMUNICATION_TYPE, "");

        if (sharedValue.equals("http")) {
            httpRun();

        } else if (sharedValue.equals("retrofit")) {
            retrofitRun();

        } else {
            saveSharedPreference("retrofit");
            retrofitRun();
            displayToast("First Time");
        }
    }

    public void httpRun() {

        FetchHttpConnection fetchHttpConnection = new FetchHttpConnection();
        fetchHttpConnection.setCallBack(new ProductCallbackInterface() {
            @Override
            public void successCallback(ArrayList<Product> productList) {
                mProductList = productList;

                Log.d("check", "successCallback: ");
                displayToast("HTTP");
                productDatabase.insertData(productList);
                updateAllProductFragment(mProductList);

            }

            @Override
            public void failedCallback(String s) {
                displayToast(s);
            }
        }, this).execute();
    }

    public void retrofitRun() {

        FetchRetrofitConnection fetchRetrofitConnection = new FetchRetrofitConnection();
        fetchRetrofitConnection.runFetchRetrofitConnection(new ProductCallbackInterface() {
            @Override
            public void successCallback(ArrayList<Product> productList) {
                mProductList = productList;
                displayToast("Retrofit");
//                productDatabase.insertData(productList);
                addProvider(productList);
                updateAllProductFragment(mProductList);
            }

            @Override
            public void failedCallback(String s) {

                displayToast("Retrofit failed");

            }
        }, this);
    }

    private void updateAllProductFragment(List<Product> productList) {
        mProductList = productList;
//        if (!helper.checkIfDbExists(this)) {
//            productDatabase.insertData(mProductList);
//        }
        AllProductsFragment allProductsFragment = (AllProductsFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (allProductsFragment != null) {
            allProductsFragment.updateData(mProductList);
        }
    }

    private void displayToast(String messageToast) {
        Toast.makeText(this, messageToast, Toast.LENGTH_SHORT).show();
    }

    public void retrofitRetrieve(MenuItem item) {

        saveSharedPreference("retrofit");
        item.setChecked(true);
    }

    public void httpRetrieve(MenuItem item) {

        saveSharedPreference("http");
        item.setChecked(true);
    }

    public void saveSharedPreference(String s) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMMUNICATION_TYPE, s);
        editor.apply();
        Toast.makeText(this, "Change to " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.communication_method, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClickListener(Product product) {

        Log.d("item1", "onItemClickListener: " + product.getProduct().getName());
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected item", product);
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        productDetailsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment, productDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void getData() {
//        productDatabase.fetchProducts(productList -> {
//            mProductList = productList;
//            if (productList == null || productList.isEmpty()) {
//                if (helper.isNetworkAvailable(this)) {
//                    checkConnectionMethod();
//                } else {
//                    // No internet connection in first time to launch the app
//                }
//            } else {
//                updateAllProductFragment(productList);
//            }
//        });

        List<Product> productList = QueryGet();
                    mProductList = productList;
            if (productList == null || productList.isEmpty()) {
                if (helper.isNetworkAvailable(this)) {
                    checkConnectionMethod();
                } else {
                    // No internet connection in first time to launch the app
                }
            } else {
                updateAllProductFragment(productList);
            }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        ArrayList<Product> productSorted = new ArrayList<>(mProductList);


        switch (item.getItemId()) {
            case R.id.nav_all_products:
                changeFragment("All products", new ArrayList<>(mProductList));
                break;

            case R.id.nav_most_cheapest_products:
                Collections.sort(productSorted,
                        Comparator.comparing(Product::getProduct,
                                Comparator.comparingDouble(Product.ProductBean::getPrice)));
                changeFragment("the cheapest products", productSorted);
                break;

            case R.id.nav_most_expensive_products:
                Collections.sort(productSorted,
                        Comparator.comparing(Product::getProduct,
                                Comparator.comparingDouble(Product.ProductBean::getPrice).reversed()));
                changeFragment("The most expensive products", productSorted);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(String fragmentName, ArrayList<Product> productArrayList) {
        AllProductsFragment allProductsFragment = new AllProductsFragment();
        bundle.putParcelableArrayList("selected item", new ArrayList<>(productArrayList));
        bundle.putString("fragmentName", fragmentName);
        allProductsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, allProductsFragment)
                .commit();
    }

    public  List<Product>  QueryGet() {
        final List<Product> productListCursor = new ArrayList<>();
        // define content provider url to read from
        Uri products = Uri.parse(ProductProvider.URL);
        // get data ordered by name
        Cursor c = getContentResolver().query(products, null, null, null, ProductProvider.NAME);
        // move through all items
        if (c.moveToFirst()) {
            do {
                Product.ProductBean productBean = new Product.ProductBean();
                productBean.setName(c.getString(c.getColumnIndex(ProductProvider.NAME)));
                productBean.setPrice(c.getDouble(c.getColumnIndex(ProductProvider.PRICE)));
                productBean.setDescription(c.getString(c.getColumnIndex(ProductProvider.DESCRIPTION)));
                productBean.setImageUrl(c.getString(c.getColumnIndex(ProductProvider.IMAGE)));

                Product mproduct = new Product();
                mproduct.setProduct(productBean);

                productListCursor.add(mproduct);
            } while (c.moveToNext());

            Log.d("getdata", "QueryGet: ");
        }

        return productListCursor;
    }

    public void addProvider(List<Product> list) {
        // Add a new student record
        ContentValues values = new ContentValues();
        // insert value
        for (int i = 0; i < list.size(); i++) {
            values.put(ProductProvider.NAME,
                    list.get(i).getProduct().getName());

            values.put(ProductProvider.PRICE,
                    list.get(i).getProduct().getPrice());

            values.put(ProductProvider.DESCRIPTION,
                    list.get(i).getProduct().getDescription());

            values.put(ProductProvider.IMAGE,
                    list.get(i).getProduct().getImageUrl());
        }
        // define the play to insert the values in
        Uri uri = getContentResolver().insert(
                ProductProvider.CONTENT_URI, values);
        // display messages
        displayToast(uri.toString());
    }
}