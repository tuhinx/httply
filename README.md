<div align="center">

<img src="https://img.shields.io/badge/%F0%9F%93%A1-HTTPLY-4285F4?style=for-the-badge&labelColor=555555&logoColor=white" alt="Httply" height="60"/>

<h3>A zero-dependency HTTP networking library for Android</h3>

<p align="center">
  <a href="https://developer.android.com"><img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white" alt="Platform"></a>
  <a href="https://developer.android.com"><img src="https://img.shields.io/badge/Min%20SDK-24-3DDC84?style=flat-square&logo=android&logoColor=white" alt="Min SDK"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow?style=flat-square" alt="License"></a>
  <a href="https://jitpack.io/#tuhinx/httply"><img src="https://img.shields.io/badge/Version-1.0.5-blue?style=flat-square" alt="Version"></a>
  <a href="https://jitpack.io/#tuhinx/httply"><img src="https://img.shields.io/badge/JitPack-available-success?style=flat-square&logo=jitpack&logoColor=white" alt="JitPack"></a>
</p>

</div>

<p align="center">
  <b>Httply</b> is a modern, lightweight HTTP networking library for Android that supports both <b>Retra-style</b> (annotation-based) and <b>Voltra-style</b> (request queue-based) APIs. Built with zero external dependencies, it's designed to be simple, efficient, and easy to use.
</p>

## ✨ Features

<div align="left">
  <table>
    <tr>
      <td align="center">🔌</td>
      <td><b>Zero External Dependencies</b> - Built entirely with Java standard libraries</td>
    </tr>
    <tr>
      <td align="center">🧩</td>
      <td>
        <b>Dual API Styles</b><br>
        • <b>Retra API</b> - Declarative, annotation-based API for defining HTTP endpoints<br>
        • <b>Voltra API</b> - Imperative, queue-based API for making requests
      </td>
    </tr>
    <tr>
      <td align="center">☕</td>
      <td><b>Full Java and Kotlin Support</b> - Works seamlessly with both languages</td>
    </tr>
    <tr>
      <td align="center">🪶</td>
      <td><b>Lightweight</b> - Minimal footprint in your app</td>
    </tr>
    <tr>
      <td align="center">🔄</td>
      <td><b>Connection Pooling</b> - Reuses connections for better performance</td>
    </tr>
    <tr>
      <td align="center">🧠</td>
      <td><b>Configurable Caching</b> - Control whether requests should be cached</td>
    </tr>
    <tr>
      <td align="center">⏱️</td>
      <td><b>Configurable Timeouts</b> - Set connect and read timeouts</td>
    </tr>
    <tr>
      <td align="center">🧵</td>
      <td><b>Customizable Thread Pools</b> - Control the number of threads used for requests</td>
    </tr>
    <tr>
      <td align="center">📋</td>
      <td><b>JSON Support</b> - Built-in JSON parsing with standard org.json library</td>
    </tr>
  </table>
</div>

## 🛡️ Zero External Dependencies

<p align="center">
  <img src="https://img.shields.io/badge/Dependencies-Zero-success?style=for-the-badge&labelColor=555555" alt="Zero Dependencies" />
</p>

Httply is designed with a strong focus on minimizing dependencies in your Android projects:

<div class="dependency-benefits">
  <div>
    <h3>🔒 Security & Reliability</h3>
    <ul>
      <li><b>No third-party libraries</b> - Reduces risk of dependency conflicts and security vulnerabilities</li>
      <li><b>Better long-term stability</b> - No risk of external dependencies becoming deprecated or unmaintained</li>
      <li><b>Consistent behavior</b> - No unexpected changes from third-party library updates</li>
    </ul>
  </div>

  <div>
    <h3>⚡ Performance Benefits</h3>
    <ul>
      <li><b>Smaller APK size</b> - No additional libraries means your app's size stays smaller</li>
      <li><b>Faster build times</b> - Fewer dependencies lead to quicker compilation and build processes</li>
      <li><b>Reduced method count</b> - Helps stay under the 65K method limit without requiring multidex</li>
    </ul>
  </div>

  <div>
    <h3>🧰 Implementation</h3>
    <ul>
      <li><b>Built on Java standard libraries</b> - Uses only core Java and Android libraries</li>
      <li><b>Clean architecture</b> - Easy to integrate without adding bloat or complexity</li>
    </ul>
  </div>
