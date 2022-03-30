package org.online.myfirebase.activity.buyer;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Product;

public class DetailProductBuyer extends AppCompatActivity implements View.OnClickListener {
        private TextInputEditText textInputEditTextProductName,textInputEditTextProductPrice,textInputEditTextProductQuantity;
        private AppCompatButton ButtonAddToCart,ButtonBack;
        private DatabaseReference database;
        private Product product;
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

            initListeners();
        }

    private void initListeners() {
        ButtonAddToCart.setOnClickListener(this);
        ButtonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.ButtonAddProduct:
               postCartFirebase();
               finish();
               break;
           case R.id.ButtonBack:
               finish();
               break;
        }
    }

    private void postCartFirebase() {
    }
}
