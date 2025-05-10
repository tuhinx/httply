package com.github.httply.retra.annotations;

import com.github.httply.retra.RetraResponse;

/**
 * Communicates responses from a server or offline requests. One and only one
 * method will be
 * invoked in response to a given request.
 *
 * @param <T> Successful response body type.
 */
public interface Callback<T> {
    /**
     * Invoked for a received HTTP response.
     *
     * @param call     The call that got the response.
     * @param response The response from the server.
     */
    void onResponse(Call<T> call, RetraResponse<T> response);

    /**
     * Invoked when a network exception occurred talking to the server or when an
     * unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call The call that failed.
     * @param t    The exception that occurred.
     */
    void onFailure(Call<T> call, Throwable t);
}
