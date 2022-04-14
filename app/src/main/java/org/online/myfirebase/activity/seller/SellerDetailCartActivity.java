package org.online.myfirebase.activity.seller;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Cart;

public class SellerDetailCartActivity extends AppCompatActivity {
    private TextInputEditText textInputEditTextProductName, textInputEditTextProductPrice, textInputEditTextProductBuyer, textInputEditTextProductQuantity;
    private AppCompatButton  ButtonBack;
    private DatabaseReference mDatabase;
    private Cart cart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inisalisasi layout
        setContentView(R.layout.activity_seller_detail_cart);
        //inisialisasi Firebase databse
        mDatabase= FirebaseDatabase.getInstance().getReference();
        //inisialisasi view
        textInputEditTextProductName=(TextInputEditText) findViewById(R.id.textInputEditTextProductName);
        textInputEditTextProductPrice=(TextInputEditText) findViewById(R.id.textInputEditTextProductPrice);
        textInputEditTextProductQuantity=(TextInputEditText) findViewById(R.id.textInputEditTextProductQuantity);
        textInputEditTextProductBuyer=(TextInputEditText) findViewById(R.id.textInputEditTextProductBuyer);
        ButtonBack=(AppCompatButton)findViewById(R.id.ButtonBack);
        //mengambil data pada adapter
        String nama_product=getIntent().getStringExtra("dataName");
        String buyer=getIntent().getStringExtra("user_cart");
        String price=getIntent().getStringExtra("price_cart");
        String quantyty=getIntent().getStringExtra("quantity_cart");
        //set text menggunakan String yang suda di inisialisasi di atas
        textInputEditTextProductName.setText(nama_product);
        textInputEditTextProductPrice.setText(price);
        textInputEditTextProductQuantity.setText(quantyty);
        textInputEditTextProductBuyer.setText(buyer);

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




}
