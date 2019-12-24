package com.example.productviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.productviewer.Helper;
import com.example.productviewer.R;
import com.example.productviewer.api.FetchHttpConnection;
import com.example.productviewer.api.FetchRetrofitConnection;
import com.example.productviewer.database.ProductDatabase;
import com.example.productviewer.fragment.AllProductsFragment;
import com.example.productviewer.fragment.MostCheapestPrductsFragment;
import com.example.productviewer.fragment.MostExpensiveProductsFragment;
import com.example.productviewer.fragment.ProductDetailsFragment;
import com.example.productviewer.interfaces.DatabaseFetching;
import com.example.productviewer.interfaces.ProductCallbackInterface;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.productviewer.Constant.COMMUNICATION_TYPE;
import static com.example.productviewer.Constant.SHARED_PREFERENCE;

public class MainActivity extends AppCompatActivity implements SelectedItemIterface, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<Product> mProductList;
    private ProductDatabase productDatabase = new ProductDatabase(this);
    private Helper helper = new Helper();
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Log.d("check", "onCreate: ");
        navigationView.setNavigationItemSelectedListener(this);
        showDrawerToggle();

        if (helper.isNetworkAvailable(this) && !helper.checkIfDbExists(this)) {
            checkConnectionMethod();
        } else {
            getData();
        }

        if (savedInstanceState == null) {
//            Log.d("database", "onCreate: "+mProductList.get(0).getProduct().getName());
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
            saveData("retrofit");
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
                updateAllProductFragment(mProductList);
            }

            @Override
            public void failedCallback(String s) {

                displayToast("Retrofit failed");

            }
        }, this);
    }

    private void updateAllProductFragment(List<Product> productList) {
            mProductList = productList ;
        if (!helper.checkIfDbExists(this)) {
            productDatabase.insertData(mProductList);
        }
        Log.d("allproduct", "updateAllProductFragment: "+ mProductList.get(0).getProduct().getName());
        AllProductsFragment allProductsFragment = (AllProductsFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        allProductsFragment.updateData(mProductList);
    }

    private void displayToast(String messageToast) {
        Toast.makeText(this, messageToast, Toast.LENGTH_SHORT).show();
    }

    public void retrofitRetrieve(MenuItem item) {

        saveData("retrofit");
        item.setChecked(true);
    }

    public void httpRetrieve(MenuItem item) {

        saveData("http");
        item.setChecked(true);
    }

    public void saveData(String s) {
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
        productDatabase.fetchProducts(new DatabaseFetching() {
            @Override
            public void onDeliverAllProduct(List<Product> productList) {
                mProductList = productList;
                Log.d("database", "onDeliverAllProduct: "+ mProductList.get(0).getProduct().getName());
                updateAllProductFragment(productList);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_all_products:
                bundle.putParcelableArrayList("selected item", (ArrayList<? extends Parcelable>) mProductList);
                AllProductsFragment allProductsFragment = new AllProductsFragment();
                allProductsFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, allProductsFragment).commit();
                break;

            case R.id.nav_most_cheapest_products:
                bundle.putParcelableArrayList("selected item", (ArrayList<? extends Parcelable>) mProductList);
                MostCheapestPrductsFragment mostCheapestPrductsFragment = new MostCheapestPrductsFragment();
                mostCheapestPrductsFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, mostCheapestPrductsFragment).commit();
                break;

            case R.id.nav_most_expensive_products:
                bundle.putParcelableArrayList("selected item", (ArrayList<? extends Parcelable>) mProductList);
                MostExpensiveProductsFragment mostExpensiveProductsFragment = new MostExpensiveProductsFragment();
                mostExpensiveProductsFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, mostExpensiveProductsFragment).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}