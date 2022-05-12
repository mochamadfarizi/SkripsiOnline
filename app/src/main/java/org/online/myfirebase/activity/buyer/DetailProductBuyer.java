package org.online.myfirebase.activity.buyer;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Cart;
import org.online.myfirebase.model.user;

import java.util.List;

public class DetailProductBuyer extends AppCompatActivity  {
        private TextInputEditText textInputEditTextProductName,textInputEditTextProductPrice,textInputEditTextProductQuantity,textInputEditTextProductUser;
        private AppCompatButton ButtonAddToCart,ButtonBack;
        private DatabaseReference mDatabase;
        private Cart cart;

        private List<user> listUsers;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //inisialisasi layout
            setContentView(R.layout.activity_detail_product_buyer);
            //inisialisasi pada Firebase
            FirebaseApp.initializeApp(this);
            mDatabase=FirebaseDatabase.getInstance().getReference();
            //inisialisasi view
             textInputEditTextProductName=(TextInputEditText) findViewById(R.id.textInputEditTextProductName);
             textInputEditTextProductPrice=(TextInputEditText) findViewById(R.id.textInputEditTextProductPrice);
             textInputEditTextProductQuantity=(TextInputEditText) findViewById(R.id.textInputEditTextProductQuantity);
             textInputEditTextProductUser=(TextInputEditText) findViewById(R.id.textInputEditTextProductUser);
             ButtonAddToCart=(AppCompatButton) findViewById(R.id.ButtonAddToCart);
             ButtonBack=(AppCompatButton) findViewById(R.id.ButtonBack);

            //perintah untuk mengeset data pada Data yang tersimpan pada adapter
            String nama=getIntent().getStringExtra("nama1");
            String price=getIntent().getStringExtra("price1");
            textInputEditTextProductName.setText(nama);
            textInputEditTextProductPrice.setText(price);
            ButtonAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!isEmpty(textInputEditTextProductUser.getText().toString()) && !isEmpty(textInputEditTextProductName.getText().toString()) && !isEmpty(textInputEditTextProductPrice.getText().toString()) && !isEmpty(textInputEditTextProductQuantity.getText().toString()))
                    {
                        //pemanggilan method post dengan parameter model
                        postCartFirebase(new Cart(textInputEditTextProductUser.getText().toString(),textInputEditTextProductName.getText().toString(),textInputEditTextProductPrice.getText().toString(),textInputEditTextProductQuantity.getText().toString()));
                    }else {
                        Snackbar.make(findViewById(R.id.ButtonAddToCart), "Data Can't be Empty", Snackbar.LENGTH_LONG).show();
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(
                                textInputEditTextProductUser.getWindowToken(), 0);
                    }
                }
            });
            ButtonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }




    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }


    private void postCartFirebase(Cart cart) {
        String nama=getIntent().getStringExtra("nama1");
        String price=getIntent().getStringExtra("price1");
        String Username = getIntent().getStringExtra("Username");
        mDatabase.child("Cart").push().setValue(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                textInputEditTextProductUser.setText("");
                textInputEditTextProductName.setText(nama);
                textInputEditTextProductPrice.setText(price);
                textInputEditTextProductQuantity.setText("");
                Snackbar.make(findViewById(R.id.ButtonAddToCart),"Successfully Add to Cart",Snackbar.LENGTH_LONG).show();
            }
        });
    }
/**
    private void getUserFirebase(){
        //inisialisasi firebase database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //method get data
        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                listUsers = new ArrayList<>();
                final  int position = 0;
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    user user = mDataSnapshot.getValue(user.class);
                    user.setKey(mDataSnapshot.getKey());
                    listUsers.add(user);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /**
                 *              Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat

                System.out.println(error.getDetails()+" "+error.getMessage());
            }


        });

**/
    }

