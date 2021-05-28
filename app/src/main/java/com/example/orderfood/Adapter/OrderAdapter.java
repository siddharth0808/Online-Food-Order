package com.example.orderfood.Adapter;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Interface.ItemClickListner;
import com.example.orderfood.POJO.CommonModel;
import com.example.orderfood.POJO.Request;
import com.example.orderfood.R;
import com.example.orderfood.ViewOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OrderAdapter  extends FirebaseRecyclerAdapter<Request,OrderAdapter.OrderViewHolder>{

    FragmentActivity context;
    public OrderAdapter(FragmentActivity ordersFragment, @NonNull FirebaseRecyclerOptions options) {
        super(options);
        this.context=ordersFragment;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
        holder.txtOrderId.setText(getRef(position).getKey());
        holder.txtOrderStatus.setText(CommonModel.convertCodeToStatus(model.getStatus()));
        holder.txtOrderPhone.setText(model.getPhone());
        holder.txtOrderPrice.setText(model.getTotal());
      /*  holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, ViewOrders.class);
                context.startActivity(i);
            }
        });*/
        holder.status_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_layout, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }




    public class OrderViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
        public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderPrice;
        public CardView card;
        public ImageView status_change;

        private ItemClickListner itemClickListner;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.order_id);
            txtOrderStatus = itemView.findViewById(R.id.order_status);
            txtOrderPhone = itemView.findViewById(R.id.order_name);
            txtOrderPrice = itemView.findViewById(R.id.order_price);
            card=itemView.findViewById(R.id.card);
            status_change=itemView.findViewById(R.id.status_change);

            itemView.setOnClickListener(this);

        }

        public  void setItemClickListner(ItemClickListner itemClickListner){
            this.itemClickListner=itemClickListner;
        }
        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");

            menu.add(0,0,getAdapterPosition(),"Update");
            menu.add(0,1,getAdapterPosition(),"Delete");
        }
    }
}
