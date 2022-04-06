package org.online.myfirebase.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Cart;

import java.util.List;

public class CartsRecyclerAdapter extends RecyclerView.Adapter<CartsRecyclerAdapter.CartsViewHolder> {
    private List<Cart> listCarts;
    private Context mContext;
    private OnItemClickListener mListener;

    public CartsRecyclerAdapter(Context context, List<Cart> listCarts) {
        this.listCarts = listCarts;
        mContext = context;

    }

    @NonNull
    @Override
    public CartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_recycler, parent, false);

        return new CartsViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(CartsViewHolder holder, int position) {
        holder.cartID.setText(String.valueOf(listCarts.get(position).getId()));
        holder.productName.setText(listCarts.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(listCarts.get(position).getProductPrice()));
        holder.productQuantity.setText(String.valueOf(listCarts.get(position).getProductQuantity()));
        holder.productBuyer.setText(listCarts.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(android.view.View view, int position);
    }

    public class CartsViewHolder extends RecyclerView.ViewHolder {
        public TextView cartID;
        public TextView productName;
        public TextView productPrice;
        public TextView productQuantity;
        public TextView productBuyer;
        public RecyclerView recyclerView;

        public CartsViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            cartID = (TextView) view.findViewById(R.id.CartID);
            productName = (TextView) view.findViewById(R.id.ProductName);
            productPrice = (TextView) view.findViewById(R.id.ProductPrice);
            productQuantity = (TextView) view.findViewById(R.id.Quantity);
            productBuyer = (TextView) view.findViewById(R.id.ProductBuyer);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCarts);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
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
