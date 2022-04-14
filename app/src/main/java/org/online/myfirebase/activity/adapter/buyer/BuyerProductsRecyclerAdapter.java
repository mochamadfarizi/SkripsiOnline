package org.online.myfirebase.activity.adapter.buyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.online.myfirebase.R;
import org.online.myfirebase.activity.adapter.seller.ProductsRecyclerAdapter;
import org.online.myfirebase.activity.buyer.BuyerHomeActivity;
import org.online.myfirebase.activity.seller.SellerHomeActivity;
import org.online.myfirebase.model.Product;

import java.util.List;

public class BuyerProductsRecyclerAdapter extends RecyclerView.Adapter<BuyerProductsRecyclerAdapter.ProductsViewHolder> {
    private List<Product> listProducts;
    private Context mContext;
    private OnItemClickListener mListener;

    public BuyerProductsRecyclerAdapter(Context context, List<Product> listProducts) {
        this.listProducts = listProducts;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buyer_product_recycler, parent, false);
        return new ProductsViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder,  int position) {
        holder.productId.setText(listProducts.get(position).getKey());
        holder.productName.setText(listProducts.get(position).getName());
        holder.productPrice.setText(String.valueOf(listProducts.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
            return   listProducts.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =  listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView productId;
        public TextView productName;
        public TextView productPrice;
        public TextView textViewButton;
        public Button deletedata;
        public RecyclerView recyclerView;
        public ProductsViewHolder(@NonNull View view, OnItemClickListener listener) {
            super(view);
            productId = (TextView) view.findViewById(R.id.ProductID);
            productName = (TextView) view.findViewById(R.id.ProductName);
            productPrice = (TextView) view.findViewById(R.id.ProductPrice);
            deletedata=(Button) view.findViewById(R.id.ButtonDelete);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewProducts);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });
        }


    }
}
