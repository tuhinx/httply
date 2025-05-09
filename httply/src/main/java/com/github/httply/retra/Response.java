package com.github.httply.retra;

import com.github.httply.core.HttpResponse;

/**
 * An HTTP response with a parsed body.
 *
 * @param <T> Successful response body type.
 */
public final class Response<T> {
    private final HttpResponse rawResponse;
    private final T body;
    private final Throwable error;

    private Response(HttpResponse rawResponse, T body, Throwable error) {
        this.rawResponse = rawResponse;
        this.body = body;
        this.error = error;
    }

    /**
     * Create a successful response.
     */
    public static <T> Response<T> success(T body, HttpResponse rawResponse) {
        return new Response<>(rawResponse, body, null);
    }

    /**
     * Create a failed response.
     */
    public static <T> Response<T> error(Throwable error, HttpResponse rawResponse) {
        return new Response<>(rawResponse, null, error);
    }

    /**
     * The raw response from the HTTP client.
     */
    public HttpResponse raw() {
        return rawResponse;
    }

    /**
     * The deserialized response body of a successful request.
     */
    public T body() {
        return body;
    }

    /**
     * The error encountered during the request, if any.
     */
    public Throwable error() {
        return error;
    }

    /**
     * Returns true if the request was successful.
     */
    public boolean isSuccessful() {
        return rawResponse != null && rawResponse.isSuccessful() && error == null;
    }
}
