package com.github.httply.retra.annotations;

import java.io.IOException;

/**
 * An invocation of a Retra method that sends a request to a webserver and
 * returns a response.
 * Each call yields its own HTTP request and response pair. Use
 * {@link #execute()} to
 * synchronously send the request and block until the response is received, or
 * {@link #enqueue(Callback)}
 * to asynchronously send the request.
 *
 * @param <T> Successful response body type.
 */
public interface Call<T> {
    /**
     * Synchronously send the request and return its response.
     *
     * @return the response body
     * @throws IOException if a problem occurred talking to the server
     */
    T execute() throws IOException;

    /**
     * Asynchronously send the request and notify {@code callback} of its response
     * or if an error
     * occurred talking to the server, creating the request, or processing the
     * response.
     */
    void enqueue(Callback<T> callback);

    /**
     * Returns a new, identical call to this one which can be enqueued or executed
     * even if this call
     * has already been executed.
     */
    Call<T> clone();

    /**
     * Cancel this call. If the call has not yet been executed it never will be.
     */
    void cancel();

    /**
     * True if {@link #cancel()} was called.
     */
    boolean isCanceled();
}
