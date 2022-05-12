package org.online.myfirebase.activity.buyer;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Cart;

public class BuyerDetailCartActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText textInputEditTextProductName,
                              textInputEditTextProductPrice,
                              textInputEditTextProductBuyer,
                              textInputEditTextProductQuantity;
    private AppCompatButton  ButtonBack;
    private Cart cart;
    private DatabaseReference mDatabase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inisialisasi Layout
        setContentView(R.layout.activity_buyer_detail_cart);
        //inisialisasi Firebase databse
        FirebaseApp.initializeApp(this);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        //inisialisasi View
        textInputEditTextProductName= (TextInputEditText) findViewById(R.id.textInputEditTextProductName);
        textInputEditTextProductPrice=(TextInputEditText) findViewById(R.id.textInputEditTextProductPrice);
        textInputEditTextProductQuantity=(TextInputEditText) findViewById(R.id.textInputEditTextProductQuantity);
        textInputEditTextProductBuyer=(TextInputEditText) findViewById(R.id.textInputEditTextProductBuyer);
        ButtonBack=(AppCompatButton)findViewById(R.id.ButtonBack);
        initListeners();
        //Pengambilan data yang tersimpan pada adapter
        String nama_product=getIntent().getStringExtra("dataName");
        String buyer=getIntent().getStringExtra("user_cart");
        String price=getIntent().getStringExtra("price_cart");
        String quantyty=getIntent().getStringExtra("quantity_cart");
        //set pada view berdasarkan String yang dibuat
        textInputEditTextProductName.setText(nama_product);
        textInputEditTextProductPrice.setText(price);
        textInputEditTextProductQuantity.setText(quantyty);
        textInputEditTextProductBuyer.setText(buyer);


    }

    private void initListeners() {
        ButtonBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        //pembuatan perintah pada button ketika di click
        switch (v.getId()){
            case R.id.ButtonBack:
                finish();
                break;
        }
    }


}