</div>

## 📦 Installation

<div align="center">
  <img src="https://img.shields.io/badge/JitPack-Integration-4c1?style=for-the-badge&logo=jitpack&logoColor=white&labelColor=555555" alt="JitPack Integration" />
</div>

<table width="100%">
<tr>
<th width="100%">Gradle</th>
</tr>
<tr>
<td>

**Step 1:** Add JitPack repository to your project-level build.gradle:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2:** Add the dependency to your app-level build.gradle:

```groovy
dependencies {
    implementation 'com.github.tuhinx:httply:1.0.5'
}
```

</td>
</tr>
<tr>
<th width="100%">Kotlin DSL</th>
</tr>
<tr>
<td>

**Step 1:** Add JitPack repository to your settings.gradle.kts:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Step 2:** Add the dependency to your app-level build.gradle.kts:

```kotlin
dependencies {
    implementation("com.github.tuhinx:httply:1.0.5")
}
```

</td>
</tr>
</table>

## 🚀 Usage

<div align="center">
  <h3>Httply provides two powerful API styles to suit your needs</h3>
</div>

<table align="center">
<tr>
<td width="50%" align="center">
  <h3>🔄 Retra API</h3>
  <p>Annotation-based, declarative style</p>
  <img src="https://img.shields.io/badge/Style-Declarative-blue?style=flat-square" alt="Declarative" />
</td>
<td width="50%" align="center">
  <h3>🔄 Voltra API</h3>
  <p>Queue-based, imperative style</p>
  <img src="https://img.shields.io/badge/Style-Imperative-purple?style=flat-square" alt="Imperative" />
</td>
</tr>
</table>

### 📝 Retra API

The Retra API provides a clean, annotation-based approach for defining HTTP endpoints.

#### Step 1: Define an interface with annotated methods

```java
// Define model classes for your API responses
public class FoodItem {
    private String name;
    private String category;
    private String price;

    // Getters
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getPrice() { return price; }
}

// Define your API interface
public interface FoodApi {
    @GET("v1/861a8605-a6e0-408d-8feb-ab303b15f59f")
    Call<List<FoodItem>> getFoodItems();
}
```

#### Step 2: Create a service factory and use the interface

```java
// Create a Retra instance using the builder pattern
Retra retra = new Retra.Builder()
        .baseUrl("https://mocki.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

// Create an implementation of the API interface
FoodApi api = retra.create(FoodApi.class);

// Asynchronous requests with callbacks
Call<List<FoodItem>> call = api.getFoodItems();
call.enqueue(new Callback<List<FoodItem>>() {
    @Override
    public void onResponse(Call<List<FoodItem>> call, RetraResponse<List<FoodItem>> response) {
        if (response.isSuccessful() && response.body() != null) {
            // Process the response on the UI thread
            for (FoodItem item : response.body()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", item.getName());
                map.put("category", item.getCategory());
                map.put("price", "$" + item.getPrice());
                foodList.add(map);
            }

            // Update the UI
            FoodAdapter adapter = new FoodAdapter(context, foodList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Retra: Empty response", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<List<FoodItem>> call, Throwable t) {
        Toast.makeText(context, "Retra Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        // Fallback to Voltra API if Retra fails
        callVoltraApi();
    }
});
```

### 🔄 Voltra API

The Voltra API provides a request queue-based approach, perfect for making multiple HTTP requests.

#### Step 1: Create a RequestQueue

```java
// Initialize a RequestQueue with the default configuration
RequestQueue queue = Httply.newRequestQueue(context);
```

#### Step 2: Make requests using the queue

<details open>
<summary><b>📊 JsonObjectRequest Example</b></summary>

```java
// GET request for a JSON object (lambda style)
String url = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
        Request.Method.GET,
        url,
        null, // No request body for GET
        response -> {
            try {
                // Parse the JSON response
                String name = response.getString("name");
                String category = response.getString("category");

                // Use the data
                System.out.println("Food: " + name + ", Category: " + category);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            // Handle any errors
            error.printStackTrace();
        }
);

// Disable caching for this request (optional)
jsonObjectRequest.setShouldCache(false);

// Add the request to the queue to execute it
queue.add(jsonObjectRequest);
```

