package com.github.httply.examples;

import android.content.Context;
import android.util.Log;

import com.github.httply.Httply;
import com.github.httply.voltra.JsonArrayRequest;
import com.github.httply.voltra.JsonObjectRequest;
import com.github.httply.voltra.Request;
import com.github.httply.voltra.RequestQueue;
import com.github.httply.voltra.StringRequest;
import com.github.httply.voltra.VoltraError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Example of using the Voltra-style API.
 */
public class VoltraExample {

    /**
     * Example usage.
     */
    public void example(Context context) {
        // Create a request queue
        RequestQueue queue = Httply.newRequestQueue(context);

        // Make a JSON object request (original style)
        String userUrl = "https://api.github.com/users/octocat";
        JsonObjectRequest userRequest = new JsonObjectRequest(userUrl,
                response -> {
                    // Handle successful response
                    Log.d("VoltraExample", "User: " + response.toString());
                },
                error -> {
                    // Handle error
                    Log.e("VoltraExample", "Error fetching user", error);
                });

        // Add the request to the queue
        queue.add(userRequest);

        // Make a JSON object request (new style with Method constant)
        String userUrl2 = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
        JsonObjectRequest userRequest2 = new JsonObjectRequest(
                Request.Method.GET,
                userUrl2,
                null, // No request body for GET
                (JsonObjectRequest.Response.Listener<JSONObject>) response -> {
                    // Handle the JSON object response
                    try {
                        String name = response.getString("name");
                        String category = response.getString("category");
                        Log.d("VoltraExample", "Food: " + name + ", Category: " + category);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (JsonObjectRequest.Response.ErrorListener) error -> {
                    // Handle error
                    error.printStackTrace();
                });

        // Add the request to the queue
        queue.add(userRequest2);

        // Make a JSON array request (original style)
        String reposUrl = "https://api.github.com/users/octocat/repos";
        JsonArrayRequest reposRequest = new JsonArrayRequest(reposUrl,
                response -> {
                    // Handle successful response
                    Log.d("VoltraExample", "Repos: " + response.toString());
                },
                error -> {
                    // Handle error
                    Log.e("VoltraExample", "Error fetching repos", error);
                });

        // Add the request to the queue
        queue.add(reposRequest);

        // Make a JSON array request (new style with Method constant)
        String foodsUrl = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
        JsonArrayRequest foodsRequest = new JsonArrayRequest(
                Request.Method.GET,
                foodsUrl,
                null, // No request body for GET
                (JsonObjectRequest.Response.Listener<JSONArray>) response -> {
                    // Handle the JSON array response
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject food = response.getJSONObject(i);
                            String name = food.getString("name");
                            String category = food.getString("category");
                            Log.d("VoltraExample", "Food " + i + ": " + name + ", " + category);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (JsonObjectRequest.Response.ErrorListener) error -> {
                    // Handle error
                    error.printStackTrace();
                });

        // Add the request to the queue
        queue.add(foodsRequest);

        // Make a string request (original style)
        String searchUrl = "https://api.github.com/search/repositories?q=android";
        StringRequest searchRequest = new StringRequest(searchUrl,
                response -> {
                    // Handle successful response
                    Log.d("VoltraExample", "Search: " + response);
                },
                error -> {
                    // Handle error
                    Log.e("VoltraExample", "Error searching repos", error);
                });

        // Add the request to the queue
        queue.add(searchRequest);

        // Make a string request (new style with Method constant)
        String dataUrl = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
        StringRequest dataRequest = new StringRequest(
                Request.Method.GET,
                dataUrl,
                null, // No request body for GET
                null, // No content type needed for GET
                (JsonObjectRequest.Response.Listener<String>) response -> {
                    // Handle the string response
                    Log.d("VoltraExample", "Raw data: " + response);
                },
                (JsonObjectRequest.Response.ErrorListener) error -> {
                    // Handle error
                    error.printStackTrace();
                });

        // Add the request to the queue
        queue.add(dataRequest);
    }
}
