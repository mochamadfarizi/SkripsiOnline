package org.online.myfirebase.activity.seller;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Product;


public class DetailProductSeller extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText textInputEditTextProductName, textInputEditTextProductPrice;
    private AppCompatButton ButtonRemoveProduct, ButtonBack;
    private DatabaseReference database;
    private Product product;
    Cursor cursor;



    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_product_seller);
    //inisiasi Firebase database
    database=FirebaseDatabase.getInstance().getReference();
    //inisialisasi view
    textInputEditTextProductName=(TextInputEditText) findViewById(R.id.textInputEditTextProductName);
    textInputEditTextProductPrice=(TextInputEditText) findViewById(R.id.textInputEditTextProductPrice);
    ButtonRemoveProduct=(AppCompatButton)findViewById(R.id.ButtonRemoveProduct);
    ButtonBack=(AppCompatButton)findViewById(R.id.ButtonBack);


    //perintah untuk mengeset data pada Data yang tersimpan pada adapter
    String nama=getIntent().getStringExtra("nama");
    String price=getIntent().getStringExtra("price");
    textInputEditTextProductName.setText(nama);
    textInputEditTextProductPrice.setText(price);

    initListeners();

}


    private void initListeners() {
        ButtonRemoveProduct.setOnClickListener(this);
        ButtonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.ButtonRemoveProduct:
                deleteDataFirebase();
                finish();
            case R.id.ButtonBack:
                finish();
        }

    }
    //Class untuk menghapus data pada firebase
    private void deleteDataFirebase() {
        /**
         * Kode Yang kemudian akan mendelete data di Firebase Realtime DB
         * berdasarkan key barang.
         * Jika sukses akan memunculkan Toast
         */
           database.child("Product").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void unused) {
                   Toast.makeText(DetailProductSeller.this, "Data has been removed", Toast.LENGTH_SHORT).show();
               }
           });



        }
    }




