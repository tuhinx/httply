package com.github.httply.voltra;

import com.github.httply.core.HttpResponse;

/**
 * Represents an error that occurred during a Voltra request.
 */
public class VoltraError extends Exception {
    private final HttpResponse response;

    /**
     * Represents a network response for error reporting.
     */
    public static class NetworkResponse {
        /**
         * The HTTP status code.
         */
        public final int statusCode;

        /**
         * The response data.
         */
        public final byte[] data;

        /**
         * The response headers.
         */
        public final String headers;

        /**
         * Creates a new network response.
         */
        public NetworkResponse(int statusCode, byte[] data, String headers) {
            this.statusCode = statusCode;
            this.data = data;
            this.headers = headers;
        }
    }

    /**
     * The network response, if any.
     */
    public final NetworkResponse networkResponse;

    /**
     * Creates a new error with the given message.
     */
    public VoltraError(String message) {
        super(message);
        this.response = null;
        this.networkResponse = null;
    }

    /**
     * Creates a new error with the given cause.
     */
    public VoltraError(Throwable cause) {
        super(cause);
        this.response = null;
        this.networkResponse = null;
    }

    /**
     * Creates a new error with the given message and cause.
     */
    public VoltraError(String message, Throwable cause) {
        super(message, cause);
        this.response = null;
        this.networkResponse = null;
    }

    /**
     * Creates a new error with the given response.
     */
    public VoltraError(HttpResponse response) {
        super("Error: " + response.code() + " " + response.message());
        this.response = response;

        // Create a network response from the HTTP response
        if (response != null) {
            byte[] data = response.body() != null ? response.body().bytes() : null;
            String headers = response.headers() != null ? response.headers().toString() : null;
            this.networkResponse = new NetworkResponse(response.code(), data, headers);
        } else {
            this.networkResponse = null;
        }
    }

    /**
     * Returns the response that caused this error, or null if none.
     */
    public HttpResponse getResponse() {
        return response;
    }
}
