 package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.POJO.OwnerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import info.hoang8f.widget.FButton;

 public class RegisterActivity extends AppCompatActivity {


    private EditText canteen_name,user_name,user_email,user_mobile,cantenn_address,user_password,user_cpassword;
    private Button register;


    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user_name= findViewById(R.id.user_name);
        user_email= findViewById(R.id.user_email);
        user_mobile= findViewById(R.id.user_mobile);
        user_password = findViewById(R.id.user_password);
        user_cpassword= findViewById(R.id.user_cpassword);
        canteen_name= findViewById(R.id.canteen_name);
        cantenn_address= findViewById(R.id.canteen_address);
        register= findViewById(R.id.Register);


        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("Canteen Owners");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= user_email.getText().toString().trim();
                final String pass= user_password.getText().toString().trim();
                final String cpass= user_cpassword.getText().toString().trim();
                final String cname= canteen_name.getText().toString().trim();
                final String caddress= cantenn_address.getText().toString().trim();
                final String name= user_name.getText().toString().trim();
                final String mobile= user_mobile.getText().toString().trim();


                if(TextUtils.isEmpty(cname) &&TextUtils.isEmpty(name) &&TextUtils.isEmpty(mobile)&&
                        TextUtils.isEmpty(email)&&TextUtils.isEmpty(caddress)&&TextUtils.isEmpty(pass)&&TextUtils.isEmpty(cpass)){
                    canteen_name.setError("Please Enter Canteen Name");
                    user_name.setError("Please Enter Name");
                    user_mobile.setError("Please Enter Mobile Number");
                    user_email.setError("Please Enter Email");
                    cantenn_address.setError("Please Enter Address");
                    user_password.setError("Please Enter Password");
                    user_cpassword.setError("Please Confirm Password");
                    return;
                }
                if(TextUtils.isEmpty(cname)) {
                    canteen_name.setError("Please Enter Canteen Name");
                    return;
                }
                if(TextUtils.isEmpty(name)) {
                    user_name.setError("Please Enter Name");
                    return;
                }
                if(TextUtils.isEmpty(mobile)) {
                    user_mobile.setError("Please Enter Mobile Number");
                    return;
                }
                if(mobile.length()!=10) {
                    user_mobile.setError("Please Enter Valid Mobile Number");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    user_email.setError("Please Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(caddress)) {
                    cantenn_address.setError("Please Enter Address");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    user_password.setError("Please Enter Password");
                    return;
                }
                if(TextUtils.isEmpty(cpass)){
                    user_cpassword.setError("Please Confirm Password");
                    return;
                }
                if(pass.length()<6){
                    user_password.setError("Password length should be 6");
                    return;
                }
                if(!cpass.equals(pass)){
                    user_cpassword.setError("Password not matched");
                    return;
                }

                final ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Please Waiting...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(mobile).exists()){
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Phone number already exixts!!!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            progressDialog.dismiss();
                            OwnerModel ownerModel= new OwnerModel(cname,name,email,caddress,cpass);
                            table_user.child(mobile).setValue(ownerModel);
                            Toast.makeText(RegisterActivity.this, "Register successfully!!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}