package com.github.httply.voltra;

import com.github.httply.core.HttpMethod;
import com.github.httply.core.HttpResponse;

/**
 * Base class for JSON requests.
 * This class is abstract and should not be used directly.
 * Use {@link JsonObjectRequest} or {@link JsonArrayRequest} instead.
 *
 * @param <T> the type of JSON response
 */
public abstract class JsonRequest<T> extends Request<T> {
    /**
     * Creates a new request with the given method.
     *
     * @param method        the HTTP method
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonRequest(HttpMethod method, String url, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    /**
     * Creates a new request with the given method and body.
     *
     * @param method        the HTTP method
     * @param url           the URL to fetch
     * @param requestBody   the body to send
     * @param contentType   the content type of the body
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonRequest(HttpMethod method, String url, String requestBody, String contentType,
            Listener<T> listener, ErrorListener errorListener) {
        super(method, url, requestBody, contentType, listener, errorListener);
    }

    @Override
    public abstract T parseResponse(HttpResponse response);
}
