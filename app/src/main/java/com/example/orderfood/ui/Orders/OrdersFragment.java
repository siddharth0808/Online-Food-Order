package com.example.orderfood.ui.Orders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.orderfood.Adapter.OrderAdapter;
import com.example.orderfood.POJO.CommonModel;
import com.example.orderfood.POJO.Request;
import com.example.orderfood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrdersFragment extends Fragment {

    private OrdersViewModel galleryViewModel;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    OrderAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    private SwipeRefreshLayout swipe;

    MaterialSpinner spinner;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Orders");


        swipe=root.findViewById(R.id.swippe);
        recyclerView = root.findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();


        return root;
    }

    private void loadOrder() {

        FirebaseRecyclerOptions<Request> options =
                new FirebaseRecyclerOptions.Builder<Request>()
                        .setQuery(requests, Request.class).build();
        adapter = new OrderAdapter(getActivity(), options);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getIntent().equals(CommonModel.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if(item.getIntent().equals(CommonModel.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
    }

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please choose message");

        LayoutInflater inflater= this.getLayoutInflater();
        final  View view= inflater.inflate(R.layout.update_order_layout,null);
        spinner=(MaterialSpinner) view.findViewById(R.id.spinner);
        spinner.setItems("Placed","Preparing","Deliverd");

        alertDialog.setView(view);
        final String localKey="key";
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}