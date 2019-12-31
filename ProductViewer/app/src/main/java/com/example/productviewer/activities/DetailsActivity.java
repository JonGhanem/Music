package com.example.productviewer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.productviewer.R;
import com.example.productviewer.model.Product;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.image_product_details)
    ImageView productImage;
    @BindView(R.id.name_product_details)
    TextView productName;
    @BindView(R.id.price_product_details)
    TextView productPrice;
    @BindView(R.id.description_product_details)
    TextView productDescription;
    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setItemView();

    }

    private void setItemView() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        mProduct = extras.getParcelable("productItem");
        productName.setText("Product Name: "+"\n"+mProduct.getProduct().getName());
        productPrice.setText("price: "+"\n $  "+mProduct.getProduct().getPrice());
        productDescription.setText("Descriptions: "+"\n"+mProduct.getProduct().getDescription());
        Picasso.get().load(mProduct
                .getProduct()
                .getImageUrl())
                .placeholder(R.drawable.shopping)
                .into(productImage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
