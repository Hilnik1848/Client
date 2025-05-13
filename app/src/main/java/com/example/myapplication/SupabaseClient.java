package com.example.myapplication;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupabaseClient {
    public interface SBC_Callback{
        public void onFailure(IOException e);
        public void onResponse(String responseBody);

    }
    public static String DOMAIN_NAME = "https://jlspqghcxxwxjzzpflrf.supabase.co/";
    public static String REST_PATH = "rest/v1/";
    public static String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw";
    //public static String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw";

    OkHttpClient client = new OkHttpClient();

    public void fetchCurrentUser(final SBC_Callback callback){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(DOMAIN_NAME + REST_PATH +"profiles?select=*&id=eq." + DataBinding.getUuidUser())
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", DataBinding.getBearerToken())
               // .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw")
               // .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw")
               // .addHeader("Range", "0-9")
                // .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    callback.onResponse(responseBody);
                }else {
                    callback.onFailure(new IOException("Ошибка сервера:с " + response));
                }
            }
        });
    }

    public void fetchAllUserOrders(final SBC_Callback callback){

        Request request = new Request.Builder()
                .url(DOMAIN_NAME + REST_PATH + "orders?select=*,category(*)&id_profile=eq."+ DataBinding.getUuidUser())
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", DataBinding.getBearerToken())

                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    callback.onResponse(responseBody);
                }else {
                    callback.onFailure(new IOException("Ошибка сервера:с " + response));
                }
            }
        });
    }

    public void fetchAllOrders(final SBC_Callback callback){
     //   OkHttpClient client = new OkHttpClient().newBuilder()
     //           .build();

        Request request = new Request.Builder()
              //  .url("https://jlspqghcxxwxjzzpflrf.supabase.co/rest/v1/orders?select=*,category(*)")
                .url(DOMAIN_NAME + REST_PATH + "orders?select=*,category(*)")
                //.addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw")

                .addHeader("apikey", API_KEY)
              //  .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impsc3BxZ2hjeHh3eGp6enBmbHJmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMzI5MjksImV4cCI6MjA2MjYwODkyOX0.zOLj7zlQfyMTVmUZP8ERMDpyaMrtLEIcVTm37tYPuYw")
                .addHeader("Authorization", DataBinding.getBearerToken())

                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    callback.onResponse(responseBody);
                }else {
                    callback.onFailure(new IOException("Ошибка сервера:с " + response));
                }
            }
        });
    }
}