```java
// GET request for a JSON object (anonymous inner class style)
String url = "https://api.example.com/food";
JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        new VoltraResponse.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int id = response.getInt("id");
                    String name = response.getString("name");
                    String category = response.getString("category");
                    double price = response.getDouble("price");

                    Toast.makeText(context,
                            name + " (" + category + ") - $" + price,
                            Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new VoltraResponse.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error loading object", Toast.LENGTH_SHORT).show();
            }
        }
);

jsonObjectRequest.setShouldCache(false);

requestQueue.add(jsonObjectRequest);
```

</details>

<details open>
<summary><b>📋 JsonArrayRequest Example</b></summary>

```java
// GET request for a JSON array (lambda style)
String urlArray = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
        Request.Method.GET,
        urlArray,
        null,
        response -> {
            try {
                // Iterate through the JSON array
                for (int i = 0; i < response.length(); i++) {
                    JSONObject food = response.getJSONObject(i);

                    // Extract data from each object
                    String name = food.getString("name");
                    String category = food.getString("category");

                    // Use the data
                    System.out.println("Food " + i + ": " + name + ", " + category);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            // Handle any errors
            error.printStackTrace();
        }
);

jsonArrayRequest.setShouldCache(false);
// Add the request to the queue to execute it
queue.add(jsonArrayRequest);
```

```java
// GET request for a JSON array (anonymous inner class style)
String url = "https://api.example.com/foods";
JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
        Request.Method.GET,
        url,
        null,
        new VoltraResponse.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject item = response.getJSONObject(i);
                        String name = item.getString("name");
                        double price = item.getDouble("price");

                        int finalI = i;
                        Toast.makeText(context,
                                (finalI + 1) + ". " + name + " - $" + price,
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new VoltraResponse.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error loading array", Toast.LENGTH_SHORT).show();
            }
        }
);
jsonArrayRequest.setShouldCache(false);

requestQueue.add(jsonArrayRequest);
```

</details>

<details open>
<summary><b>📝 StringRequest Example</b></summary>

```java
// GET request for a string response (lambda style)
String url = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";
StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        url,
        response -> {
            Log.d("TAG", "Raw response: " + response);
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
                    Toast.makeText(context, "Loaded " + foodList.size() + " items.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("TAG", "JSON parsing error: " + e.getMessage());
                    Toast.makeText(context, "Invalid JSON format", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Empty response", Toast.LENGTH_SHORT).show();
            }
        },
        error -> {
            Log.e("TAG", "Voltra Error: " + error.getMessage(), error);
            Toast.makeText(context, "Voltra Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
);

// Disable caching for this request
stringRequest.setShouldCache(false);

// Add the request to the queue
queue.add(stringRequest);
```

```java
// GET request for a string response (anonymous inner class style)
String url = "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f";

StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        url,
        new VoltraResponse.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Raw response: " + response);
                if (response != null && !response.trim().isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", item.getString("name"));
                            map.put("category", item.getString("category"));
                            map.put("price", "$" + item.getString("price"));
                            // Add to your data list
                            foodList.add(map);
                        }
                        Toast.makeText(context, "Loaded " + foodList.size() + " items.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e("TAG", "JSON parsing error: " + e.getMessage());
                        Toast.makeText(context, "Invalid JSON format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Empty response", Toast.LENGTH_SHORT).show();
                }
            }
        },
        new VoltraResponse.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                Log.e("TAG", "Voltra Error: " + error.getMessage(), error);
                Toast.makeText(context, "Voltra Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
);

// Disable caching for this request
stringRequest.setShouldCache(false);

// Add the request to the queue
requestQueue.add(stringRequest);
```

</details>

## ⚙️ Advanced Configuration

<div align="center">
  <img src="https://img.shields.io/badge/Configuration-Advanced-orange?style=for-the-badge&labelColor=555555" alt="Advanced Configuration" />
</div>

### 🚀 Performance Optimization

Httply is designed with performance in mind, offering several features to optimize your network operations:

<table>
<tr>
<td align="center">⚡</td>
<td><b>Efficient Resource Usage</b> - Minimizes memory and CPU consumption</td>
</tr>
<tr>
<td align="center">🔄</td>
<td><b>Connection Reuse</b> - Reduces connection establishment overhead</td>
</tr>
<tr>
<td align="center">📦</td>
<td><b>Minimal Footprint</b> - Zero dependencies means smaller APK size</td>
</tr>
<tr>
<td align="center">⏱️</td>
<td><b>Configurable Timeouts</b> - Fine-tune network behavior for your use case</td>
</tr>
</table>

