package com.example.productviewer.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
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
import com.example.productviewer.model.Product;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private Product mProduct;


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
        if (this.getArguments() != null) {
            mProduct = this.getArguments().getParcelable("selected item");
        }
        setVariable();
        Log.d("Item11", "onViewCreated: "+ mProduct.getProduct().getName());
    }

    @SuppressLint("SetTextI18n")
    private void setVariable() {

//        Picasso.get().load(mProduct
//                .getProduct()
//                .getImageUrl())
//                .placeholder(R.drawable.shopping)
//                .into(productImage);

        // UNIVERSAL IMAGE LOADER SETUP
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
			.build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance

// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//	which implements ImageAware interface)
        imageLoader.displayImage(mProduct.getProduct().getImageUrl(), productImage);

        productName.setText("Product Name: "+"\n"+mProduct.getProduct().getName());
        productPrice.setText("price: "+"\n $  "+mProduct.getProduct().getPrice());
        productDescription.setText("Descriptions: "+"\n"+mProduct.getProduct().getDescription());
    }


}
