package org.online.myfirebase.activity.buyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.online.myfirebase.R;
import org.online.myfirebase.activity.adapter.buyer.BuyerCartsRecyclerAdapter;
import org.online.myfirebase.activity.adapter.seller.CartsRecyclerAdapter;
import org.online.myfirebase.activity.seller.SellerHomeActivity;
import org.online.myfirebase.model.Cart;
import java.util.ArrayList;

public class CartActivityBuyer extends AppCompatActivity implements View.OnClickListener, BuyerCartsRecyclerAdapter.dataListener {
        private ArrayList<Cart> listCarts;
        private AppCompatTextView textViewName;
        private DatabaseReference mDatabase;
        private Button ButtonRefreshCart, ButtonBack;
        private RecyclerView recyclerViewCarts;
        private BuyerCartsRecyclerAdapter cartsRecyclerAdapter;
        Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buyer);
        //inisialisasiFirebase
        FirebaseApp.initializeApp(this);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        initViews();
        initObject();
        initListeners();
    }
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewCarts = (RecyclerView) findViewById(R.id.recyclerViewCarts);
        ButtonRefreshCart = (Button)findViewById(R.id.ButtonRefreshCart);
        ButtonBack = (Button)findViewById(R.id.ButtonBack);
    }
    private void initListeners() {
        ButtonRefreshCart.setOnClickListener(this);
        ButtonBack.setOnClickListener(this);
    }
    private void initObject() {
        listCarts = new ArrayList<>();
        cartsRecyclerAdapter = new BuyerCartsRecyclerAdapter(mContext, listCarts);
        recyclerViewCarts.setLayoutManager(new LinearLayoutManager(this, recyclerViewCarts.VERTICAL, true));
        recyclerViewCarts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCarts.setHasFixedSize(false);
        recyclerViewCarts.setAdapter(cartsRecyclerAdapter);
        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        getCartFromFirebase();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ButtonRefreshCart:
                getCartFromFirebase();
                break;
            case R.id.ButtonBack:
                finish();
                break;
        }
    }

    private void getCartFromFirebase() {
        //inisialisasi firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // method get data
        mDatabase.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                listCarts = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    Cart cart = mDataSnapshot.getValue(Cart.class);
                    cart.setKey(mDataSnapshot.getKey());
                    listCarts.add(cart);
                }
                cartsRecyclerAdapter = new BuyerCartsRecyclerAdapter(CartActivityBuyer.this,listCarts);
                recyclerViewCarts.setAdapter(cartsRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /**
                 *              Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(error.getDetails()+" "+error.getMessage());
            }
        });
    }


    @Override
    public void onDeleteData(Cart data, int position) {
        /**
         * Kode Yang kemudian akan mendelete data di Firebase Realtime DB
         * berdasarkan key barang.
         * Jika sukses akan memunculkan Toast
         */
        mDatabase.child("Cart").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CartActivityBuyer.this, "Data has been removed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
