package org.online.myfirebase.activity.buyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import org.online.myfirebase.activity.seller.DetailProductSeller;
import org.online.myfirebase.activity.seller.SellerHomeActivity;
import org.online.myfirebase.model.Product;

import java.util.ArrayList;
import java.util.List;


public class BuyerHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatActivity activity = BuyerHomeActivity.this;
    private RecyclerView recyclerViewProducts;
    private ArrayList<Product> listProducts;
    private ProductsRecyclerAdapter productsRecyclerAdapter;
    private DatabaseReference mDatabase;
    private Button ButtonBuyerChart,ButtonLogout,SeeCart;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inisialisasiFirebase
        mDatabase=FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_buyer_home);
        initView();
        initObject();
        initListener();
    }

    private void initListener() {
        ButtonBuyerChart.setOnClickListener(this);
        ButtonLogout.setOnClickListener(this);
    }

    private void initView() {
        recyclerViewProducts = (RecyclerView) findViewById(R.id.recyclerViewProducts);
        ButtonBuyerChart = (Button)findViewById(R.id.ButtonBuyerCart);
        ButtonLogout = (Button)findViewById(R.id.ButtonLogout);
    }


    private void initObject() {
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



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ButtonBuyerCart:
               startActivity(new Intent(BuyerHomeActivity.this,CartActivityBuyer.class));
                break;
            case R.id.ButtonLogout:
                finish();
                break;
        }
    }
    private void getDataFromFirebase() {
        //inisialisasi firebase database
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

                productsRecyclerAdapter = new ProductsRecyclerAdapter(BuyerHomeActivity.this, listProducts);
                recyclerViewProducts.setAdapter(productsRecyclerAdapter);
                productsRecyclerAdapter.setOnItemClickListener(new ProductsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,final int position) {
                        //inisialisasi view pada Detail product
                        Intent intent = new Intent(getApplicationContext(), DetailProductBuyer.class);
                        //pengambilan data yang tersimpan pada adapter
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("nama1",listProducts.get(position).getName());
                        intent.putExtra("price1",listProducts.get(position).getPrice());
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
}
