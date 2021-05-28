package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.POJO.CommonModel;
import com.example.orderfood.POJO.OwnerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.hoang8f.widget.FButton;

import static com.example.orderfood.R.id.forgot_btn;

public class MainActivity extends AppCompatActivity {
    private EditText Phone,Password;
    private Button login_btn;
    private TextView forgot_btn,register_btn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Phone=findViewById(R.id.Phone);
        Password=findViewById(R.id.Password);
        login_btn=findViewById(R.id.login_btn);
        progressDialog = new ProgressDialog(MainActivity.this);

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("Canteen Owners");


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone=Phone.getText().toString().trim();
                final String password= Password.getText().toString().trim();
                if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(password)){
                    Phone.setError("Please Enter Email");
                    Password.setError("Please Enter Password");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Phone.setError("Please Enter Email");
                    return;
                }if(TextUtils.isEmpty(password)){
                    Password.setError("Please Enter Password");
                    return;
                }
                progressDialog.setTitle("Loging...");
                progressDialog.show();
               table_user.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.child(phone).exists()) {
                           progressDialog.dismiss();
                           OwnerModel ownerModel = snapshot.child(phone).getValue(OwnerModel.class);
                           if (ownerModel.getPasssword().equals(password)) {
                               Toast.makeText(MainActivity.this, "Login Successfully!!!", Toast.LENGTH_SHORT).show();
                               Intent i= new Intent(MainActivity.this,DashbordActivity.class);
                               CommonModel.currentUser=ownerModel;
                               startActivity(i);
                               finish();
                           } else {

                               Password.setError("Invalid phone number/password");
                           }
                       }
                       else{
                           Phone.setError("User does not exists");
                           progressDialog.dismiss();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });

        register_btn=findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });
        forgot_btn=findViewById(R.id.forgot_btn);
        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= Phone.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    Phone.setError("Please Enter Email");
                    return;
                }
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        MainActivity.this);

                alertDialog2.setTitle("Forgot Password ?");
                alertDialog2.setMessage(""+email);

                alertDialog2.setPositiveButton("SEND",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog2.show();
            }
        });
    }
}