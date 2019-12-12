package com.example.productviewer.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productviewer.DummyData;
import com.example.productviewer.R;
import com.example.productviewer.adapter.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostExpensiveProductsFragment extends Fragment {

    @BindView(R.id.most_expnsive_products_recyclerview)
    RecyclerView recyclerView;


    public MostExpensiveProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_most_expensive_products, null);

        List<DummyData> dummyDataList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            DummyData dummyDataItem = new DummyData("product " + (i + 100), "the price is $$$$");

            dummyDataList.add(dummyDataItem);

        }

        //REFERENCE
        recyclerView = (RecyclerView) rootView.findViewById(R.id.most_expnsive_products_recyclerview);

//        //LAYOUT MANAGER
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        recyclerView.setAdapter(new ProductsAdapter(dummyDataList, getActivity()));


        return rootView;
    }

    @Override
    @NonNull
    public String toString() {

        return "Most Expnsive Products";
    }
}
