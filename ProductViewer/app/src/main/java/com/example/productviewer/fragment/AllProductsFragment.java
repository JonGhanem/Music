package com.example.productviewer.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productviewer.R;
import com.example.productviewer.activities.MainActivity;
import com.example.productviewer.adapter.ProductsAdapter;
import com.example.productviewer.interfaces.FragmentCommunicatorInterface;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductsFragment extends Fragment {

    @BindView(R.id.all_products_recyclerview)
    RecyclerView recyclerView;


    public AllProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_products,null);
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
        return "All Products";
    }
}
