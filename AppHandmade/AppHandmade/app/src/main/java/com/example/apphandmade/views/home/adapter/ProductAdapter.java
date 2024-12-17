package com.example.apphandmade.views.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apphandmade.R;
import com.example.apphandmade.helper.ProductDao;
import com.example.apphandmade.views.home.ProductDetailActivity;
import com.example.apphandmade.views.home.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    private Context context;
    private List<Product> productList;
    private List<Product> productListFull;
    private ProductDao productDao;

    public ProductAdapter(Context context, List<Product> productList, ProductDao productDao) {
        this.context = context;
        this.productList = productList;
        this.productListFull = new ArrayList<>(productList);
        this.productDao = productDao;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.imageView.setImageBitmap(bitmap);
        holder.productName.setText(product.getProductName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.ratingBar.setRating(product.getRating());

        if(product.isFavorite()){
            Glide.with(holder.itemView).load(R.drawable.ic_favorite_active).into(holder.icFavorite);
        }else {
            Glide.with(holder.itemView).load(R.drawable.ic_favorite_deactive).into(holder.icFavorite);
        }

        holder.icFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = !product.isFavorite();
                product.setFavorite(isFavorite);
                productDao.updateFavoriteStatus(product.getId(), isFavorite);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", product.getId());
                intent.putExtra("productName", product.getProductName());
                intent.putExtra("productDescription", product.getProductDetail());
                intent.putExtra("productPrice", product.getPrice());
                intent.putExtra("productRating", product.getRating());
                intent.putExtra("productImage", product.getImage());
                intent.putExtra("quantity", product.getQuantity());
                intent.putExtra("isFavorite", product.isFavorite());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product item : productListFull) {
                    if (item.getProductName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, icFavorite;
        TextView productName, price;
        RatingBar ratingBar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Anhsp);
            icFavorite = itemView.findViewById(R.id.ic_favorite);
            productName = itemView.findViewById(R.id.txttensp);
            price = itemView.findViewById(R.id.Dongia);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}




