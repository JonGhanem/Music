package com.example.productviewer.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.productviewer.R;
import com.example.productviewer.adapter.ProductsAdapter;
import com.example.productviewer.fragment.ProductDetailsFragment;
import com.example.productviewer.interfaces.FragmentCommunicator;
import com.example.productviewer.interfaces.SelectedItem;
import com.example.productviewer.model.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.productviewer.App.COMMUNICATION_TYPE;
import static com.example.productviewer.App.SHARED_PREFERENCE;

public class MainActivity extends AppCompatActivity implements SelectedItem {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<Product> productList;
    private ProductDetailsFragment productDetailsFragment;
    private ProductsAdapter mProductsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupNavController();
    }

    private void setupNavController() {

        productList = getIntent().getParcelableArrayListExtra("productList");
        if (productList != null) {
            Log.d("data", productList.get(0).getProduct().getName());
        }
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

    public void passVal(FragmentCommunicator fragmentCommunicator) {

        if(productList!=null){

            fragmentCommunicator.passProductList(productList);
        }
    }

    @Override
    public void onItemClickListener(Product product) {

        Log.d("item1", "onItemClickListener: "+ product.getProduct().getName());
        Toast.makeText(this,"hello new fragment", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected item", product);
        productDetailsFragment = new ProductDetailsFragment();
        productDetailsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment, productDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
