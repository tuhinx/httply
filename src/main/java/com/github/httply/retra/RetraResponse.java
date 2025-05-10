package com.github.httply.retra;

import com.github.httply.core.HttpResponse;

/**
 * Represents an HTTP response with a parsed body.
 * <p>
 * This class encapsulates either a successful response with a body or a failure
 * with an error.
 *
 * @param <T> the type of the successful response body
 */
public final class RetraResponse<T> {
    private final HttpResponse rawResponse;
    private final T body;
    private final Throwable error;

    private RetraResponse(HttpResponse rawResponse, T body, Throwable error) {
        this.rawResponse = rawResponse;
        this.body = body;
        this.error = error;
    }

    /**
     * Creates a successful response.
     *
     * @param body        the response body
     * @param rawResponse the raw HTTP response
     * @param <T>         the type of the response body
     * @return a successful response instance
     */
    public static <T> RetraResponse<T> success(T body, HttpResponse rawResponse) {
        if (rawResponse == null) {
            throw new IllegalArgumentException("rawResponse cannot be null for a successful response.");
        }
        return new RetraResponse<>(rawResponse, body, null);
    }

    /**
     * Creates a failed response.
     *
     * @param error       the error encountered
     * @param rawResponse the raw HTTP response
     * @param <T>         the type of the response body
     * @return a failed response instance
     */
    public static <T> RetraResponse<T> error(Throwable error, HttpResponse rawResponse) {
        if (error == null) {
            throw new IllegalArgumentException("error cannot be null for a failed response.");
        }
        return new RetraResponse<>(rawResponse, null, error);
    }

    /**
     * @return the raw HTTP response
     */
    public HttpResponse raw() {
        return rawResponse;
    }

    /**
     * @return the response body if the request was successful, or {@code null}
     *         otherwise
     */
    public T body() {
        return body;
    }

    /**
     * @return the error if the request failed, or {@code null} otherwise
     */
    public Throwable error() {
        return error;
    }

    /**
     * @return {@code true} if the response is successful (status code 2xx and no
     *         error)
     */
    public boolean isSuccessful() {
        return rawResponse != null && rawResponse.isSuccessful() && error == null;
    }
}
