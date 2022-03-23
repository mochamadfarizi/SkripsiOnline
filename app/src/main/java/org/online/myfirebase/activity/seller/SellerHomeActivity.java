package org.online.myfirebase.activity.seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.online.myfirebase.R;
import org.online.myfirebase.activity.AddProductActivity;
import org.online.myfirebase.activity.adapter.ProductsRecyclerAdapter;
import org.online.myfirebase.model.Product;


import java.util.ArrayList;

public class SellerHomeActivity extends AppCompatActivity implements View.OnClickListener{


    private RecyclerView recyclerViewProducts;
    private TextView TextViewButton;
    private ArrayList<Product> listProducts;
    private ProductsRecyclerAdapter productsRecyclerAdapter;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Button ButtonAddProduct, ButtonRefreshProduct, ButtonBuyerCart, ButtonLogout;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        //inisialisasi view
        recyclerViewProducts = (RecyclerView) findViewById(R.id.recyclerViewProducts);
        ButtonAddProduct = (Button)findViewById(R.id.ButtonAddProduct);
        ButtonRefreshProduct = (Button)findViewById(R.id.ButtonRefreshProduct);
        ButtonBuyerCart = (Button)findViewById(R.id.ButtonBuyerCart);
        ButtonLogout = (Button)findViewById(R.id.ButtonLogout);

        //inisialisasi Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        iniObject();
        initListeners();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ButtonAddProduct:
                startActivity(new Intent(SellerHomeActivity.this, AddProductActivity.class));
                break;
            case R.id.ButtonRefreshProduct:
                getDataFromFirebase();
                break;
            case R.id.ButtonBuyerCart:
                startActivity(new Intent(SellerHomeActivity.this, CartActivitySeller.class));
                break;
            case R.id.ButtonLogout:
                finish();
                break;

        }
    }

    private void initListeners() {
        ButtonAddProduct.setOnClickListener(this);
        ButtonRefreshProduct.setOnClickListener(this);
        ButtonBuyerCart.setOnClickListener(this);
        ButtonLogout.setOnClickListener(this);
    }

private void iniObject(){
   // Inisialisasi RecyclerView & komponennya
        listProducts = new ArrayList<>();
        productsRecyclerAdapter = new ProductsRecyclerAdapter(mContext, listProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, recyclerViewProducts.VERTICAL, false));
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
                    Product barang = mDataSnapshot.getValue(Product.class);
                    barang.setKey(mDataSnapshot.getKey());
                    listProducts.add(barang);
                }

                productsRecyclerAdapter = new ProductsRecyclerAdapter(SellerHomeActivity.this, listProducts);
                recyclerViewProducts.setAdapter(productsRecyclerAdapter);
                productsRecyclerAdapter.setOnItemClickListener(new ProductsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,final int position) {
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
                System.out.println(error.getDetails()+" "+error.getMessage());
            }
        });
    }
    private void openDetailProductActivity(int productId) {
        Intent intent = new Intent(getApplicationContext(), DetailProductSeller.class);
        intent.putExtra("productID", productId);
        startActivity(intent);
    }
}

