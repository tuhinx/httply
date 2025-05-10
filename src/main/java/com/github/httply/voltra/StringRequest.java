package com.github.httply.voltra;

import com.github.httply.core.HttpMethod;
import com.github.httply.core.HttpResponse;

/**
 * A request that returns a response as a string.
 */
public class StringRequest extends Request<String> {
    /**
     * Creates a new GET request.
     *
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public StringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        super(HttpMethod.GET, url, listener, errorListener);
    }

    /**
     * Creates a new request with the given method.
     *
     * @param method        the HTTP method
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public StringRequest(HttpMethod method, String url, Listener<String> listener, ErrorListener errorListener) {
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
    public StringRequest(HttpMethod method, String url, String requestBody, String contentType,
            Listener<String> listener, ErrorListener errorListener) {
        super(method, url, requestBody, contentType, listener, errorListener);
    }

    /**
     * Creates a new request with the given method constant.
     * This constructor matches the Voltra-style API pattern.
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param requestBody   the body to send (can be null for GET requests)
     * @param contentType   the content type of the body (can be null if requestBody
     *                      is null)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public StringRequest(int method, String url, String requestBody, String contentType,
            Listener<String> listener, ErrorListener errorListener) {
        super(Method.toHttpMethod(method), url, requestBody, contentType, listener, errorListener);
    }

    /**
     * Creates a new request with the given method constant.
     * This constructor matches the example usage pattern.
     *
     * Example usage:
     *
     * <pre>
     * String url = "https://api.example.com/data";
     * StringRequest stringRequest = new StringRequest(
     *         Request.Method.GET,
     *         url,
     *         null, // No request body for GET
     *         null, // No content type needed for GET
     *         response -> {
     *             // Handle the string response
     *             Log.d("StringRequest", "Response: " + response);
     *         },
     *         error -> {
     *             // Handle error
     *             error.printStackTrace();
     *         });
     * requestQueue.add(stringRequest);
     * </pre>
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param requestBody   the body to send (can be null for GET requests)
     * @param contentType   the content type of the body (can be null if requestBody
     *                      is null)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public StringRequest(int method, String url, String requestBody, String contentType,
            VoltraResponse.Listener<String> listener,
            VoltraResponse.ErrorListener errorListener) {
        super(Method.toHttpMethod(method), url, requestBody, contentType,
                response -> listener.onResponse(response),
                error -> errorListener.onErrorResponse(error));
    }

    /**
     * Creates a new request with the given method constant.
     * This constructor matches the example usage pattern with anonymous inner
     * classes.
     *
     * Example usage:
     *
     * <pre>
     * String url = "https://api.example.com/message";
     *
     * StringRequest stringRequest = new StringRequest(
     *         Request.Method.GET,
     *         url,
     *         new Response.Listener<String>() {
     *             &#64;Override
     *             public void onResponse(String response) {
     *                 Toast.makeText(context, "Response: " + response, Toast.LENGTH_SHORT).show();
     *             }
     *         },
     *         new Response.ErrorListener() {
     *             @Override
     *             public void onErrorResponse(VoltraError error) {
     *                 error.printStackTrace();
     *                 Toast.makeText(context, "Error fetching string", Toast.LENGTH_SHORT).show();
     *             }
     *         });
     * requestQueue.add(stringRequest);
     * </pre>
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public StringRequest(int method, String url,
            VoltraResponse.Listener<String> listener,
            VoltraResponse.ErrorListener errorListener) {
        this(method, url, null, null, listener, errorListener);
    }

    @Override
    public String parseResponse(HttpResponse response) {
        return response.body() != null ? response.body().string() : "";
    }
}
