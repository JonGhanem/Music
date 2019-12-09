package com.example.productviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productviewer.DummyData;
import com.example.productviewer.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<DummyData> dummyItems;
    private Context context;

    public ProductsAdapter(List<DummyData> dummyItems, Context context) {
        this.dummyItems = dummyItems;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_overview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DummyData dummyData = dummyItems.get(position);

        holder.productNameOverview.setText(dummyData.getProductName());
        holder.productDetailsOverview.setText(dummyData.getProductDetails());

    }

    @Override
    public int getItemCount() {
        return dummyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productNameOverview, productDetailsOverview;
        ImageView productImageOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageOverview = itemView.findViewById(R.id.product_image_overview);
            productNameOverview = itemView.findViewById(R.id.product_name_overview);
            productDetailsOverview = itemView.findViewById(R.id.product_details_overview);
        }
    }
}
