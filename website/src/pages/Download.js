import React from 'react';
import CopyButton from '../components/CopyButton';
import './Download.css';

const Download = () => {
  return (
    <div className="download-page">
      <div className="container">
        <div className="page-header">
          <h1>Download</h1>
          <p>Get Httply for your Android project</p>
        </div>

        <section className="content-section">
        <h2>Gradle Setup</h2>
        <p>
          Add Httply to your project using Gradle. Make sure you have the 
          JitPack repository in your project's build.gradle file.
        </p>

        <div className="setup-steps">
          <div className="step">
            <h3>Step 1: Add JitPack Repository</h3>
            <p>Add JitPack to your project-level build.gradle:</p>
            <div className="code-example">
              <CopyButton text={`dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}`} />
              <pre>{`dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}`}</pre>
            </div>
          </div>

          <div className="step">
            <h3>Step 2: Add Httply Dependency</h3>
            <p>Add Httply to your app-level build.gradle:</p>
          <div className="code-example">
            <CopyButton text={`dependencies {
    implementation 'com.github.tuhinx:httply:1.0.5'
}`} />
            <pre>{`dependencies {
    implementation 'com.github.tuhinx:httply:1.0.5'
}`}</pre>
          </div>
          </div>

          <div className="step">
            <h3>Step 3: Add Internet Permission</h3>
            <p>Add internet permission to your AndroidManifest.xml:</p>
            <div className="code-example">
              <pre><code>{`<uses-permission android:name="android.permission.INTERNET" />`}</code></pre>
            </div>
          </div>
        </div>

        <h2>Maven Setup</h2>
        <p>If you're using Maven, add Httply to your pom.xml:</p>

        <div className="code-example">
          <CopyButton text={`<dependency>
    <groupId>com.github.tuhinx</groupId>
    <artifactId>httply</artifactId>
    <version>1.0.5</version>
</dependency>`} />
          <pre>{`<dependency>
    <groupId>com.github.tuhinx</groupId>
    <artifactId>httply</artifactId>
    <version>1.0.5</version>
</dependency>`}</pre>
        </div>

        <h2>Version Information</h2>
        <div className="version-info">
          <div className="version-card">
            <h3>Current Version</h3>
            <div className="version-number">1.0.5</div>
            <p>Latest stable release</p>
          </div>
          <div className="version-card">
            <h3>Minimum SDK</h3>
            <div className="version-number">24</div>
            <p>Android 7.0 (API level 24)</p>
          </div>
          <div className="version-card">
            <h3>Target SDK</h3>
            <div className="version-number">35</div>
            <p>Android 15 (API level 35)</p>
          </div>
        </div>

        <h2>ProGuard Rules</h2>
        <p>
          If you're using ProGuard, add these rules to keep Httply working correctly:
        </p>

        <div className="code-example">
          <CopyButton text={`# Httply
-keep class com.github.httply.** { *; }
-keep interface com.github.httply.** { *; }

# Keep your API interfaces
-keep interface * extends com.github.httply.retra.ServiceFactory { *; }

# Keep data classes
-keep class * implements java.io.Serializable { *; }
-keep class * implements java.lang.Cloneable { *; }

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature`} />
          <pre>{`# Httply
-keep class com.github.httply.** { *; }
-keep interface com.github.httply.** { *; }

# Keep your API interfaces
-keep interface * extends com.github.httply.retra.ServiceFactory { *; }

# Keep data classes
-keep class * implements java.io.Serializable { *; }
-keep class * implements java.lang.Cloneable { *; }

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature`}</pre>
        </div>

        <h2>Quick Start Example</h2>
        <p>Here's a complete example to get you started:</p>

        <div className="code-example">
          <CopyButton text={`// 1. Create your API interface
public interface ApiService {
    @GET("posts")
    Call<List<Post>> getPosts();
}

// 2. Initialize Httply
ServiceFactory factory = Httply.newServiceFactory("https://jsonplaceholder.typicode.com/")
    .build();

ApiService api = factory.create(ApiService.class);

// 3. Use in your Activity/Fragment
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Make request
        Call<List<Post>> call = api.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, RetraResponse<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    // Handle posts
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Handle error
            }
        });
    }
}`} />
          <pre>{`// 1. Create your API interface
public interface ApiService {
    @GET("posts")
    Call<List<Post>> getPosts();
}

// 2. Initialize Httply
ServiceFactory factory = Httply.newServiceFactory("https://jsonplaceholder.typicode.com/")
    .build();

ApiService api = factory.create(ApiService.class);

// 3. Use in your Activity/Fragment
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Make request
        Call<List<Post>> call = api.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, RetraResponse<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    // Handle posts
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Handle error
            }
        });
    }
}`}</pre>
        </div>

        <h2>Need Help?</h2>
        <div className="help-links">
          <a href="https://github.com/tuhinx/httply" target="_blank" rel="noopener noreferrer" className="help-link">
            <span className="help-icon">📚</span>
            <div>
              <h3>GitHub Repository</h3>
              <p>View source code and examples</p>
            </div>
          </a>
          <a href="https://github.com/tuhinx/httply/issues" target="_blank" rel="noopener noreferrer" className="help-link">
            <span className="help-icon">🐛</span>
            <div>
              <h3>Report Issues</h3>
              <p>Found a bug? Let us know</p>
            </div>
          </a>
          <a href="https://github.com/tuhinx/httply/discussions" target="_blank" rel="noopener noreferrer" className="help-link">
            <span className="help-icon">💬</span>
            <div>
              <h3>Community</h3>
              <p>Ask questions and get help</p>
            </div>
          </a>
        </div>
        </section>
      </div>
    </div>
  );
};

export default Download;
