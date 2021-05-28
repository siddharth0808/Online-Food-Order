package com.example.orderfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.POJO.OrderModel;
import com.example.orderfood.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewOrdersAdapter extends FirebaseRecyclerAdapter<OrderModel,ViewOrdersAdapter.OrdersViewHolder>  {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public ViewOrdersAdapter(Activity activity,@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
        this.context=activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull OrderModel model) {
        holder.Pname.setText(model.getProductName());
        holder.price.setText(model.getPrice());
        holder.Pquant.setText(model.getQuantity());
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_layout, parent, false);
        return new ViewOrdersAdapter.OrdersViewHolder(view);
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView Pname, price,Pquant;
        public CardView card;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            Pname = itemView.findViewById(R.id.Pname);
            price = itemView.findViewById(R.id.price);
            Pquant= itemView.findViewById(R.id.Pquant);
            card=itemView.findViewById(R.id.card);

        }
    }

}
