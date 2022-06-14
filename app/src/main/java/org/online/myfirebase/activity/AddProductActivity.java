package org.online.myfirebase.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.online.myfirebase.R;
import org.online.myfirebase.model.Product;


public class AddProductActivity extends AppCompatActivity  {
    //inisialisasi variabel
    private EditText productName;
    private EditText ProductPrice;
    private Button addProduct, back;
    private Product product;
    private DatabaseReference mDatabase;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productName = (EditText) findViewById(R.id.textInputEditTextProductName);
        ProductPrice = (EditText) findViewById(R.id.textInputEditTextProductPrice);
        addProduct = (Button) findViewById(R.id.appCompatButtonAddProduct);
        back = (Button) findViewById(R.id.appCompatButtonCancelAddProduct);
        //menginisialisasi firebase
        FirebaseApp.initializeApp(this);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(productName.getText().toString()) && !isEmpty(ProductPrice.getText().toString()))
                {
                    //pemanggilan method post dengan parameter model
                    postProduct(new Product(productName.getText().toString(),ProductPrice.getText().toString()));
                }else {
                    Snackbar.make(findViewById(R.id.appCompatButtonAddProduct), "Data barang tidak boleh kosong",
                            Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            productName.getWindowToken(), 0);
                }
            }
        });
            back.setOnClickListener(new View.OnClickListener() {
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
    public void postProduct(Product product){
        //perinah untuk post pada firebase
        mDatabase.child("Product").push().setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                productName.setText("");
                ProductPrice.setText("");
                Snackbar.make(findViewById(R.id.appCompatButtonAddProduct),
                        "Succes for added data to Firebase!",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