### 🧠 Request Caching

Httply allows you to control whether individual requests should be cached:

```java
// Create a request
StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        "https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f",
        new VoltraResponse.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Process the response
                Log.d("TAG", "Raw response: " + response);
                // Parse JSON and update UI
            }
        },
        new VoltraResponse.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                // Handle errors
                Log.e("TAG", "Voltra Error: " + error.getMessage(), error);
            }
        }
);

// Disable caching for this request
stringRequest.setShouldCache(false);

// Add the request to the queue
requestQueue.add(stringRequest);
```

By default, all requests are cached. Setting `setShouldCache(false)` will:

- Prevent the connection from being reused for future requests
- Force a new connection to be established for this request
- Useful for requests that need fresh data or when troubleshooting network issues

### 🔧 Custom HTTP Client

<div align="center">
  <img src="https://img.shields.io/badge/HTTP%20Client-Customizable-4285F4?style=for-the-badge&labelColor=555555" alt="Customizable HTTP Client" />
</div>

Httply provides a powerful, customizable HTTP client that forms the foundation of both the Retra and Voltra APIs. The client is built on Java's standard `HttpURLConnection` but adds several advanced features:

<table>
<tr>
<td align="center">⏱️</td>
<td><b>Configurable Timeouts</b> - Set custom connect and read timeouts to handle slow networks</td>
</tr>
<tr>
<td align="center">🔄</td>
<td><b>Connection Pooling</b> - Reuse connections to improve performance and reduce latency</td>
</tr>
<tr>
<td align="center">🧵</td>
<td><b>Custom Thread Pools</b> - Configure the executor used for asynchronous requests</td>
</tr>
<tr>
<td align="center">🔀</td>
<td><b>Redirect Control</b> - Enable or disable following HTTP redirects</td>
</tr>
<tr>
<td align="center">🧠</td>
<td><b>Caching Control</b> - Fine-grained control over which requests should be cached</td>
</tr>
</table>

#### Basic Configuration

```java
// Create a custom HTTP client with advanced configuration
HttpClient client = Httply.newHttpClient()
        .connectTimeout(15, TimeUnit.SECONDS)  // Set connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Set read timeout
        .followRedirects(true)                 // Enable following redirects
        .build();
```

#### Advanced Configuration

```java
// Create a custom connection pool
ConnectionPool connectionPool = new ConnectionPool(
        10,                     // Maximum idle connections
        5, TimeUnit.MINUTES     // Keep-alive duration
);

// Create a custom executor for async requests
Executor executor = Executors.newFixedThreadPool(4);

// Create a fully customized HTTP client
HttpClient client = Httply.newHttpClient()
        .connectionPool(connectionPool)
        .executor(executor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(false)  // Disable following redirects
        .build();
```

<table width="100%">
<tr>
<th width="100%">Use with Retra API</th>
</tr>
<tr>
<td>

```java
// Configure a Retra instance with the custom client
Retra retra = new Retra.Builder()
        .baseUrl("https://api.example.com/")
        .client(client)
        .addConverterFactory(JsonConverterFactory.create())
        .build();

// Use Retra to create API interfaces
ApiService api = retra.create(ApiService.class);
```

</td>
</tr>
<tr>
<th width="100%">Use with Voltra API</th>
</tr>
<tr>
<td>

```java
// Configure a RequestQueue with the custom client
RequestQueue queue = Httply.newRequestQueue(
        context,
        client
);

// Use the queue to make requests
queue.add(new StringRequest(...));
```

</td>
</tr>
</table>

#### Connection Pooling

Httply's HTTP client includes a built-in connection pooling mechanism that significantly improves performance by reusing existing connections:

<table>
<tr>
<td align="center">⚡</td>
<td><b>Reduced Latency</b> - Eliminates the overhead of establishing new connections</td>
</tr>
<tr>
<td align="center">🔋</td>
<td><b>Lower Resource Usage</b> - Reduces CPU, memory, and battery consumption</td>
</tr>
<tr>
<td align="center">📈</td>
<td><b>Improved Throughput</b> - Handles more requests with fewer resources</td>
</tr>
<tr>
<td align="center">⚙️</td>
<td><b>Configurable</b> - Customize pool size and connection keep-alive duration</td>
</tr>
</table>

