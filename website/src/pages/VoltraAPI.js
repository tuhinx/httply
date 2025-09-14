import React from 'react';
import CopyButton from '../components/CopyButton';
import './VoltraAPI.css';

const VoltraAPI = () => {
  return (
    <div className="voltra-api-page">
      <div className="container">
        <div className="page-header">
          <h1>Voltra API</h1>
          <p>Request queue-based, imperative HTTP API for Android</p>
        </div>

        <section className="content-section">
          <h2>What is Voltra API?</h2>
          <p>
            The Voltra API provides a request queue-based approach, perfect for making multiple HTTP requests efficiently. 
            It's similar to Volley but built with zero external dependencies. You create requests and add them to a queue 
            for execution.
          </p>

          <h2>Basic Setup</h2>
          <p>Here's how to get started with Voltra API:</p>

          <div className="step">
            <h3>Step 1: Create a RequestQueue</h3>
            <p>Initialize a RequestQueue with the default configuration:</p>
            <div className="code-example">
              <CopyButton text={`// Initialize a RequestQueue with the default configuration
RequestQueue queue = Httply.newRequestQueue(context);`} />
              <pre>{`// Initialize a RequestQueue with the default configuration
RequestQueue queue = Httply.newRequestQueue(context);`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Step 2: Make Requests</h3>
            <p>Create and add requests to the queue:</p>
            <div className="code-example">
              <CopyButton text={`// Create a simple GET request
String url = "https://api.example.com/users";
StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        url,
        response -> {
            // Handle successful response
            Log.d("TAG", "Response: " + response);
        },
        error -> {
            // Handle error
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

// Add the request to the queue
queue.add(stringRequest);`} />
              <pre>{`// Create a simple GET request
String url = "https://api.example.com/users";
StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        url,
        response -> {
            // Handle successful response
            Log.d("TAG", "Response: " + response);
        },
        error -> {
            // Handle error
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

// Add the request to the queue
queue.add(stringRequest);`}</pre>
            </div>
          </div>

          <h2>Request Types</h2>
          <p>Voltra API supports different types of requests for various data formats:</p>

          <div className="request-grid">
            <div className="request-item">
              <h3>StringRequest</h3>
              <p>For raw string responses</p>
              <div className="code-example">
                <CopyButton text={`StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/data",
        response -> {
            // Handle string response
            Log.d("TAG", "Response: " + response);
        },
        error -> {
            // Handle error
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

queue.add(stringRequest);`} />
                <pre>{`StringRequest stringRequest = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/data",
        response -> {
            // Handle string response
            Log.d("TAG", "Response: " + response);
        },
        error -> {
            // Handle error
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

queue.add(stringRequest);`}</pre>
              </div>
            </div>

            <div className="request-item">
              <h3>JsonObjectRequest</h3>
              <p>For JSON object responses</p>
              <div className="code-example">
                <CopyButton text={`JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
        Request.Method.GET,
        "https://api.example.com/user/123",
        null, // No request body for GET
        response -> {
            try {
                String name = response.getString("name");
                String email = response.getString("email");
                Log.d("TAG", "Name: " + name + ", Email: " + email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

queue.add(jsonObjectRequest);`} />
                <pre>{`JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
        Request.Method.GET,
        "https://api.example.com/user/123",
        null, // No request body for GET
        response -> {
            try {
                String name = response.getString("name");
                String email = response.getString("email");
                Log.d("TAG", "Name: " + name + ", Email: " + email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

queue.add(jsonObjectRequest);`}</pre>
              </div>
            </div>

            <div className="request-item">
              <h3>JsonArrayRequest</h3>
              <p>For JSON array responses</p>
              <div className="code-example">
                <CopyButton text={`JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
        Request.Method.GET,
        "https://api.example.com/users",
        null,
        response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject user = response.getJSONObject(i);
                    String name = user.getString("name");
                    Log.d("TAG", "User " + i + ": " + name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

queue.add(jsonArrayRequest);`} />
                <pre>{`JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
        Request.Method.GET,
        "https://api.example.com/users",
        null,
        response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject user = response.getJSONObject(i);
                    String name = user.getString("name");
                    Log.d("TAG", "User " + i + ": " + name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
        error -> {
            Log.e("TAG", "Error: " + error.getMessage());
        }
);

queue.add(jsonArrayRequest);`}</pre>
              </div>
            </div>
          </div>

          <h2>HTTP Methods</h2>
          <p>Voltra API supports all standard HTTP methods:</p>

          <div className="method-grid">
            <div className="method-item">
              <h3>GET Request</h3>
              <div className="code-example">
                <CopyButton text={`StringRequest getRequest = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/users",
        response -> {
            // Handle GET response
        },
        error -> {
            // Handle error
        }
);`} />
                <pre>{`StringRequest getRequest = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/users",
        response -> {
            // Handle GET response
        },
        error -> {
            // Handle error
        }
);`}</pre>
              </div>
            </div>

            <div className="method-item">
              <h3>POST Request</h3>
              <div className="code-example">
                <CopyButton text={`// Create JSON object for POST body
JSONObject postData = new JSONObject();
try {
    postData.put("name", "John Doe");
    postData.put("email", "john@example.com");
} catch (JSONException e) {
    e.printStackTrace();
}

JsonObjectRequest postRequest = new JsonObjectRequest(
        Request.Method.POST,
        "https://api.example.com/users",
        postData,
        response -> {
            // Handle POST response
        },
        error -> {
            // Handle error
        }
);`} />
                <pre>{`// Create JSON object for POST body
JSONObject postData = new JSONObject();
try {
    postData.put("name", "John Doe");
    postData.put("email", "john@example.com");
} catch (JSONException e) {
    e.printStackTrace();
}

JsonObjectRequest postRequest = new JsonObjectRequest(
        Request.Method.POST,
        "https://api.example.com/users",
        postData,
        response -> {
            // Handle POST response
        },
        error -> {
            // Handle error
        }
);`}</pre>
              </div>
            </div>

            <div className="method-item">
              <h3>PUT Request</h3>
              <div className="code-example">
                <CopyButton text={`JSONObject putData = new JSONObject();
try {
    putData.put("name", "Jane Doe");
    putData.put("email", "jane@example.com");
} catch (JSONException e) {
    e.printStackTrace();
}

JsonObjectRequest putRequest = new JsonObjectRequest(
        Request.Method.PUT,
        "https://api.example.com/users/123",
        putData,
        response -> {
            // Handle PUT response
        },
        error -> {
            // Handle error
        }
);`} />
                <pre>{`JSONObject putData = new JSONObject();
try {
    putData.put("name", "Jane Doe");
    putData.put("email", "jane@example.com");
} catch (JSONException e) {
    e.printStackTrace();
}

JsonObjectRequest putRequest = new JsonObjectRequest(
        Request.Method.PUT,
        "https://api.example.com/users/123",
        putData,
        response -> {
            // Handle PUT response
        },
        error -> {
            // Handle error
        }
);`}</pre>
              </div>
            </div>

            <div className="method-item">
              <h3>DELETE Request</h3>
              <div className="code-example">
                <CopyButton text={`StringRequest deleteRequest = new StringRequest(
        Request.Method.DELETE,
        "https://api.example.com/users/123",
        response -> {
            // Handle DELETE response
        },
        error -> {
            // Handle error
        }
);`} />
                <pre>{`StringRequest deleteRequest = new StringRequest(
        Request.Method.DELETE,
        "https://api.example.com/users/123",
        response -> {
            // Handle DELETE response
        },
        error -> {
            // Handle error
        }
);`}</pre>
              </div>
            </div>
          </div>

          <h2>Request Configuration</h2>
          <p>Customize your requests with various configuration options:</p>

          <div className="step">
            <h3>Caching Control</h3>
            <p>Control whether requests should be cached:</p>
            <div className="code-example">
              <CopyButton text={`StringRequest request = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/data",
        response -> {
            // Handle response
        },
        error -> {
            // Handle error
        }
);

// Disable caching for this request
request.setShouldCache(false);

queue.add(request);`} />
              <pre>{`StringRequest request = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/data",
        response -> {
            // Handle response
        },
        error -> {
            // Handle error
        }
);

// Disable caching for this request
request.setShouldCache(false);

queue.add(request);`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Custom Headers</h3>
            <p>Add custom headers to your requests:</p>
            <div className="code-example">
              <CopyButton text={`StringRequest request = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/protected",
        response -> {
            // Handle response
        },
        error -> {
            // Handle error
        }
) {
    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your-token-here");
        headers.put("Content-Type", "application/json");
        return headers;
    }
};

queue.add(request);`} />
              <pre>{`StringRequest request = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/protected",
        response -> {
            // Handle response
        },
        error -> {
            // Handle error
        }
) {
    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your-token-here");
        headers.put("Content-Type", "application/json");
        return headers;
    }
};

queue.add(request);`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Request Priority</h3>
            <p>Set request priority for queue ordering:</p>
            <div className="code-example">
              <CopyButton text={`StringRequest highPriorityRequest = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/urgent",
        response -> {
            // Handle response
        },
        error -> {
            // Handle error
        }
);

// Set high priority
highPriorityRequest.setPriority(Request.Priority.HIGH);

queue.add(highPriorityRequest);`} />
              <pre>{`StringRequest highPriorityRequest = new StringRequest(
        Request.Method.GET,
        "https://api.example.com/urgent",
        response -> {
            // Handle response
        },
        error -> {
            // Handle error
        }
);

// Set high priority
highPriorityRequest.setPriority(Request.Priority.HIGH);

queue.add(highPriorityRequest);`}</pre>
            </div>
          </div>

          <h2>Advanced Configuration</h2>
          <p>Customize your RequestQueue with advanced options:</p>

          <div className="step">
            <h3>Custom HTTP Client</h3>
            <p>Use a custom HTTP client with specific configurations:</p>
            <div className="code-example">
              <CopyButton text={`// Create a custom HTTP client
HttpClient client = Httply.newHttpClient()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(true)
        .build();

// Use the custom client with RequestQueue
RequestQueue queue = Httply.newRequestQueue(context, client);`} />
              <pre>{`// Create a custom HTTP client
HttpClient client = Httply.newHttpClient()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(true)
        .build();

// Use the custom client with RequestQueue
RequestQueue queue = Httply.newRequestQueue(context, client);`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Connection Pooling</h3>
            <p>Configure connection pooling for better performance:</p>
            <div className="code-example">
              <CopyButton text={`// Create a custom connection pool
ConnectionPool connectionPool = new ConnectionPool(
        20,                      // Maximum idle connections
        10, TimeUnit.MINUTES     // Keep-alive duration
);

// Use the connection pool
HttpClient client = Httply.newHttpClient()
        .connectionPool(connectionPool)
        .build();

RequestQueue queue = Httply.newRequestQueue(context, client);`} />
              <pre>{`// Create a custom connection pool
ConnectionPool connectionPool = new ConnectionPool(
        20,                      // Maximum idle connections
        10, TimeUnit.MINUTES     // Keep-alive duration
);

// Use the connection pool
HttpClient client = Httply.newHttpClient()
        .connectionPool(connectionPool)
        .build();

RequestQueue queue = Httply.newRequestQueue(context, client);`}</pre>
            </div>
          </div>

          <h2>Complete Example</h2>
          <p>Here's a complete example showing how to use Voltra API in an Android Activity:</p>

          <div className="code-example">
            <CopyButton text={`public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ListView listView;
    private List<HashMap<String, String>> userList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize the request queue
        requestQueue = Httply.newRequestQueue(this);
        
        // Initialize UI
        listView = findViewById(R.id.listView);
        userList = new ArrayList<>();
        
        // Load users
        loadUsers();
    }
    
    private void loadUsers() {
        String url = "https://jsonplaceholder.typicode.com/users";
        
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        userList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject user = response.getJSONObject(i);
                            
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", user.getString("name"));
                            userMap.put("email", user.getString("email"));
                            userMap.put("phone", user.getString("phone"));
                            
                            userList.add(userMap);
                        }
                        
                        // Update UI on main thread
                        runOnUiThread(() -> {
                            ArrayAdapter<HashMap<String, String>> adapter = 
                                new SimpleAdapter(this, userList, R.layout.user_item,
                                    new String[]{"name", "email", "phone"},
                                    new int[]{R.id.name, R.id.email, R.id.phone});
                            listView.setAdapter(adapter);
                        });
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showError("Failed to parse user data");
                    }
                },
                error -> {
                    showError("Failed to load users: " + error.getMessage());
                }
        );
        
        // Disable caching for fresh data
        jsonArrayRequest.setShouldCache(false);
        
        // Add request to queue
        requestQueue.add(jsonArrayRequest);
    }
    
    private void showError(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}`} />
            <pre>{`public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ListView listView;
    private List<HashMap<String, String>> userList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize the request queue
        requestQueue = Httply.newRequestQueue(this);
        
        // Initialize UI
        listView = findViewById(R.id.listView);
        userList = new ArrayList<>();
        
        // Load users
        loadUsers();
    }
    
    private void loadUsers() {
        String url = "https://jsonplaceholder.typicode.com/users";
        
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        userList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject user = response.getJSONObject(i);
                            
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", user.getString("name"));
                            userMap.put("email", user.getString("email"));
                            userMap.put("phone", user.getString("phone"));
                            
                            userList.add(userMap);
                        }
                        
                        // Update UI on main thread
                        runOnUiThread(() -> {
                            ArrayAdapter<HashMap<String, String>> adapter = 
                                new SimpleAdapter(this, userList, R.layout.user_item,
                                    new String[]{"name", "email", "phone"},
                                    new int[]{R.id.name, R.id.email, R.id.phone});
                            listView.setAdapter(adapter);
                        });
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showError("Failed to parse user data");
                    }
                },
                error -> {
                    showError("Failed to load users: " + error.getMessage());
                }
        );
        
        // Disable caching for fresh data
        jsonArrayRequest.setShouldCache(false);
        
        // Add request to queue
        requestQueue.add(jsonArrayRequest);
    }
    
    private void showError(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}`}</pre>
          </div>
        </section>
      </div>
    </div>
  );
};

export default VoltraAPI;
