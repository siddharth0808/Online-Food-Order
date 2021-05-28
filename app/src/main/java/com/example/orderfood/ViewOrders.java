package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.orderfood.Adapter.OrderAdapter;
import com.example.orderfood.Adapter.ViewOrdersAdapter;
import com.example.orderfood.POJO.OrderModel;
import com.example.orderfood.POJO.Request;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewOrders extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    ViewOrdersAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Orders").child("foods");

        recyclerView = findViewById(R.id.orders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ViewOrders.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadOrder() {

        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Orders").child("foods").orderByKey(), OrderModel.class).build();
        adapter = new ViewOrdersAdapter(ViewOrders.this, options);


    }

    @Override
    public void onStart() {
        super.onStart();

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