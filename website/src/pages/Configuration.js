import React from 'react';
import CopyButton from '../components/CopyButton';
import './Configuration.css';

const Configuration = () => {
  return (
    <div className="configuration-page">
      <div className="container">
        <div className="page-header">
          <h1>Configuration</h1>
          <p>Configure Httply for your specific needs</p>
        </div>

        <section className="content-section">
        <h2>Basic Configuration</h2>
        <p>
          Httply can be configured with various options to optimize performance 
          and behavior for your application.
        </p>

        <div className="code-example">
          <CopyButton text={`// Create HTTP client with custom configuration
HttpClient client = Httply.newHttpClient()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .followRedirects(true)
    .build();

// Create service factory with custom client
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .client(client)
    .build();`} />
          <pre>{`// Create HTTP client with custom configuration
HttpClient client = Httply.newHttpClient()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .followRedirects(true)
    .build();

// Create service factory with custom client
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .client(client)
    .build();`}</pre>
        </div>

        <h2>Connection Pooling</h2>
        <p>
          Configure connection pooling for better performance and resource management:
        </p>

        <div className="code-example">
          <CopyButton text={`// Create custom connection pool
ConnectionPool connectionPool = new ConnectionPool(
    10,                     // Maximum idle connections
    5, TimeUnit.MINUTES     // Keep-alive duration
);

// Use with HTTP client
HttpClient client = Httply.newHttpClient()
    .connectionPool(connectionPool)
    .build();`} />
          <pre>{`// Create custom connection pool
ConnectionPool connectionPool = new ConnectionPool(
    10,                     // Maximum idle connections
    5, TimeUnit.MINUTES     // Keep-alive duration
);

// Use with HTTP client
HttpClient client = Httply.newHttpClient()
    .connectionPool(connectionPool)
    .build();`}</pre>
        </div>

        <h2>Thread Pool Configuration</h2>
        <p>
          Customize the thread pool for background operations:
        </p>

        <div className="code-example">
          <CopyButton text={`// Create custom executor
Executor executor = Executors.newFixedThreadPool(4);

// Use with HTTP client
HttpClient client = Httply.newHttpClient()
    .executor(executor)
    .build();

// Or create RequestQueue with custom thread pool size
RequestQueue queue = Httply.newRequestQueue(context, 8);`} />
          <pre>{`// Create custom executor
Executor executor = Executors.newFixedThreadPool(4);

// Use with HTTP client
HttpClient client = Httply.newHttpClient()
    .executor(executor)
    .build();

// Or create RequestQueue with custom thread pool size
RequestQueue queue = Httply.newRequestQueue(context, 8);`}</pre>
        </div>

        <h2>Custom Converters</h2>
        <p>
          Add custom converters for serialization and deserialization:
        </p>

        <div className="code-example">
          <CopyButton text={`// Create service factory with custom converter
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .converter(new JsonConverter())
    .build();

// Or use with custom client
HttpClient client = Httply.newHttpClient().build();
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .client(client)
    .converter(new JsonConverter())
    .build();`} />
          <pre>{`// Create service factory with custom converter
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .converter(new JsonConverter())
    .build();

// Or use with custom client
HttpClient client = Httply.newHttpClient().build();
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .client(client)
    .converter(new JsonConverter())
    .build();`}</pre>
        </div>

        <h2>Request Caching</h2>
        <p>
          Control whether individual requests should be cached:
        </p>

        <div className="code-example">
          <CopyButton text={`// Create a request
StringRequest request = new StringRequest(
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

// Add to queue
queue.add(request);`} />
          <pre>{`// Create a request
StringRequest request = new StringRequest(
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

// Add to queue
queue.add(request);`}</pre>
        </div>

        <h2>Configuration Options</h2>
        <div className="config-table">
          <table>
            <thead>
              <tr>
                <th>Option</th>
                <th>Type</th>
                <th>Default</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>baseUrl</td>
                <td>String</td>
                <td>Required</td>
                <td>Base URL for all requests</td>
              </tr>
              <tr>
                <td>connectTimeout</td>
                <td>long, TimeUnit</td>
                <td>10 seconds</td>
                <td>Connection timeout</td>
              </tr>
              <tr>
                <td>readTimeout</td>
                <td>long, TimeUnit</td>
                <td>10 seconds</td>
                <td>Read timeout</td>
              </tr>
              <tr>
                <td>followRedirects</td>
                <td>boolean</td>
                <td>true</td>
                <td>Whether to follow HTTP redirects</td>
              </tr>
              <tr>
                <td>connectionPool</td>
                <td>ConnectionPool</td>
                <td>Default pool</td>
                <td>Connection pooling configuration</td>
              </tr>
              <tr>
                <td>executor</td>
                <td>Executor</td>
                <td>CachedThreadPool</td>
                <td>Thread pool for async requests</td>
              </tr>
              <tr>
                <td>converter</td>
                <td>Converter</td>
                <td>JsonConverter</td>
                <td>Data conversion for Retra API</td>
              </tr>
            </tbody>
          </table>
        </div>
        </section>
      </div>
    </div>
  );
};

export default Configuration;
