<div align="center">

<img src="https://img.shields.io/badge/%F0%9F%93%A1-HTTPLY-4285F4?style=for-the-badge&labelColor=555555&logoColor=white" alt="Httply" height="60"/>

<h3>A zero-dependency HTTP networking library for Android</h3>

<p align="center">
  <a href="https://developer.android.com"><img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white" alt="Platform"></a>
  <a href="https://developer.android.com"><img src="https://img.shields.io/badge/Min%20SDK-24-3DDC84?style=flat-square&logo=android&logoColor=white" alt="Min SDK"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow?style=flat-square" alt="License"></a>
  <a href="https://jitpack.io/#tuhinx/httply"><img src="https://img.shields.io/badge/Version-1.0.0-blue?style=flat-square" alt="Version"></a>
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

<table>
<tr>
<th width="50%">Gradle</th>
<th width="50%">Kotlin DSL</th>
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
    implementation 'com.github.tuhinx:httply:1.0.0'
}
```

</td>
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
    implementation("com.github.tuhinx:httply:1.0.0")
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
public interface ApiService {
    @GET("users/{user}")
    JSONObject getUser(@Path("user") String user);

    @GET("search/repositories")
    HttpResponse searchRepos(@Query("q") String query);

    @POST("users/{user}/repos")
    JSONObject createRepo(@Path("user") String user, @Body JSONObject body);
}
```

#### Step 2: Create a service factory and use the interface

```java
// Create a service factory
ServiceFactory factory = Httply.newServiceFactory()
        .baseUrl("https://api.example.com/")
        .build();

// Create an implementation of the API interface
ApiService api = factory.create(ApiService.class);

// Make requests
try {
    // Get a user by username
    JSONObject user = api.getUser("username");
    System.out.println("User: " + user.getString("name"));

    // Search repositories with a query parameter
    HttpResponse response = api.searchRepos("android");
    if (response.isSuccessful()) {
        JSONObject result = new JSONObject(response.body().string());
        System.out.println("Total count: " + result.getInt("total_count"));
    }
} catch (Exception e) {
    e.printStackTrace();
}
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
        new Response.Listener<JSONObject>() {
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
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error loading object", Toast.LENGTH_SHORT).show();
            }
        }
);
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
        new Response.Listener<JSONArray>() {
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
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error loading array", Toast.LENGTH_SHORT).show();
            }
        }
);
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
        null, // No request body for GET
        null, // No content type needed for GET
        response -> {
            // Handle the string response
            Log.d("StringRequest", "Raw data: " + response);

            // You can then parse the string response as needed
            // For example, if it's JSON:
            try {
                JSONObject jsonObject = new JSONObject(response);
                String name = jsonObject.getString("name");
                String category = jsonObject.getString("category");
                Log.d("StringRequest", "Parsed: " + name + ", " + category);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            // Handle any errors
            error.printStackTrace();
        }
);

// Add the request to the queue to execute it
queue.add(stringRequest);
```

```java
// GET request for a string response (anonymous inner class style)
String url = "https://api.example.com/message";

StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        url,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Response: " + response, Toast.LENGTH_SHORT).show();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VoltraError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error fetching string", Toast.LENGTH_SHORT).show();
            }
        }
);
requestQueue.add(stringRequest);
```

</details>

## ⚙️ Advanced Configuration

<div align="center">
  <img src="https://img.shields.io/badge/Configuration-Advanced-orange?style=for-the-badge&labelColor=555555" alt="Advanced Configuration" />
</div>

### 🔧 Custom HTTP Client

Httply allows you to customize the HTTP client to suit your specific needs:

```java
// Create a custom HTTP client with advanced configuration
HttpClient client = Httply.newHttpClient()
        .connectTimeout(15, TimeUnit.SECONDS)  // Set connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Set read timeout
        .followRedirects(true)                 // Enable following redirects
        .build();
```

<table>
<tr>
<th width="50%">Use with Retra API</th>
<th width="50%">Use with Voltra API</th>
</tr>
<tr>
<td>

```java
// Configure a ServiceFactory with the custom client
ServiceFactory factory = Httply.newServiceFactory()
        .baseUrl("https://api.example.com/")
        .client(client)
        .build();

// Use the factory to create API interfaces
ApiService api = factory.create(ApiService.class);
```

</td>
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

---

<div align="center">

<b>Made with ❤️ by Tuhinx </b>

  </p>
</div>
