import React from 'react';
import CopyButton from '../components/CopyButton';
import './RetraAPI.css';

const RetraAPI = () => {
  return (
    <div className="retra-api-page">
      <div className="container">
        <div className="page-header">
          <h1>Retra API</h1>
          <p>Annotation-based, declarative HTTP API for Android</p>
        </div>

        <section className="content-section">
          <h2>What is Retra API?</h2>
          <p>
            The Retra API provides a clean, annotation-based approach for defining HTTP endpoints. 
            It's similar to Retrofit but built with zero external dependencies. You define your API 
            as a Java interface, and Httply generates the implementation.
          </p>

          <h2>Basic Setup</h2>
          <p>Here's how to get started with Retra API:</p>

          <div className="step">
            <h3>Step 1: Define Your API Interface</h3>
            <p>Create a Java interface with annotated methods:</p>
            <div className="code-example">
              <CopyButton text={`public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
    
    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);
    
    @POST("user/repos")
    Call<Repo> createRepo(@Body Repo repo);
}`} />
              <pre>{`public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
    
    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);
    
    @POST("user/repos")
    Call<Repo> createRepo(@Body Repo repo);
}`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Step 2: Create Model Classes</h3>
            <p>Define your data models for API responses:</p>
            <div className="code-example">
              <CopyButton text={`public class User {
    private String login;
    private String name;
    private String email;
    private int public_repos;
    
    // Getters and setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getPublicRepos() { return public_repos; }
    public void setPublicRepos(int public_repos) { this.public_repos = public_repos; }
}

public class Repo {
    private String name;
    private String description;
    private String language;
    private int stargazers_count;
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public int getStargazersCount() { return stargazers_count; }
    public void setStargazersCount(int stargazers_count) { this.stargazers_count = stargazers_count; }
}`} />
              <pre>{`public class User {
    private String login;
    private String name;
    private String email;
    private int public_repos;
    
    // Getters and setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getPublicRepos() { return public_repos; }
    public void setPublicRepos(int public_repos) { this.public_repos = public_repos; }
}

public class Repo {
    private String name;
    private String description;
    private String language;
    private int stargazers_count;
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public int getStargazersCount() { return stargazers_count; }
    public void setStargazersCount(int stargazers_count) { this.stargazers_count = stargazers_count; }
}`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Step 3: Create Service Factory</h3>
            <p>Initialize Httply with your base URL:</p>
            <div className="code-example">
              <CopyButton text={`// Create a Retra instance using the builder pattern
ServiceFactory factory = Httply.newServiceFactory("https://api.github.com/")
        .build();

// Create an implementation of the API interface
GitHubService service = factory.create(GitHubService.class);`} />
              <pre>{`// Create a Retra instance using the builder pattern
ServiceFactory factory = Httply.newServiceFactory("https://api.github.com/")
        .build();

// Create an implementation of the API interface
GitHubService service = factory.create(GitHubService.class);`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Step 4: Make Requests</h3>
            <p>Use the service to make HTTP requests:</p>
            <div className="code-example">
              <CopyButton text={`// Synchronous request
Call<User> userCall = service.getUser("octocat");
try {
    RetraResponse<User> response = userCall.execute();
    if (response.isSuccessful() && response.body() != null) {
        User user = response.body();
        Log.d("TAG", "User: " + user.getName());
    }
} catch (IOException e) {
    e.printStackTrace();
}

// Asynchronous request with callback
Call<List<Repo>> reposCall = service.listRepos("octocat");
reposCall.enqueue(new Callback<List<Repo>>() {
    @Override
    public void onResponse(Call<List<Repo>> call, RetraResponse<List<Repo>> response) {
        if (response.isSuccessful() && response.body() != null) {
            List<Repo> repos = response.body();
            for (Repo repo : repos) {
                Log.d("TAG", "Repo: " + repo.getName());
            }
        } else {
            Log.e("TAG", "Error: " + response.code());
        }
    }

    @Override
    public void onFailure(Call<List<Repo>> call, Throwable t) {
        Log.e("TAG", "Request failed: " + t.getMessage());
    }
});`} />
              <pre>{`// Synchronous request
Call<User> userCall = service.getUser("octocat");
try {
    RetraResponse<User> response = userCall.execute();
    if (response.isSuccessful() && response.body() != null) {
        User user = response.body();
        Log.d("TAG", "User: " + user.getName());
    }
} catch (IOException e) {
    e.printStackTrace();
}

// Asynchronous request with callback
Call<List<Repo>> reposCall = service.listRepos("octocat");
reposCall.enqueue(new Callback<List<Repo>>() {
    @Override
    public void onResponse(Call<List<Repo>> call, RetraResponse<List<Repo>> response) {
        if (response.isSuccessful() && response.body() != null) {
            List<Repo> repos = response.body();
            for (Repo repo : repos) {
                Log.d("TAG", "Repo: " + repo.getName());
            }
        } else {
            Log.e("TAG", "Error: " + response.code());
        }
    }

    @Override
    public void onFailure(Call<List<Repo>> call, Throwable t) {
        Log.e("TAG", "Request failed: " + t.getMessage());
    }
});`}</pre>
            </div>
          </div>

          <h2>HTTP Method Annotations</h2>
          <p>Retra API supports all standard HTTP methods:</p>

          <div className="annotation-grid">
            <div className="annotation-item">
              <h3>@GET</h3>
              <p>For GET requests</p>
              <div className="code-example">
                <CopyButton text={`@GET("users/{id}")
Call<User> getUser(@Path("id") String userId);`} />
                <pre>{`@GET("users/{id}")
Call<User> getUser(@Path("id") String userId);`}</pre>
              </div>
            </div>

            <div className="annotation-item">
              <h3>@POST</h3>
              <p>For POST requests</p>
              <div className="code-example">
                <CopyButton text={`@POST("users")
Call<User> createUser(@Body User user);`} />
                <pre>{`@POST("users")
Call<User> createUser(@Body User user);`}</pre>
              </div>
            </div>

            <div className="annotation-item">
              <h3>@PUT</h3>
              <p>For PUT requests</p>
              <div className="code-example">
                <CopyButton text={`@PUT("users/{id}")
Call<User> updateUser(@Path("id") String id, @Body User user);`} />
                <pre>{`@PUT("users/{id}")
Call<User> updateUser(@Path("id") String id, @Body User user);`}</pre>
              </div>
            </div>

            <div className="annotation-item">
              <h3>@DELETE</h3>
              <p>For DELETE requests</p>
              <div className="code-example">
                <CopyButton text={`@DELETE("users/{id}")
Call<Void> deleteUser(@Path("id") String id);`} />
                <pre>{`@DELETE("users/{id}")
Call<Void> deleteUser(@Path("id") String id);`}</pre>
              </div>
            </div>
          </div>

          <h2>Parameter Annotations</h2>
          <p>Use annotations to specify how parameters are used in requests:</p>

          <div className="parameter-grid">
            <div className="parameter-item">
              <h3>@Path</h3>
              <p>Replace path segments in the URL</p>
              <div className="code-example">
                <CopyButton text={`@GET("users/{id}/posts/{postId}")
Call<Post> getPost(@Path("id") String userId, @Path("postId") String postId);`} />
                <pre>{`@GET("users/{id}/posts/{postId}")
Call<Post> getPost(@Path("id") String userId, @Path("postId") String postId);`}</pre>
              </div>
            </div>

            <div className="parameter-item">
              <h3>@Query</h3>
              <p>Add query parameters to the URL</p>
              <div className="code-example">
                <CopyButton text={`@GET("users")
Call<List<User>> getUsers(@Query("page") int page, @Query("limit") int limit);`} />
                <pre>{`@GET("users")
Call<List<User>> getUsers(@Query("page") int page, @Query("limit") int limit);`}</pre>
              </div>
            </div>

            <div className="parameter-item">
              <h3>@Body</h3>
              <p>Send data in the request body</p>
              <div className="code-example">
                <CopyButton text={`@POST("users")
Call<User> createUser(@Body User user);`} />
                <pre>{`@POST("users")
Call<User> createUser(@Body User user);`}</pre>
              </div>
            </div>

            <div className="parameter-item">
              <h3>@Header</h3>
              <p>Add custom headers to the request</p>
              <div className="code-example">
                <CopyButton text={`@GET("users")
Call<List<User>> getUsers(@Header("Authorization") String token);`} />
                <pre>{`@GET("users")
Call<List<User>> getUsers(@Header("Authorization") String token);`}</pre>
              </div>
            </div>
          </div>

          <h2>Advanced Configuration</h2>
          <p>Customize your Retra API with advanced options:</p>

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

// Use the custom client with Retra
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
        .client(client)
        .build();`} />
              <pre>{`// Create a custom HTTP client
HttpClient client = Httply.newHttpClient()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(true)
        .build();

// Use the custom client with Retra
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
        .client(client)
        .build();`}</pre>
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

ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
        .client(client)
        .build();`} />
              <pre>{`// Create a custom connection pool
ConnectionPool connectionPool = new ConnectionPool(
        20,                      // Maximum idle connections
        10, TimeUnit.MINUTES     // Keep-alive duration
);

// Use the connection pool
HttpClient client = Httply.newHttpClient()
        .connectionPool(connectionPool)
        .build();

ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
        .client(client)
        .build();`}</pre>
            </div>
          </div>

          <h2>Complete Example</h2>
          <p>Here's a complete example showing how to use Retra API in an Android Activity:</p>

          <div className="code-example">
            <CopyButton text={`public class MainActivity extends AppCompatActivity {
    private GitHubService githubService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize the service
        ServiceFactory factory = Httply.newServiceFactory("https://api.github.com/")
                .build();
        githubService = factory.create(GitHubService.class);
        
        // Make a request
        loadUserRepos("octocat");
    }
    
    private void loadUserRepos(String username) {
        Call<List<Repo>> call = githubService.listRepos(username);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, RetraResponse<List<Repo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Repo> repos = response.body();
                    // Update UI with repos
                    updateReposList(repos);
                } else {
                    showError("Failed to load repos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                showError("Network error: " + t.getMessage());
            }
        });
    }
    
    private void updateReposList(List<Repo> repos) {
        // Update your UI here
        Log.d("TAG", "Loaded " + repos.size() + " repositories");
    }
    
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}`} />
            <pre>{`public class MainActivity extends AppCompatActivity {
    private GitHubService githubService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize the service
        ServiceFactory factory = Httply.newServiceFactory("https://api.github.com/")
                .build();
        githubService = factory.create(GitHubService.class);
        
        // Make a request
        loadUserRepos("octocat");
    }
    
    private void loadUserRepos(String username) {
        Call<List<Repo>> call = githubService.listRepos(username);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, RetraResponse<List<Repo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Repo> repos = response.body();
                    // Update UI with repos
                    updateReposList(repos);
                } else {
                    showError("Failed to load repos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                showError("Network error: " + t.getMessage());
            }
        });
    }
    
    private void updateReposList(List<Repo> repos) {
        // Update your UI here
        Log.d("TAG", "Loaded " + repos.size() + " repositories");
    }
    
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}`}</pre>
          </div>
        </section>
      </div>
    </div>
  );
};

export default RetraAPI;
