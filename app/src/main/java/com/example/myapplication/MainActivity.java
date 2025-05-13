package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapters.OrdersAdapter;
import com.example.myapplication.Models.Order;
import com.example.myapplication.Models.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView userAvatarImV;
    private TextView userNameTxV;
    private RecyclerView ordersRecV;

    private void init() {
        userAvatarImV = findViewById(R.id.avatarIV);
        userNameTxV = findViewById(R.id.usernameTxV);
        ordersRecV = findViewById(R.id.ordersRecV);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        init();
        //TODO
        DataBinding.saveBearerToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw");
        DataBinding.saveUuidUser("6a86d7e2-b7cb-4fbd-ae22-cb656e68250f");
        //TODO
        getAllOrders();
        getCurrentUser();
    }
    private void getCurrentUser() {
        SupabaseClient supabaseClient = new SupabaseClient();
        supabaseClient.fetchCurrentUser(new SupabaseClient.SBC_Callback() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(()->{
                    Log.e("fetchAllUserOrders:onFailure" , e.getLocalizedMessage());
                });
            }

            @Override
            public void onResponse(String responseBody) {
                runOnUiThread(() -> {
                    Log.e("fetchAllUserOrders:onResponse", responseBody);

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Profile>>() {}.getType();
                    List<Profile> profiles = gson.fromJson(responseBody, type);

                    if (profiles != null && !profiles.isEmpty()) {
                        Profile profile = profiles.get(0);
                        String url = "https://jlspqghcxxwxjzzpflrf.supabase.co/storage/v1/object/public/avatars/";
                        Glide.with(getApplicationContext())
                                .load(url + profile.getAvatar_url())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_background)
                                .into(userAvatarImV);
                        userNameTxV.setText(profile.getUsername());
                    }
                });
            }
        });
    }
    private void getAllOrders() {
        SupabaseClient supabaseClient = new SupabaseClient();
        supabaseClient.fetchAllUserOrders(new SupabaseClient.SBC_Callback() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(()->{
                    Log.e("getAllOrders:onFailure" , e.getLocalizedMessage());
                });
            }

            @Override
            public void onResponse(String responseBody) {
                runOnUiThread(() -> {
                    Log.e("getAllOrders:onResponse", responseBody);

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Order>>() {}.getType();
                    List<Order> orderList = gson.fromJson(responseBody, type);

                    OrdersAdapter ordersAdapter = new OrdersAdapter(getApplicationContext(), orderList);
                    ordersRecV.setAdapter(ordersAdapter);
                    ordersRecV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                });
            }
        });
    }
}