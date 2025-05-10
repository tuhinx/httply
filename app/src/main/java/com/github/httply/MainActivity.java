package com.github.httply;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.httply.retra.GsonConverterFactory;
import com.github.httply.retra.Retra;
import com.github.httply.retra.RetraResponse;
import com.github.httply.retra.annotations.Call;
import com.github.httply.retra.annotations.Callback;

import com.github.httply.voltra.Request;
import com.github.httply.voltra.RequestQueue;
import com.github.httply.voltra.StringRequest;
import com.github.httply.voltra.VoltraError;
import com.github.httply.voltra.VoltraResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    ListView listView;
    ArrayList<HashMap<String, String>> foodList;
    private static final String BASE_URL = "https://mocki.io/";
    private static final String FULL_URL = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
    private static final String TAG = "MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        foodList = new ArrayList<>();

        Button btnRetraCall = findViewById(R.id.btnRetraCall);
        Button btnVoltraCall = findViewById(R.id.btnVoltraCall);

        requestQueue = Httply.newRequestQueue(this);

        btnRetraCall.setOnClickListener(v -> {
            foodList.clear();
            updateAdapter();
            Toast.makeText(this, "Loading data with Retra...", Toast.LENGTH_SHORT).show();
            callRetraApi();
        });

        btnVoltraCall.setOnClickListener(v -> {
            foodList.clear();
            updateAdapter();
            Toast.makeText(this, "Loading data with Voltra...", Toast.LENGTH_SHORT).show();
            callVoltraApi();
        });
    }

    private void updateAdapter() {
        if (listView.getAdapter() != null) {
            ((FoodAdapter) listView.getAdapter()).notifyDataSetChanged();
        } else {
            listView.setAdapter(new FoodAdapter(MainActivity.this, foodList));
        }
    }

    private void callVoltraApi() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                FULL_URL,
                new VoltraResponse.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Raw response: " + response);
                        runOnUiThread(() -> {
                            if (response != null && !response.trim().isEmpty()) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject item = jsonArray.getJSONObject(i);
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("name", item.getString("name"));
                                        map.put("category", item.getString("category"));
                                        map.put("price", "$" + item.getString("price"));
                                        foodList.add(map);
                                    }
                                    updateAdapter();
                                    Toast.makeText(MainActivity.this, "Loaded " + foodList.size() + " items.", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                    Toast.makeText(MainActivity.this, "Invalid JSON format", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Empty response", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },
                new VoltraResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(VoltraError error) {
                        Log.e(TAG, "Voltra Error: " + error.getMessage(), error);
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Voltra Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



    private void callRetraApi() {
        Retra retra = new Retra.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodApi api = retra.create(FoodApi.class);
        Call<List<FoodItem>> call = api.getFoodItems();

        call.enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, RetraResponse<List<FoodItem>> response) {
                runOnUiThread(() -> {
                    if (response.isSuccessful() && response.body() != null) {
                        for (FoodItem item : response.body()) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", item.getName());
                            map.put("category", item.getCategory());
                            map.put("price", "$" + item.getPrice());
                            foodList.add(map);
                        }
                        FoodAdapter adapter = new FoodAdapter(MainActivity.this, foodList);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Retra: Empty response", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Retra Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    callVoltraApi(); // fallback
                });
            }
        });
    }
}
