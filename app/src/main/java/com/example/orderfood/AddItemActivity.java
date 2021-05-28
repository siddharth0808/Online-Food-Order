package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.POJO.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {

    ImageView itemImage,back;
    EditText itemName,itemPrice,itemDescription;

    Button addBtn;
    final static int PICK_IMAGE_REQUEST=1000;
    private Uri imageuri;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Model model;

    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        storageReference = FirebaseStorage.getInstance().getReference("Food Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Food Items");

        itemName=findViewById(R.id.itemName);
        itemPrice= findViewById(R.id.itemPrice);
        itemImage= findViewById(R.id.itemImage);
        itemDescription= findViewById(R.id.itemDescription);
        addBtn= findViewById(R.id.addBtn);
      back=findViewById(R.id.back);
       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(AddItemActivity.this,DashbordActivity.class);
                startActivity(i);
            }
        });
        progressDialog = new ProgressDialog(AddItemActivity.this);
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,PICK_IMAGE_REQUEST);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode== RESULT_OK && data!=null&& data.getData()!=null ){
            imageuri=data.getData();
            Picasso.get().load(imageuri).into(itemImage);

        }
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    private void uploadImage() {
        if (imageuri != null) {

            progressDialog.setTitle("Item is Uploading...");
            progressDialog.show();
            final String imageName= UUID.randomUUID().toString();
            final StorageReference storageReference2 = storageReference.child("Food Images"+imageName);
            storageReference2.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            /*String Iname = itemName.getText().toString().trim();
                            String Iprice = itemPrice.getText().toString().trim();
                            String Idecrip = itemDescription.getText().toString().trim();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Item Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            Model imageUploadInfo = new Model(Iname, Iprice ,taskSnapshot.getUploadSessionUri().toString(),Idecrip);
                            */
                            progressDialog.dismiss();
                            Toast.makeText(AddItemActivity.this, "Uploaded!!!", Toast.LENGTH_SHORT).show();
                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    model = new Model(itemName.getText().toString(), itemPrice.getText().toString(),
                                            uri.toString(), itemDescription.getText().toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(model);
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddItemActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {

            Toast.makeText(AddItemActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

    }
}