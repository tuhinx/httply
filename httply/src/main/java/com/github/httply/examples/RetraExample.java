package com.github.httply.examples;

import android.content.Context;
import android.util.Log;

import com.github.httply.Httply;
import com.github.httply.core.HttpResponse;
import com.github.httply.retra.Call;
import com.github.httply.retra.ServiceFactory;
import com.github.httply.retra.annotations.GET;
import com.github.httply.retra.annotations.Path;
import com.github.httply.retra.annotations.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Example of using the Retra-style API.
 */
public class RetraExample {

    /**
     * Example API interface.
     */
    public interface GitHubApi {
        @GET("users/{user}")
        Call<JSONObject> getUser(@Path("user") String user);

        @GET("users/{user}/repos")
        Call<JSONArray> listRepos(@Path("user") String user);

        @GET("search/repositories")
        Call<HttpResponse> searchRepos(@Query("q") String query);
    }

    /**
     * Example usage.
     */
    public void example() {
        // Create a service factory
        ServiceFactory factory = Httply.newServiceFactory()
                .baseUrl("https://api.github.com/")
                .build();

        // Create a service instance
        GitHubApi api = factory.create(GitHubApi.class);

        // Use an executor for background operations
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Make API calls
                JSONObject user = api.getUser("octocat").execute();
                Log.d("RetraExample", "User: " + user.toString());

                JSONArray repos = api.listRepos("octocat").execute();
                Log.d("RetraExample", "Repos: " + repos.toString());

                HttpResponse response = api.searchRepos("android").execute();
                Log.d("RetraExample", "Search: " + response.body().string());
            } catch (Exception e) {
                Log.e("RetraExample", "Error in API call", e);
            }
        });
    }
}
