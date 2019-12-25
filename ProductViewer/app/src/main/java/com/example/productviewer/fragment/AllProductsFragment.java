package com.example.productviewer.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productviewer.utils.HelperClass;
import com.example.productviewer.R;
import com.example.productviewer.adapter.ProductsAdapter;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductsFragment extends Fragment {

    @BindView(R.id.all_products_recyclerview)
    RecyclerView recyclerView;

    private HelperClass helper = new HelperClass();

    private List<Product> productList = new ArrayList<>();

    private ProgressDialog dialog;
    private String fragmentName;

    public AllProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_products, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        //show name of fragment
        getActivity().setTitle("All Products");
        Log.d("check", "onViewCreated: allproducts");
        dialog = new ProgressDialog(getActivity());
        if(helper.isNetworkAvailable(getActivity())){
            dialog.setTitle("Parsing JSON feed...");
        }else{
            dialog.setTitle("Reading from internal storage...");
        }
        if (getArguments() != null) {
            productList = getArguments().getParcelableArrayList("selected item");
            Log.d("ProductFrsgmrnt", "onViewCreated: " + productList.get(0).getProduct().getName());
            fragmentName = getArguments().getString("fragmentName");
        }
        if (productList == null || productList.isEmpty()){
            dialog.show();
        }
        getActivity().setTitle(fragmentName);
        //ADAPTER
        ProductsAdapter productsAdapter = new ProductsAdapter(productList, getActivity());
        //intialize SelectedItems for the adapter
        if (getActivity() instanceof SelectedItemIterface) {
            productsAdapter.setmSelectedItemIterface((SelectedItemIterface) getActivity());
        }
        recyclerView.setAdapter(productsAdapter);

    }

    @Override
    @NonNull
    public String toString() {
        return "All Products";
    }


    public void updateData(List<Product> products){
        //show name of fragment
        getActivity().setTitle("All Products");
        this.productList = products;
        //ADAPTER
        ProductsAdapter productsAdapter = new ProductsAdapter(productList, getActivity());
        //intialize SelectedItems for the adapter
        if (getActivity() instanceof SelectedItemIterface) {
            productsAdapter.setmSelectedItemIterface((SelectedItemIterface) getActivity());
        }
        recyclerView.setAdapter(productsAdapter);
        Log.d("allproduct", "onNavigationItemSelected: "+productList.get(0).getProduct().getName());
        dialog.dismiss();

    }
}
