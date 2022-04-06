package org.online.myfirebase.activity.buyer;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Cart;

public class DetailProductBuyer extends AppCompatActivity  {
        private TextInputEditText textInputEditTextProductName,textInputEditTextProductPrice,textInputEditTextProductQuantity;
        private AppCompatButton ButtonAddToCart,ButtonBack;
        private DatabaseReference database;
        private Cart cart;
        Cursor curson;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //inisialisasi layout
            setContentView(R.layout.activity_detail_product_buyer);
            //inisialisasi pada Firebase
            database=FirebaseDatabase.getInstance().getReference();
            //inisialisasi view
             textInputEditTextProductName=(TextInputEditText) findViewById(R.id.textInputEditTextProductName);
             textInputEditTextProductPrice=(TextInputEditText) findViewById(R.id.textInputEditTextProductPrice);
             textInputEditTextProductQuantity=(TextInputEditText) findViewById(R.id.textInputEditTextProductQuantity);
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

                    if(!isEmpty(textInputEditTextProductName.getText().toString()) && !isEmpty(textInputEditTextProductPrice.getText().toString()) && !isEmpty(textInputEditTextProductQuantity.getText().toString()))
                    {
                        //pemanggilan method post dengan parameter model
                        postCartFirebase(new Cart(textInputEditTextProductName.getText().toString(),textInputEditTextProductPrice.getText().toString(),Integer.parseInt(textInputEditTextProductQuantity.getText().toString())));
                    }else {
                        Snackbar.make(findViewById(R.id.ButtonAddToCart), "Data Can't be Empty", Snackbar.LENGTH_LONG).show();
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(
                                textInputEditTextProductName.getWindowToken(), 0);
                    }
                }
            });
            ButtonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            database.child("Cart").child("prdouctId").child("id").removeValue();
        }


    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }


    private void postCartFirebase(Cart cart) {
        String nama=getIntent().getStringExtra("nama1");
        String price=getIntent().getStringExtra("price1");
        String userName=getIntent().getStringExtra("Username");

        database.child("Cart").push().setValue(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cart.getUsername();
                textInputEditTextProductName.setText(nama);
                textInputEditTextProductPrice.setText(price);
                textInputEditTextProductQuantity.setText("");
                Snackbar.make(findViewById(R.id.ButtonAddToCart),"Successfully Add to Cart",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
