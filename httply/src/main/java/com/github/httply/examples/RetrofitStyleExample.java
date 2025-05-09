package com.github.httply.examples;

import android.content.Context;
import android.util.Log;

import com.github.httply.examples.model.Post;
import com.github.httply.retra.Call;
import com.github.httply.retra.Callback;
import com.github.httply.retra.GsonConverterFactory;
import com.github.httply.retra.Response;
import com.github.httply.retra.Retra;

import java.io.IOException;
import java.util.List;

/**
 * Example of using the Retra API in a Retrofit-style way.
 */
public class RetrofitStyleExample {
    private static final String TAG = "RetrofitStyleExample";
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    /**
     * Example of using the Retra API with synchronous calls.
     */
    public void synchronousExample() {
        // Create a Retra instance
        Retra retra = new Retra.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an implementation of the API interface
        JsonPlaceholderApi api = retra.create(JsonPlaceholderApi.class);

        // Make a synchronous call
        try {
            List<Post> posts = api.getPosts().execute();
            for (Post post : posts) {
                Log.d(TAG, "Post: " + post.getTitle());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    /**
     * Example of using the Retra API with asynchronous calls.
     */
    public void asynchronousExample() {
        // Create a Retra instance
        Retra retra = new Retra.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an implementation of the API interface
        JsonPlaceholderApi api = retra.create(JsonPlaceholderApi.class);

        // Make an asynchronous call
        Call<List<Post>> call = api.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    for (Post post : posts) {
                        Log.d(TAG, "Title: " + post.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    /**
     * Example of getting a specific post.
     */
    public void getPostExample() {
        // Create a Retra instance
        Retra retra = new Retra.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an implementation of the API interface
        JsonPlaceholderApi api = retra.create(JsonPlaceholderApi.class);

        // Make an asynchronous call to get a specific post
        Call<Post> call = api.getPost(1);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    Log.d(TAG, "Post #1: " + post.getTitle());
                    Log.d(TAG, "Body: " + post.getBody());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}
