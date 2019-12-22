package com.example.productviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.TaskStackBuilder;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.productviewer.R;
import com.example.productviewer.api.FetchHttpConnection;
import com.example.productviewer.api.FetchRetrofitConnection;
import com.example.productviewer.database.ProductDatabase;
import com.example.productviewer.fragment.AllProductsFragment;
import com.example.productviewer.fragment.ProductDetailsFragment;
import com.example.productviewer.interfaces.FragmentCommunicatorInterface;
import com.example.productviewer.interfaces.ProductCallbackInterface;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;
import com.google.android.material.navigation.NavigationView;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.productviewer.App.COMMUNICATION_TYPE;
import static com.example.productviewer.App.SHARED_PREFERENCE;

public class MainActivity extends AppCompatActivity implements SelectedItemIterface {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<Product> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Log.d("check", "onCreate: ");
        checkConnectionMethod();
        setupNavController();
    }

    private void setupNavController() {

//        mProductList = getIntent().getParcelableArrayListExtra("mProductList");
//        if (mProductList != null) {
//            Log.d("data", mProductList.get(0).getProduct().getName());
//        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_all_products, R.id.nav_most_cheapest_products,
                R.id.nav_most_expensive_products)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
            Toast.makeText(this, "first time", Toast.LENGTH_SHORT).show();
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

                updateAllProductFragment(productList);

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
                updateAllProductFragment(productList);
            }

            @Override
            public void failedCallback(String s) {

                displayToast("Retrofit failed");
            }
        }, this);
    }

    private void updateAllProductFragment(ArrayList<Product> productList) {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FragmentCommunicatorInterface fragmentCommunicator = (FragmentCommunicatorInterface) navHostFragment.getChildFragmentManager().getFragments().get(0);
        fragmentCommunicator.passProductList(productList);
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

    public void passProductItem(FragmentCommunicatorInterface fragmentCommunicatorInterface) {

        if (mProductList != null) {

            fragmentCommunicatorInterface.passProductList(mProductList);
        }
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
}