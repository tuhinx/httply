import React from 'react';
import CopyButton from '../components/CopyButton';
import './Declarations.css';

const Declarations = () => {
  return (
    <div className="declarations-page">
      <div className="container">
        <div className="page-header">
          <h1>Declarations</h1>
          <p>Learn how to declare API interfaces using Httply's Retra API</p>
        </div>

        <section className="content-section">
        <h2>API Interface Declaration</h2>
        <p>
          Httply uses annotation-based interface declarations similar to Retrofit. 
          Define your API endpoints as interface methods with annotations.
        </p>

        <div className="code-example">
          <CopyButton text={`public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();
    
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String userId);
    
    @POST("users")
    Call<User> createUser(@Body User user);
    
    @PUT("users/{id}")
    Call<User> updateUser(
        @Path("id") String userId,
        @Body User user
    );
    
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String userId);
}`} />
          <pre>{`public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();
    
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String userId);
    
    @POST("users")
    Call<User> createUser(@Body User user);
    
    @PUT("users/{id}")
    Call<User> updateUser(
        @Path("id") String userId,
        @Body User user
    );
    
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String userId);
}`}</pre>
        </div>

        <h2>HTTP Method Annotations</h2>
        <div className="annotation-grid">
          <div className="annotation-item">
            <h3>@GET</h3>
            <p>Make GET requests</p>
            <code>@GET("endpoint")</code>
          </div>
          <div className="annotation-item">
            <h3>@POST</h3>
            <p>Make POST requests</p>
            <code>@POST("endpoint")</code>
          </div>
          <div className="annotation-item">
            <h3>@PUT</h3>
            <p>Make PUT requests</p>
            <code>@PUT("endpoint")</code>
          </div>
          <div className="annotation-item">
            <h3>@DELETE</h3>
            <p>Make DELETE requests</p>
            <code>@DELETE("endpoint")</code>
          </div>
          <div className="annotation-item">
            <h3>@PATCH</h3>
            <p>Make PATCH requests</p>
            <code>@PATCH("endpoint")</code>
          </div>
          <div className="annotation-item">
            <h3>@HEAD</h3>
            <p>Make HEAD requests</p>
            <code>@HEAD("endpoint")</code>
          </div>
        </div>

        <h2>Parameter Annotations</h2>
        <div className="parameter-examples">
          <div className="parameter-item">
            <h3>@Path</h3>
            <p>Replace path placeholders with method parameters</p>
            <div className="code-example">
              <CopyButton text={`@GET("users/{id}")
suspend fun getUser(@Path("id") userId: String): User`} />
              <pre>{`@GET("users/{id}")
suspend fun getUser(@Path("id") userId: String): User`}</pre>
            </div>
          </div>
          
          <div className="parameter-item">
            <h3>@Query</h3>
            <p>Add query parameters to the URL</p>
            <div className="code-example">
              <CopyButton text={`@GET("users")
suspend fun getUsers(@Query("page") page: Int): List<User>`} />
              <pre>{`@GET("users")
suspend fun getUsers(@Query("page") page: Int): List<User>`}</pre>
            </div>
          </div>
          
          <div className="parameter-item">
            <h3>@Body</h3>
            <p>Send data in the request body</p>
            <div className="code-example">
              <CopyButton text={`@POST("users")
suspend fun createUser(@Body user: User): User`} />
              <pre>{`@POST("users")
suspend fun createUser(@Body user: User): User`}</pre>
            </div>
          </div>
          
          <div className="parameter-item">
            <h3>@Header</h3>
            <p>Add custom headers to the request</p>
            <div className="code-example">
              <CopyButton text={`@GET("users")
suspend fun getUsers(@Header("Authorization") token: String): List<User>`} />
              <pre>{`@GET("users")
suspend fun getUsers(@Header("Authorization") token: String): List<User>`}</pre>
            </div>
          </div>
        </div>

        <h2>Using the API</h2>
        <p>Create a service factory and use your API interface:</p>
        
        <div className="code-example">
          <CopyButton text={`// Create service factory
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .build();

// Create API instance
ApiService api = factory.create(ApiService.class);

// Make requests
Call<List<User>> usersCall = api.getUsers();
Call<User> userCall = api.getUser("123");

// Execute asynchronously
usersCall.enqueue(new Callback<List<User>>() {
    @Override
    public void onResponse(Call<List<User>> call, RetraResponse<List<User>> response) {
        if (response.isSuccessful()) {
            List<User> users = response.body();
            // Handle success
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        // Handle error
    }
});`} />
          <pre>{`// Create service factory
ServiceFactory factory = Httply.newServiceFactory("https://api.example.com/")
    .build();

// Create API instance
ApiService api = factory.create(ApiService.class);

// Make requests
Call<List<User>> usersCall = api.getUsers();
Call<User> userCall = api.getUser("123");

// Execute asynchronously
usersCall.enqueue(new Callback<List<User>>() {
    @Override
    public void onResponse(Call<List<User>> call, RetraResponse<List<User>> response) {
        if (response.isSuccessful()) {
            List<User> users = response.body();
            // Handle success
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        // Handle error
    }
});`}</pre>
        </div>
        </section>
      </div>
    </div>
  );
};

export default Declarations;
