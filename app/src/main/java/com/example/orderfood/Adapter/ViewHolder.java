package com.example.orderfood.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.orderfood.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewHolder extends RecyclerView.ViewHolder{


    public ImageView edit;
    public ImageView delete;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void setDetails (FragmentActivity activity, String itemName, String itemPrice, String itemImgUri, String itemDescription){
            TextView nameItem, priceItem, descripItem;
            CircleImageView imgItem;

            nameItem = itemView.findViewById(R.id.NameItem);
            priceItem = itemView.findViewById(R.id.PricItem);
            descripItem = itemView.findViewById(R.id.descripItem);
            imgItem = itemView.findViewById(R.id.ImgItem);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

            nameItem.setText(itemName);
            priceItem.setText(itemPrice);
            Picasso.get().load(itemImgUri).into(imgItem);

            descripItem.setText(itemDescription);

            Animation animation = AnimationUtils.loadAnimation(activity,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);

        }

}
