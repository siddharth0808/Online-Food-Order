package com.example.orderfood.ui.Menu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.orderfood.Adapter.ViewHolder;
import com.example.orderfood.POJO.Model;
import com.example.orderfood.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuFragment extends Fragment {

    private MenuViewModel homeViewModel;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;



    // Creating List of ImageUploadInfo class.
    private ArrayList<Model> list = new ArrayList<>();



    private SwipeRefreshLayout swipe;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView=root.findViewById(R.id.itemRV);
        swipe=root.findViewById(R.id.swippe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        reference = database.getReference("Food Items");



       // adapter=new ViewHolder(options);
        //recyclerView.setAdapter(adapter);





         return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food Items"), Model.class)
                        .build();

        final FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter2 =
                new FirebaseRecyclerAdapter<Model,ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final Model model) {

                        holder.setDetails(getActivity(),model.getItemName(),model.getItemPrice(),model.getItemImgUri(),model.getItemDescription());
                        final String itemName = getItem(position).getItemName();
                        final String itemPrice = getItem(position).getItemPrice();
                        final String itemImg = getItem(position).getItemImgUri();
                        final String itemDes = getItem(position).getItemDescription();


                        holder.edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final DialogPlus dialogPlus=DialogPlus.newDialog(getActivity())
                                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.custom_layout))
                                        .setExpanded(true,1500)
                                        .create();

                                View myview=dialogPlus.getHolderView();
                                final EditText Iname= myview.findViewById(R.id.Iname);
                                final EditText Iprice= myview.findViewById(R.id.Iprice);
                                final EditText Idesc= myview.findViewById(R.id.Idesc);
                                final Button update=myview.findViewById(R.id.update);

                                Iname.setText(model.getItemName());
                                Iprice.setText(model.getItemPrice());
                                Idesc.setText(model.getItemDescription());
                                dialogPlus.show();

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("itemName",Iname.getText().toString());
                                        map.put("itemPrice",Iprice.getText().toString());
                                        map.put("itemDescription",Idesc.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("Food Items")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogPlus.dismiss();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                        holder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setTitle("Confirm delete ?");
                                alertDialogBuilder.setMessage("Do you want to delete item "+itemName);
                                        alertDialogBuilder.setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        FirebaseDatabase.getInstance().getReference().child("Food Items")
                                                                .child(getRef(position).getKey()).removeValue();
                                                    }
                                                });

                                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_item,parent,false);

                        return new ViewHolder(view);
                    }
                };


        firebaseRecyclerAdapter2.startListening();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firebaseRecyclerAdapter2.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
        recyclerView.setAdapter(firebaseRecyclerAdapter2);

    }


}