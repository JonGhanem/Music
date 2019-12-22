package com.example.productviewer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productviewer.R;
import com.example.productviewer.activities.MainActivity;
import com.example.productviewer.interfaces.SelectedItemIterface;
import com.example.productviewer.model.Product;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;
    private SelectedItemIterface mSelectedItemIterface;
    private int mPosition;
    Context context;

    public ProductsAdapter(List<Product> products, Context context) {

        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_overview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Product.ProductBean product = products.get(position).getProduct();


        holder.productNameOverview.setText(product.getName());
        holder.productDetailsOverview.setText(product.getDescription());
        holder.productPriceOverView.setText("$ " + product.getPrice());

        Picasso.get()
                .load(product.getImageUrl()).placeholder(R.drawable.shopping)
                .into(holder.productImageOverview);


//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .build();
//        ImageLoader.getInstance().init(config);
//        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
//
//// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
////	which implements ImageAware interface)
//        if (imageLoader != null) {
//            imageLoader.displayImage(product.getImageUrl(), holder.productImageOverview);
//        } else {
//            imageLoader.cancelDisplayTask(holder.productImageOverview);
//        }

        //onClick listener interface for passing selected item to mainActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("adapter", "onClick: ");
                if (mSelectedItemIterface != null) {
                    mPosition = position;
                    if (mPosition != RecyclerView.NO_POSITION) {
                        Log.d("adapter", "onClick1: ");
                        mSelectedItemIterface.onItemClickListener(products.get(mPosition));
                    }
                }
            }
        });
    }

    public void setmSelectedItemIterface(SelectedItemIterface selectedItemIterface) {
        mSelectedItemIterface = selectedItemIterface;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameOverview, productDetailsOverview, productPriceOverView;
        ImageView productImageOverview;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageOverview = itemView.findViewById(R.id.product_image_overview);
            productNameOverview = itemView.findViewById(R.id.product_name_overview);
            productDetailsOverview = itemView.findViewById(R.id.product_details_overview);
            productPriceOverView = itemView.findViewById(R.id.price_overview);
        }
    }


}
