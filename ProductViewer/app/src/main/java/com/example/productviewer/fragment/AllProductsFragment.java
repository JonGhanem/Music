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

import com.example.productviewer.Helper;
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
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.loading)
    TextView loading;
    Helper helper = new Helper();

    List<Product> productList = new ArrayList<>();

    private ProgressDialog dialog;

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
        Log.d("check", "onViewCreated: ");
        dialog = new ProgressDialog(getActivity());

        if (getArguments() != null) {
            productList = getArguments().getParcelableArrayList("selected item");
        }
        if (productList == null || productList.isEmpty()){
            dialog.show();
        }

        //ADAPTER
        ProductsAdapter productsAdapter = new ProductsAdapter(productList, getActivity());
        //intialize SelectedItems for the adapter
        if (getActivity() instanceof SelectedItemIterface) {
            productsAdapter.setmSelectedItemIterface((SelectedItemIterface) getActivity());
        }
        recyclerView.setAdapter(productsAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    @NonNull
    public String toString() {
        return "All Products";
    }


    public void updateData(List<Product> products){
        this.productList = products;
        Log.d("allproduct", "updateData: "+products.get(0).getProduct().getName());

        //ADAPTER
        ProductsAdapter productsAdapter = new ProductsAdapter(productList, getActivity());
        //intialize SelectedItems for the adapter
        if (getActivity() instanceof SelectedItemIterface) {
            productsAdapter.setmSelectedItemIterface((SelectedItemIterface) getActivity());
        }
        recyclerView.setAdapter(productsAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        dialog.dismiss();
    }
}
