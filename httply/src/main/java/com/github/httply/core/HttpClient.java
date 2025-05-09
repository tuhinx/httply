package com.github.httply.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Client for making HTTP requests.
 */
public class HttpClient {
    private static final int BUFFER_SIZE = 4096;

    private final ConnectionPool connectionPool;
    private final Executor executor;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;
    private final boolean followRedirects;

    private HttpClient(Builder builder) {
        this.connectionPool = builder.connectionPool;
        this.executor = builder.executor;
        this.connectTimeoutMs = builder.connectTimeoutMs;
        this.readTimeoutMs = builder.readTimeoutMs;
        this.followRedirects = builder.followRedirects;
    }

    /**
     * Executes the given request synchronously and returns the response.
     *
     * @param request the request to execute
     * @return the response
     * @throws IOException if an I/O error occurs
     */
    public HttpResponse execute(HttpRequest request) throws IOException {
        HttpURLConnection connection = openConnection(request);
        try {
            // Write request body if present
            if (request.body() != null) {
                writeRequestBody(connection, request.body());
            }

            // Read response
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            // Build response headers
            HttpHeader.Builder headersBuilder = new HttpHeader.Builder();
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                String name = entry.getKey();
                if (name != null) { // Skip the status line
                    for (String value : entry.getValue()) {
                        headersBuilder.add(name, value);
                    }
                }
            }

            // Read response body
            HttpResponse.ResponseBody responseBody = null;
            InputStream inputStream = responseCode >= 400
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            if (inputStream != null) {
                byte[] bytes = readBytes(inputStream);
                String contentType = connection.getContentType();
                responseBody = HttpResponse.ResponseBody.create(bytes, contentType);
            }

            // Build response
            HttpResponse.Builder responseBuilder = new HttpResponse.Builder()
                    .code(responseCode)
                    .message(responseMessage)
                    .body(responseBody)
                    .request(request);

            // Add headers
            HttpHeader headers = headersBuilder.build();
            for (String name : headers.names()) {
                for (String value : headers.getAll(name)) {
                    responseBuilder.addHeader(name, value);
                }
            }

            HttpResponse response = responseBuilder.build();

            return response;
        } finally {
            // Add connection back to the pool or close it
            boolean canReuse = canReuseConnection(connection);
            if (canReuse) {
                connectionPool.put(connection, request.url());
            } else {
                connection.disconnect();
            }
        }
    }

    /**
     * Executes the given request asynchronously and notifies the callback of the
     * response.
     *
     * @param request  the request to execute
     * @param callback the callback to notify
     */
    public void executeAsync(final HttpRequest request, final Callback callback) {
        executor.execute(() -> {
            try {
                HttpResponse response = execute(request);
                callback.onResponse(response);
            } catch (Exception e) {
                callback.onFailure(e);
            }
        });
    }

    /**
     * Opens a connection for the given request.
     */
    private HttpURLConnection openConnection(HttpRequest request) throws IOException {
        // Try to get a connection from the pool
        HttpURLConnection connection = connectionPool.get(request.url());

        // If no connection is available, create a new one
        if (connection == null) {
            URL url = new URL(request.url().toString());
            connection = (HttpURLConnection) url.openConnection();
        }

        // Configure the connection
        connection.setRequestMethod(request.method().name());
        connection.setConnectTimeout(connectTimeoutMs);
        connection.setReadTimeout(readTimeoutMs);
        connection.setInstanceFollowRedirects(followRedirects);

        // Set request headers
        for (String name : request.headers().names()) {
            for (String value : request.headers().getAll(name)) {
                connection.addRequestProperty(name, value);
            }
        }

        // Configure for request body if present
        if (request.body() != null) {
            connection.setDoOutput(true);
            if (request.body().contentType() != null) {
                connection.setRequestProperty("Content-Type", request.body().contentType());
            }
            if (request.body().contentLength() != -1) {
                connection.setRequestProperty("Content-Length",
                        Long.toString(request.body().contentLength()));
            }
        }

        return connection;
    }

    /**
     * Writes the request body to the connection.
     */
    private void writeRequestBody(HttpURLConnection connection, HttpRequest.RequestBody body)
            throws IOException {
        try (OutputStream out = connection.getOutputStream();
                InputStream in = body.content()) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * Reads all bytes from the given input stream.
     */
    private byte[] readBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        return out.toByteArray();
    }

    /**
     * Returns whether the connection can be reused.
     */
    private boolean canReuseConnection(HttpURLConnection connection) {
        String connection_header = connection.getHeaderField("Connection");
        return !"close".equalsIgnoreCase(connection_header);
    }

    /**
     * Closes all connections in the connection pool.
     */
    public void close() {
        connectionPool.closeAll();
    }

    /**
     * Builder for {@link HttpClient}.
     */
    public static final class Builder {
        private ConnectionPool connectionPool;
        private Executor executor;
        private int connectTimeoutMs = 10000; // 10 seconds
        private int readTimeoutMs = 10000; // 10 seconds
        private boolean followRedirects = true;

        public Builder() {
            this.connectionPool = new ConnectionPool();
            this.executor = Executors.newCachedThreadPool();
        }

        /**
         * Sets the connection pool for this client.
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) {
                throw new NullPointerException("connectionPool == null");
            }
            this.connectionPool = connectionPool;
            return this;
        }

        /**
         * Sets the executor for this client.
         */
        public Builder executor(Executor executor) {
            if (executor == null) {
                throw new NullPointerException("executor == null");
            }
            this.executor = executor;
            return this;
        }

        /**
         * Sets the connect timeout for this client.
         */
        public Builder connectTimeout(long timeout, TimeUnit unit) {
            if (timeout < 0) {
                throw new IllegalArgumentException("timeout < 0");
            }
            if (unit == null) {
                throw new NullPointerException("unit == null");
            }
            this.connectTimeoutMs = (int) unit.toMillis(timeout);
            return this;
        }

        /**
         * Sets the read timeout for this client.
         */
        public Builder readTimeout(long timeout, TimeUnit unit) {
            if (timeout < 0) {
                throw new IllegalArgumentException("timeout < 0");
            }
            if (unit == null) {
                throw new NullPointerException("unit == null");
            }
            this.readTimeoutMs = (int) unit.toMillis(timeout);
            return this;
        }

        /**
         * Sets whether this client should follow redirects.
         */
        public Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        /**
         * Builds a new {@link HttpClient}.
         */
        public HttpClient build() {
            return new HttpClient(this);
        }
    }

    /**
     * Callback for asynchronous HTTP requests.
     */
    public interface Callback {
        /**
         * Called when the request is successful.
         */
        void onResponse(HttpResponse response);

        /**
         * Called when the request fails.
         */
        void onFailure(Exception e);
    }
}
