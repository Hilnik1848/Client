package com.example.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.nameTxV.setText(order.getName());
        holder.dateTxV.setText(order.getDate());
        holder.catNameTxV.setText(order.getCategory().getName());
        String url = "https://jlspqghcxxwxjzzpflrf.supabase.co/storage/v1/object/public/imegeorder/";
        Glide.with(context)
                .load(url + order.getAvatar_name())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.catColor.setBackgroundColor(Color.parseColor(order.getCategory().getColor()));
    }
@Override
public int getItemCount() {
    return orderList.size();
}

public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView nameTxV, dateTxV, catNameTxV;
    ImageView imageView;
    View catColor;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTxV = itemView.findViewById(R.id.nameTextView);
        dateTxV = itemView.findViewById(R.id.dateTextView);
        catNameTxV = itemView.findViewById(R.id.categoryTextView);
        imageView = itemView.findViewById(R.id.imageView);
        catColor = itemView.findViewById(R.id.line);
    }}
}