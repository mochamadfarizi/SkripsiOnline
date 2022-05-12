package org.online.myfirebase.activity.seller;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Product;


public class DetailProductSeller extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText textInputEditTextProductName, textInputEditTextProductPrice;
    private AppCompatButton  ButtonBack;
    private DatabaseReference database;
    private Product product;
    Cursor cursor;



    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_product_seller);
    //inisiasi Firebase databas
        FirebaseApp.initializeApp(this);
    database=FirebaseDatabase.getInstance().getReference();
    //inisialisasi view
    textInputEditTextProductName=(TextInputEditText) findViewById(R.id.textInputEditTextProductName);
    textInputEditTextProductPrice=(TextInputEditText) findViewById(R.id.textInputEditTextProductPrice);
    ButtonBack=(AppCompatButton)findViewById(R.id.ButtonBack);


    //perintah untuk mengeset data pada Data yang tersimpan pada adapter
    String nama=getIntent().getStringExtra("nama");
    String price=getIntent().getStringExtra("price");
    textInputEditTextProductName.setText(nama);
    textInputEditTextProductPrice.setText(price);

    initListeners();

}


    private void initListeners() {

        ButtonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.ButtonBack:
                finish();
        }

    }
        }






