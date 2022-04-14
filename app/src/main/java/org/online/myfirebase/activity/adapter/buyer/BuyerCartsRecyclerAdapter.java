package org.online.myfirebase.activity.adapter.buyer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.online.myfirebase.R;
import org.online.myfirebase.activity.adapter.seller.CartsRecyclerAdapter;
import org.online.myfirebase.activity.buyer.BuyerDetailCartActivity;
import org.online.myfirebase.activity.buyer.CartActivityBuyer;
import org.online.myfirebase.activity.seller.CartActivitySeller;
import org.online.myfirebase.activity.seller.SellerDetailCartActivity;
import org.online.myfirebase.model.Cart;

import java.util.List;

public class BuyerCartsRecyclerAdapter extends RecyclerView.Adapter<BuyerCartsRecyclerAdapter.BuyerCartsViewHolder>  {
    private List<Cart> listCarts;
    private Context mContext;
    private CartsRecyclerAdapter.OnItemClickListener mListener;
    CartsRecyclerAdapter.dataListener listener;
    public interface dataListener{
        void onDeleteData(Cart data, int position);
    }
    public BuyerCartsRecyclerAdapter(Context context, List<Cart> listCarts) {
        this.listCarts = listCarts;
        mContext = context;
        listener = (CartActivityBuyer)context;
    }
    @NonNull
    @Override
    public BuyerCartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_recycler, parent, false);
        return new BuyerCartsViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerCartsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cartID.setText(String.valueOf(listCarts.get(position).getKey()));
        holder.productName.setText(listCarts.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(listCarts.get(position).getProductPrice()));
        holder.productQuantity.setText(String.valueOf(listCarts.get(position).getProductQuantity()));
        holder.productBuyer.setText(listCarts.get(position).getUsername());
        holder.list_cart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String[] action = {"Detail Data", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                    /*
                                  Berpindah Activity pada halaman layout updateData
                                  dan mengambil data pada listMahasiswa, berdasarkan posisinya
                                  untuk dikirim pada activity Seller Detail Cart
                                 */
                                Bundle bundle = new Bundle();
                                bundle.putString("user_cart", listCarts.get(position).getUsername());
                                bundle.putString("dataName", listCarts.get(position).getProductName());
                                bundle.putString("price_cart", listCarts.get(position).getProductPrice());
                                bundle.putString("quantity_cart", listCarts.get(position).getProductQuantity());
                                bundle.putString("getPrimaryKey", listCarts.get(position).getKey());
                                Intent intent =new Intent(view.getContext(),BuyerDetailCartActivity.class);
                                intent.putExtras(bundle);
                                mContext.startActivity(intent);
                                break;
                            case 1:
                                //Menggunakan interface untuk mengirim data mahasiswa, yang akan dihapus
                                listener.onDeleteData(listCarts.get(position), position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCarts.size();
    }



    public void setOnItemClickListener(CartsRecyclerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(android.view.View view, int position);
    }
    public class BuyerCartsViewHolder extends RecyclerView.ViewHolder {
        public TextView cartID;
        public TextView productName;
        public TextView productPrice;
        public TextView productQuantity;
        public TextView productBuyer;
        public RecyclerView recyclerView;
        public LinearLayout list_cart;

        public BuyerCartsViewHolder(View view, final CartsRecyclerAdapter.OnItemClickListener listener) {
            super(view);
            cartID = (TextView) view.findViewById(R.id.CartID);
            productName = (TextView) view.findViewById(R.id.ProductName);
            productPrice = (TextView) view.findViewById(R.id.ProductPrice);
            productQuantity = (TextView) view.findViewById(R.id.Quantity);
            productBuyer = (TextView) view.findViewById(R.id.ProductBuyer);
            list_cart= itemView.findViewById(R.id.list_cart);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCarts);

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
