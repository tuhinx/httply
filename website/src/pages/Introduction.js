import React from 'react';
import CopyButton from '../components/CopyButton';
import './Introduction.css';

const Introduction = () => {
  return (
    <div className="introduction-page">
      <div className="container">
        <section className="content-section">
          <h1>Introduction</h1>
          <p>Httply turns your HTTP API into a Java interface.</p>
          
          <div className="code-example">
            <CopyButton text={`public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}`} />
            <pre>{`public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}`}</pre>
          </div>

          <p>The <code>Httply</code> class generates an implementation of the <code>GitHubService</code> interface.</p>

          <div className="code-example">
            <CopyButton text={`ServiceFactory factory = Httply.newServiceFactory("https://api.github.com/")
    .build();

GitHubService service = factory.create(GitHubService.class);`} />
            <pre>{`ServiceFactory factory = Httply.newServiceFactory("https://api.github.com/")
    .build();

GitHubService service = factory.create(GitHubService.class);`}</pre>
          </div>

          <p>Each <code>Call</code> from the created <code>GitHubService</code> can make synchronous or asynchronous HTTP requests to the remote webserver.</p>

          <div className="code-example">
            <CopyButton text={`Call<List<Repo>> repos = service.listRepos("octocat");`} />
            <pre>{`Call<List<Repo>> repos = service.listRepos("octocat");`}</pre>
          </div>

          <p>Use annotations to describe the HTTP request on each interface method:</p>
          <ul>
            <li>URL parameter replacement and query parameter support</li>
            <li>Object conversion to request body (e.g., JSON, protocol buffers)</li>
            <li>Multipart request body and file upload</li>
          </ul>

          <h2>What is Httply?</h2>
        <p>
          Httply is a modern, lightweight HTTP networking library for Android that supports both <strong>Retra-style</strong> (annotation-based) and <strong>Voltra-style</strong> (request queue-based) APIs. Built with zero external dependencies, it's designed to be simple, efficient, and easy to use.
        </p>
        
        <div className="api-vertical">
          <div className="api-section">
            <h3>Retra API</h3>
            <p>Annotation-based interface similar to Retrofit, perfect for RESTful APIs</p>
            <div className="code-example">
              <CopyButton text={`public interface ApiService {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String userId);
}

// Create service factory
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .build();

// Use the service
ApiService api = factory.create(ApiService.class);
Call<User> call = api.getUser("123");`} />
              <pre>{`public interface ApiService {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String userId);
}

// Create service factory
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .build();

// Use the service
ApiService api = factory.create(ApiService.class);
Call<User> call = api.getUser("123");`}</pre>
            </div>
          </div>
          
          <div className="api-section">
            <h3>Voltra API</h3>
            <p>Request queue-based API for making multiple HTTP requests efficiently</p>
            <div className="code-example">
              <CopyButton text={`// Create request queue
RequestQueue queue = Httply.newRequestQueue(context);

// Make requests
JsonObjectRequest request = new JsonObjectRequest(
    Request.Method.GET,
    "https://api.example.com/users",
    null,
    response -> {
        // Handle response
    },
    error -> {
        // Handle error
    }
);

queue.add(request);`} />
              <pre>{`// Create request queue
RequestQueue queue = Httply.newRequestQueue(context);

// Make requests
JsonObjectRequest request = new JsonObjectRequest(
    Request.Method.GET,
    "https://api.example.com/users",
    null,
    response -> {
        // Handle response
    },
    error -> {
        // Handle error
    }
);

queue.add(request);`}</pre>
            </div>
          </div>
        </div>

        <h2>Key Features</h2>
        <ul className="features-list">
          <li><strong>🔌 Zero External Dependencies:</strong> Built entirely with Java standard libraries</li>
          <li><strong>🧩 Dual API Styles:</strong> Choose between Retra (annotation-based) or Voltra (queue-based) approaches</li>
          <li><strong>☕ Full Java and Kotlin Support:</strong> Works seamlessly with both languages</li>
          <li><strong>🪶 Lightweight:</strong> Minimal footprint in your app</li>
          <li><strong>🔄 Connection Pooling:</strong> Reuses connections for better performance</li>
          <li><strong>🧠 Configurable Caching:</strong> Control whether requests should be cached</li>
          <li><strong>⏱️ Configurable Timeouts:</strong> Set connect and read timeouts</li>
          <li><strong>🧵 Customizable Thread Pools:</strong> Control the number of threads used for requests</li>
          <li><strong>📋 JSON Support:</strong> Built-in JSON parsing with standard org.json library</li>
        </ul>

        <h2>Zero External Dependencies</h2>
        <p>
          Httply is designed with a strong focus on minimizing dependencies in your Android projects:
        </p>
        <ul className="features-list">
          <li><strong>🔒 Security & Reliability:</strong> No third-party libraries reduces risk of dependency conflicts and security vulnerabilities</li>
          <li><strong>⚡ Performance Benefits:</strong> Smaller APK size, faster build times, and reduced method count</li>
          <li><strong>🧰 Implementation:</strong> Built on Java standard libraries with clean architecture</li>
        </ul>

        <h2>Quick Start</h2>
        <p>Add Httply to your project and start making HTTP requests in minutes:</p>
        
        <div className="code-example">
          <CopyButton text={`// 1. Add dependency
implementation 'com.github.tuhinx:httply:1.0.5'

// 2. Create your API interface (Retra API)
public interface ApiService {
    @GET("posts")
    Call<List<Post>> getPosts();
}

// 3. Use it
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .build();
ApiService api = factory.create(ApiService.class);
Call<List<Post>> call = api.getPosts();`} />
          <pre>{`// 1. Add dependency
implementation 'com.github.tuhinx:httply:1.0.5'

// 2. Create your API interface (Retra API)
public interface ApiService {
    @GET("posts")
    Call<List<Post>> getPosts();
}

// 3. Use it
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .build();
ApiService api = factory.create(ApiService.class);
Call<List<Post>> call = api.getPosts();`}</pre>
        </div>
        </section>
      </div>
    </div>
  );
};

export default Introduction;
