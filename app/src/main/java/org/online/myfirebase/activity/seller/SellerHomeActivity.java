package org.online.myfirebase.activity.seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import org.online.myfirebase.activity.AddProductActivity;
import org.online.myfirebase.activity.CartActivity;
import org.online.myfirebase.activity.adapter.seller.ProductsRecyclerAdapter;
import org.online.myfirebase.model.Product;


import java.util.ArrayList;

public class SellerHomeActivity extends AppCompatActivity implements ProductsRecyclerAdapter.dataListener {


    private RecyclerView recyclerViewProducts;
    private TextView TextViewButton;
    private ArrayList<Product> listProducts;
    private ProductsRecyclerAdapter productsRecyclerAdapter;
    private DatabaseReference mDatabase;
    private Button ButtonAddProduct, ButtonRefreshProduct, ButtonBuyerCart, ButtonLogout;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        //inisialisasi view
        recyclerViewProducts = (RecyclerView) findViewById(R.id.recyclerViewProducts);
        ButtonAddProduct = (Button) findViewById(R.id.ButtonAddProduct);
        ButtonRefreshProduct = (Button) findViewById(R.id.ButtonRefreshProduct);
        ButtonBuyerCart = (Button) findViewById(R.id.ButtonBuyerCart);
        ButtonLogout = (Button) findViewById(R.id.ButtonLogout);

        //inisialisasi Firebase
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        iniObject();
        ButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerHomeActivity.this, AddProductActivity.class));
            }
        });

        ButtonRefreshProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromFirebase();
            }
        });

        ButtonBuyerCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerHomeActivity.this, CartActivity.class));
            }
        });
        ButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void iniObject() {
        // Inisialisasi RecyclerView & komponennya
        listProducts = new ArrayList<>();
        productsRecyclerAdapter = new ProductsRecyclerAdapter(mContext, listProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, recyclerViewProducts.VERTICAL, true));
        recyclerViewProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProducts.setHasFixedSize(false);
        recyclerViewProducts.setAdapter(productsRecyclerAdapter);
        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        getDataFromFirebase();


    }

    private void getDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // method get data
        mDatabase.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                listProducts = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    Product product = mDataSnapshot.getValue(Product.class);
                    product.setKey(mDataSnapshot.getKey());
                    listProducts.add(product);
                }

                productsRecyclerAdapter = new ProductsRecyclerAdapter(SellerHomeActivity.this, listProducts);
                recyclerViewProducts.setAdapter(productsRecyclerAdapter);
               productsRecyclerAdapter.setOnItemClickListener(new ProductsRecyclerAdapter.OnItemClickListener() {
                   @Override
                   public void onItemClick(View view,  final int position) {
                       //inisialisasi view pada Detail product
                       Intent intent = new Intent(getApplicationContext(), DetailProductSeller.class);
                       //pengambilan data yang tersimpan pada adapter
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("nama",listProducts.get(position).getName());
                       intent.putExtra("price",listProducts.get(position).getPrice());
                       startActivity(intent);
                   }
               });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /**
                 *              Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(error.getDetails() + " " + error.getMessage());
            }
        });
    }


    @Override
    public void onDeleteData(Product data, int position) {
        /**
         * Kode Yang kemudian akan mendelete data di Firebase Realtime DB
         * berdasarkan key barang.
         * Jika sukses akan memunculkan Toast
         */
        mDatabase.child("Product").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SellerHomeActivity.this, "Data has been removed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



