package org.online.myfirebase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.online.myfirebase.R;
import org.online.myfirebase.activity.buyer.BuyerHomeActivity;
import org.online.myfirebase.activity.buyer.DetailProductBuyer;
import org.online.myfirebase.activity.seller.SellerHomeActivity;
import org.online.myfirebase.model.user;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private TextView textViewLink;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private Button login;
    private EditText nama;
    private EditText pass;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference ;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //inisialisasi view
        nama = (EditText) findViewById(R.id.textInputEditTextUsername);
        pass = (EditText) findViewById(R.id.textInputEditTextPassword);
        login = (Button) findViewById(R.id.appCompatButtonLogin);
        textViewLink = (TextView) findViewById(R.id.textViewLinkRegister);
        //inisialiasasi file firebase aplikasi
        FirebaseApp.initializeApp(this);
        //inisialisasi firebase databse
        mDatabase=FirebaseDatabase.getInstance();
        mGetReference=mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();


        //activity ketika button login di click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });
        textViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }

        });
    }
    private void userLogin() {
        String nama1 = nama.getText().toString().trim();
        String pass1 = pass.getText().toString().trim();
        Query role = mGetReference.orderByChild("role");
        if (nama1.isEmpty()) {
            nama.setError("Email required!");
            nama.requestFocus();
            return;
        }   
        if (pass1.isEmpty()) {
            pass.setError("Password required!");
            pass.requestFocus();
            return;
        }
        if (pass1.length() < 6) {
            pass.setError("Password wrong!");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(nama1, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, BuyerHomeActivity.class));
                    Toast.makeText(LoginActivity.this, "Login Successfully,Enjoy Your Shopping!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed,Please Try Again Dude", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void GetRole() {
        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    for (String key : dataMap.keySet()) {
                        Object data = dataMap.get(key);
                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;
                             user mUser = new user((String) userData.get("role"));
                             mUser.getRole();
                            if (mUser.equals("Seller")) {
                                startActivity(new Intent(LoginActivity.this, SellerHomeActivity.class));
                            }
                           else if (mUser.equals("Buyer")) {
                                startActivity(new Intent(LoginActivity.this, BuyerHomeActivity.class));
                            }
                           return;
                        } catch (ClassCastException cce) {
// If the object can’t be casted into HashMap, it means that it is of type String.
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}



