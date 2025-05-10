package com.github.httply.voltra;

import com.github.httply.core.HttpHeader;
import com.github.httply.core.HttpMethod;
import com.github.httply.core.HttpRequest;
import com.github.httply.core.HttpResponse;
import com.github.httply.core.HttpUrl;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for all Voltra-style requests.
 *
 * @param <T> the type of parsed response
 */
public abstract class Request<T> {
    /**
     * Default timeout for requests in milliseconds.
     */
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    /**
     * HTTP Method constants for use with Voltra-style API.
     */
    public static final class Method {
        public static final int GET = 0;
        public static final int POST = 1;
        public static final int PUT = 2;
        public static final int DELETE = 3;
        public static final int HEAD = 4;
        public static final int OPTIONS = 5;
        public static final int PATCH = 6;
        public static final int TRACE = 7;

        /**
         * Converts an integer method value to an HttpMethod enum.
         *
         * @param method the integer method value
         * @return the corresponding HttpMethod enum
         * @throws IllegalArgumentException if the method is invalid
         */
        public static HttpMethod toHttpMethod(int method) {
            switch (method) {
                case GET:
                    return HttpMethod.GET;
                case POST:
                    return HttpMethod.POST;
                case PUT:
                    return HttpMethod.PUT;
                case DELETE:
                    return HttpMethod.DELETE;
                case HEAD:
                    return HttpMethod.HEAD;
                case OPTIONS:
                    return HttpMethod.OPTIONS;
                case PATCH:
                    return HttpMethod.PATCH;
                case TRACE:
                    return HttpMethod.TRACE;
                default:
                    throw new IllegalArgumentException("Invalid method: " + method);
            }
        }
    }

    /**
     * Callback interface for delivering parsed responses.
     *
     * @deprecated Use {@link VoltraResponse.Listener} instead.
     */
    @Deprecated
    public interface Listener<T> extends VoltraResponse.Listener<T> {
    }

    /**
     * Callback interface for delivering error responses.
     *
     * @deprecated Use {@link VoltraResponse.ErrorListener} instead.
     */
    @Deprecated
    public interface ErrorListener extends VoltraResponse.ErrorListener {
    }

    private final HttpMethod method;
    private final String url;
    private final Map<String, String> headers = new HashMap<>();
    private final Listener<T> listener;
    private final ErrorListener errorListener;
    private final String requestBody;
    private final String contentType;
    private int timeoutMs = DEFAULT_TIMEOUT_MS;
    private int retryCount = 0;
    private int maxRetries = 1;
    private Object tag;
    private boolean shouldCache = true;

    /**
     * Creates a new request with the given method.
     *
     * @param method        the HTTP method
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public Request(HttpMethod method, String url, Listener<T> listener, ErrorListener errorListener) {
        this(method, url, null, null, listener, errorListener);
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
    public Request(HttpMethod method, String url, String requestBody, String contentType,
            Listener<T> listener, ErrorListener errorListener) {
        this.method = method;
        this.url = url;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    /**
     * Returns the HTTP method of this request.
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Returns the URL of this request.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the headers of this request.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Adds a header to this request.
     */
    public Request<T> addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * Returns the body of this request.
     */
    public String getRequestBody() {
        return requestBody;
    }

    /**
     * Returns the content type of this request.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns the timeout of this request in milliseconds.
     */
    public int getTimeoutMs() {
        return timeoutMs;
    }

    /**
     * Sets the timeout of this request in milliseconds.
     */
    public Request<T> setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    /**
     * Returns the retry count of this request.
     */
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * Returns the maximum number of retries for this request.
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets the maximum number of retries for this request.
     */
    public Request<T> setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    /**
     * Returns the tag of this request.
     */
    public Object getTag() {
        return tag;
    }

    /**
     * Sets the tag of this request.
     */
    public Request<T> setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    /**
     * Sets whether this request should be cached.
     *
     * @param shouldCache true if the request should be cached, false otherwise
     * @return this request
     */
    public Request<T> setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
        return this;
    }

    /**
     * Returns whether this request should be cached.
     *
     * @return true if the request should be cached, false otherwise
     */
    public boolean shouldCache() {
        return shouldCache;
    }

    /**
     * Increments the retry count of this request.
     */
    public void incrementRetryCount() {
        retryCount++;
    }

    /**
     * Returns whether this request has exceeded its maximum number of retries.
     */
    public boolean hasExceededMaxRetries() {
        return retryCount >= maxRetries;
    }

    /**
     * Delivers the response to the listener.
     */
    public void deliverResponse(T response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    /**
     * Delivers an error to the error listener.
     */
    public void deliverError(VoltraError error) {
        if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }

    /**
     * Converts this request to an HttpRequest.
     */
    public HttpRequest toHttpRequest() {
        // Ensure URL is properly formatted
        String trimmedUrl = url != null ? url.trim() : null;

        HttpRequest.Builder builder = new HttpRequest.Builder()
                .url(trimmedUrl)
                .timeout(timeoutMs)
                .shouldCache(shouldCache);

        // Set headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        // Set method and body
        if (requestBody != null && contentType != null) {
            HttpRequest.RequestBody body = HttpRequest.RequestBody.create(requestBody, contentType);
            switch (method) {
                case POST:
                    builder.post(body);
                    break;
                case PUT:
                    builder.put(body);
                    break;
                case PATCH:
                    builder.patch(body);
                    break;
                case DELETE:
                    builder.delete(body);
                    break;
                default:
                    throw new IllegalStateException("Unsupported method: " + method);
            }
        } else {
            switch (method) {
                case GET:
                    builder.get();
                    break;
                case DELETE:
                    builder.delete();
                    break;
                case HEAD:
                    builder.head();
                    break;
                default:
                    throw new IllegalStateException("Unsupported method without body: " + method);
            }
        }

        return builder.build();
    }

    /**
     * Parses the given response.
     *
     * @param response the response to parse
     * @return the parsed response
     */
    public abstract T parseResponse(HttpResponse response);
}
