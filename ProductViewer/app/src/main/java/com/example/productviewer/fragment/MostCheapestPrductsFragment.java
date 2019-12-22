package com.example.productviewer.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.productviewer.R;
import com.example.productviewer.activities.MainActivity;
import com.example.productviewer.adapter.ProductsAdapter;
import com.example.productviewer.interfaces.FragmentCommunicatorInterface;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostCheapestPrductsFragment extends Fragment {

    @BindView(R.id.most_cheapest_products_recyclerview)
    RecyclerView recyclerView;


    public MostCheapestPrductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_most_cheapest_products,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        ((MainActivity) Objects.requireNonNull(getActivity())).passProductItem(new FragmentCommunicatorInterface() {
            @Override
            public void passProductList(List<Product> productList) {
                //ADAPTER
                ProductsAdapter productsAdapter = new ProductsAdapter(productList, getActivity());
                //intialize SelectedItems for the adapter
                if(getActivity() instanceof SelectedItemIterface){
                    productsAdapter.setmSelectedItemIterface((SelectedItemIterface) getActivity());
                }
                recyclerView.setAdapter(productsAdapter);

            }
        });
    }

    @Override
    @NonNull
    public String toString() {

        return "Most Cheapest Products";
    }

    public void mostCheapestProductsInflate(ArrayList<Product> productList){
        Log.d("item", productList.get(1).getProduct().getDescription());

        //ADAPTER
        recyclerView.setAdapter(new ProductsAdapter(productList, getActivity()));

    }


}
