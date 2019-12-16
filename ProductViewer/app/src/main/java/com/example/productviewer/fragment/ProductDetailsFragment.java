package com.example.productviewer.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.productviewer.R;
import com.example.productviewer.activities.MainActivity;
import com.example.productviewer.interfaces.FragmentCommunicator;
import com.example.productviewer.model.Product;
import com.squareup.picasso.Picasso;

import java.nio.file.Files;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    Bundle bundle = new Bundle();
    Product mProduct;


    @BindView(R.id.image_product_details)
    ImageView productImage;

    @BindView(R.id.name_product_details)
    TextView productName;

    @BindView(R.id.price_product_details)
    TextView productPrice;

    @BindView(R.id.description_product_details)
    TextView productDescription;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mProduct = this.getArguments().getParcelable("selected item");
        setVariable();
        Log.d("Item11", "onViewCreated: "+ mProduct.getProduct().getName());
    }

    private void setVariable() {
        Picasso.get().load(mProduct.getProduct().getImageUrl()).into(productImage);
        productName.setText("Product Name: "+"\n"+mProduct.getProduct().getName());
        productPrice.setText("price: "+"\n $  "+mProduct.getProduct().getPrice());
        productDescription.setText("Descriptions: "+"\n"+mProduct.getProduct().getDescription());
    }


}
