package org.online.myfirebase.activity.seller;

import android.content.Context;
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
import org.online.myfirebase.activity.adapter.CartsRecyclerAdapter;
import org.online.myfirebase.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivitySeller extends AppCompatActivity implements View.OnClickListener, CartsRecyclerAdapter.dataListener {
    private AppCompatActivity activity = CartActivitySeller.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewCarts;
    private List<Cart> listCarts;
    private CartsRecyclerAdapter cartsRecyclerAdapter;
    private DatabaseReference mDatabase;
    private Cart cart;
    private Button ButtonRefreshCart, ButtonBack;
    Context mContext;
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_cart_seller);
        //inisialisasi firebase
        FirebaseApp.initializeApp(this);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewCarts = (RecyclerView) findViewById(R.id.recyclerViewCarts);
        ButtonRefreshCart = (Button)findViewById(R.id.ButtonRefreshCart);
        ButtonBack = (Button)findViewById(R.id.ButtonBack);
    }
    private void initListeners(){
        ButtonRefreshCart.setOnClickListener(this);
        ButtonBack.setOnClickListener(this);

    }
    private void initObjects() {
        listCarts = new ArrayList<>();
        cartsRecyclerAdapter = new CartsRecyclerAdapter(mContext, listCarts);
        recyclerViewCarts.setLayoutManager(new LinearLayoutManager(this, recyclerViewCarts.VERTICAL, true));
        recyclerViewCarts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCarts.setHasFixedSize(false);
        recyclerViewCarts.setAdapter(cartsRecyclerAdapter);
        cart = new Cart();
        getCartFirebase();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ButtonRefreshCart:
                getCartFirebase();
                break;
            case R.id.ButtonBack:
                finish();
                break;
        }

    }

    private void getCartFirebase() {
        //inisialisasi Firebase
        mDatabase=FirebaseDatabase.getInstance().getReference();
        //method get data cart
        mDatabase.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                listCarts = new ArrayList<>();
                //perulangan untuk mengambil setiap data pada Cart
                for(DataSnapshot mDataSnapshot :snapshot.getChildren()){
                    Cart cart = mDataSnapshot.getValue(Cart.class);
                    cart.setKey(mDataSnapshot.getKey());
                    listCarts.add(cart);
                }
                cartsRecyclerAdapter= new CartsRecyclerAdapter(CartActivitySeller.this,listCarts);
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
        mDatabase.child("Cart").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CartActivitySeller.this, "Data has been removed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
