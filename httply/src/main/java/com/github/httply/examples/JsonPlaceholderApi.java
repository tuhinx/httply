package com.github.httply.examples;

import com.github.httply.examples.model.Post;
import com.github.httply.retra.Call;
import com.github.httply.retra.annotations.GET;
import com.github.httply.retra.annotations.Path;

import java.util.List;

/**
 * Example API interface for the JSONPlaceholder API.
 */
public interface JsonPlaceholderApi {
    /**
     * Get all posts.
     */
    @GET("posts")
    Call<List<Post>> getPosts();
    
    /**
     * Get a specific post by ID.
     */
    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int id);
}
