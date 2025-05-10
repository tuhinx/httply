package com.github.httply.voltra;

/**
 * Response listener interface for compatibility with example usage pattern.
 */
public class VoltraResponse {
    /**
     * Listener interface for receiving the response.
     */
    public interface Listener<T> {
        /**
         * Called when a response is received.
         */
        void onResponse(T response);
    }

    /**
     * Listener interface for receiving errors.
     */
    public interface ErrorListener {
        /**
         * Called when an error is received.
         */
        void onErrorResponse(VoltraError error);
    }
}