```java
// Create a custom connection pool with 20 max idle connections
// and a 10-minute keep-alive duration
ConnectionPool connectionPool = new ConnectionPool(
        20,                      // Maximum idle connections
        10, TimeUnit.MINUTES     // Keep-alive duration
);

// Use the custom connection pool with your HTTP client
HttpClient client = Httply.newHttpClient()
        .connectionPool(connectionPool)
        .build();
```

#### Thread Pool Customization

Httply allows you to customize the thread pool used for asynchronous requests, giving you control over resource usage and concurrency:

<table>
<tr>
<td align="center">🧵</td>
<td><b>Fixed Thread Pool</b> - Limit the number of concurrent requests</td>
</tr>
<tr>
<td align="center">⏱️</td>
<td><b>Scheduled Executor</b> - Schedule requests to run at specific times</td>
</tr>
<tr>
<td align="center">🔄</td>
<td><b>Single Thread</b> - Ensure requests are processed sequentially</td>
</tr>
<tr>
<td align="center">🚀</td>
<td><b>Cached Thread Pool</b> - Dynamically adjust thread count based on load</td>
</tr>
</table>

```java
// Create a fixed thread pool with 4 threads
Executor fixedPool = Executors.newFixedThreadPool(4);

// Create a single-threaded executor for sequential processing
Executor singleThread = Executors.newSingleThreadExecutor();

// Create a cached thread pool that adjusts based on load
Executor cachedPool = Executors.newCachedThreadPool();

// Use a custom executor with your HTTP client
HttpClient client = Httply.newHttpClient()
        .executor(fixedPool)  // Choose the appropriate executor for your needs
        .build();
```

#### Direct Usage

For advanced use cases, you can also use the HTTP client directly:

```java
// Create an HTTP client
HttpClient client = Httply.newHttpClient().build();

// Create a request
HttpRequest request = new HttpRequest.Builder()
        .url("https://mocki.io/v1/861a8605-a6e0-408d-8feb-ab303b15f59f")
        .method(HttpMethod.GET)
        .shouldCache(true)  // Enable caching (default)
        .build();

// Execute synchronously
try {
    HttpResponse response = client.execute(request);
    if (response.isSuccessful()) {
        String body = response.body().string();
        // Process the response
    }
} catch (IOException e) {
    e.printStackTrace();
}

// Or execute asynchronously
client.executeAsync(request, new HttpClient.Callback() {
    @Override
    public void onResponse(HttpResponse response) {
        // Process the response
    }

    @Override
    public void onFailure(Exception e) {
        // Handle the error
    }
});
```




## 🔍 Troubleshooting

<div align="center">
  <img src="https://img.shields.io/badge/Troubleshooting-Guide-red?style=for-the-badge&labelColor=555555" alt="Troubleshooting Guide" />
</div>

### Common Issues and Solutions

<table>
<tr>
<td align="center">🔌</td>
<td>
<b>Connection Timeout</b><br>
If you're experiencing connection timeouts, try increasing the timeout values:
<pre>HttpClient client = Httply.newHttpClient()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();</pre>
</td>
</tr>
<tr>
<td align="center">🔄</td>
<td>
<b>Stale Connections</b><br>
If you suspect stale connections in the pool, disable caching for critical requests:
<pre>request.setShouldCache(false);</pre>
</td>
</tr>
<tr>
<td align="center">🧵</td>
<td>
<b>Thread Pool Exhaustion</b><br>
If you're making many concurrent requests, consider using a larger thread pool:
<pre>Executor executor = Executors.newFixedThreadPool(8);
HttpClient client = Httply.newHttpClient()
        .executor(executor)
        .build();</pre>
</td>
</tr>
<tr>
<td align="center">🔒</td>
<td>
<b>SSL/TLS Issues</b><br>
For HTTPS connections, ensure your server's certificates are valid and trusted.
</td>
</tr>
</table>

---


## 📄 License

<div align="center">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge&labelColor=555555" alt="MIT License" />
</div>

<details>
<summary>View MIT License Text</summary>

```
MIT License

Copyright (c) 2025 TuhinX

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

</details>

<div align="center">

<b>Made with ❤️ by Tuhinx </b>

  </p>
</div>
