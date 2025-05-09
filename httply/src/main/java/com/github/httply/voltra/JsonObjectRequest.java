package com.github.httply.voltra;

import com.github.httply.core.HttpHeader;
import com.github.httply.core.HttpMethod;
import com.github.httply.core.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A request that returns a response as a JSON object.
 */
public class JsonObjectRequest extends Request<JSONObject> {
    /**
     * Creates a new GET request.
     *
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonObjectRequest(String url, Listener<JSONObject> listener, ErrorListener errorListener) {
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
    public JsonObjectRequest(HttpMethod method, String url, Listener<JSONObject> listener,
            ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    /**
     * Creates a new request with the given method and JSON body.
     *
     * @param method        the HTTP method
     * @param url           the URL to fetch
     * @param requestBody   the JSON body to send
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonObjectRequest(HttpMethod method, String url, JSONObject requestBody,
            Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, requestBody != null ? requestBody.toString() : null,
                requestBody != null ? HttpHeader.Values.APPLICATION_JSON : null,
                listener, errorListener);
    }

    /**
     * Creates a new request with the given method constant and JSON body.
     * This constructor matches the Voltra-style API pattern.
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param requestBody   the JSON body to send (can be null for GET requests)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonObjectRequest(int method, String url, JSONObject requestBody,
            Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.toHttpMethod(method), url,
                requestBody != null ? requestBody.toString() : null,
                requestBody != null ? HttpHeader.Values.APPLICATION_JSON : null,
                listener, errorListener);
    }

    /**
     * Creates a new request with the given method constant and JSON body.
     * This constructor matches the example usage pattern.
     *
     * Example usage:
     *
     * <pre>
     * String url = "https://api.example.com/user";
     * JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
     *         Request.Method.GET,
     *         url,
     *         null,
     *         response -> {
     *             // Handle the JSON object response
     *             try {
     *                 String name = response.getString("name");
     *                 int age = response.getInt("age");
     *                 Toast.makeText(context, "Name: " + name + ", Age: " + age, Toast.LENGTH_SHORT).show();
     *             } catch (JSONException e) {
     *                 e.printStackTrace();
     *             }
     *         },
     *         error -> {
     *             // Handle error
     *             error.printStackTrace();
     *         });
     * requestQueue.add(jsonObjectRequest);
     * </pre>
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param jsonRequest   the JSON body to send (can be null for GET requests)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest,
            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.toHttpMethod(method), url,
                jsonRequest != null ? jsonRequest.toString() : null,
                jsonRequest != null ? HttpHeader.Values.APPLICATION_JSON : null,
                response -> listener.onResponse(response),
                error -> errorListener.onErrorResponse(error));
    }

    /**
     * Response listener interface for compatibility with example usage pattern.
     */
    public static class Response {
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

    @Override
    public JSONObject parseResponse(HttpResponse response) {
        if (response.body() == null) {
            System.err.println("ERROR: Response body is null");
            return new JSONObject(); // Return empty object instead of null
        }

        // Get the response body as bytes first to avoid string() method issues
        byte[] responseBytes = response.body().bytes();
        String jsonString = new String(responseBytes);
        System.out.println("DEBUG: Raw JSON response: " + jsonString);

        // Check if the string is empty or whitespace
        if (jsonString == null || jsonString.trim().isEmpty()) {
            System.err.println("ERROR: Empty JSON response");
            return new JSONObject(); // Return empty object
        }

        // Check for common formatting issues
        jsonString = jsonString.trim();

        try {
            // Try to parse as JSON object directly
            JSONObject jsonObject = new JSONObject(jsonString);
            System.out.println("DEBUG: Successfully parsed as JSON object");
            return jsonObject;
        } catch (JSONException e) {
            System.err.println("ERROR: Failed to parse as JSON object: " + e.getMessage());
            System.err.println("ERROR: Problematic JSON string: '" + jsonString + "'");

            // Try to handle common error cases
            try {
                // Check if it's a JSON array - convert it to an object with indices as keys
                JSONArray jsonArray = new JSONArray(jsonString);
                System.out.println("DEBUG: Successfully parsed as JSON array with " + jsonArray.length() + " items");

                JSONObject result = new JSONObject();

                // Convert array to object with indices as keys
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.put(String.valueOf(i), jsonArray.get(i));
                }

                // Add a meta field to indicate this was converted from an array
                result.put("_meta", new JSONObject()
                        .put("convertedFromArray", true)
                        .put("originalLength", jsonArray.length()));

                System.out.println("DEBUG: Converted JSON array to object with " + jsonArray.length() + " items");
                return result;
            } catch (JSONException e2) {
                System.err.println("ERROR: Also failed to parse as JSON array: " + e2.getMessage());

                // Last resort: try to clean the string and parse again
                try {
                    // Sometimes the response might have extra characters or encoding issues
                    String cleaned = jsonString.replaceAll("[^\\x20-\\x7E]", ""); // Remove non-printable chars
                    if (!cleaned.equals(jsonString)) {
                        System.out.println("DEBUG: Cleaned JSON string: '" + cleaned + "'");
                        return new JSONObject(cleaned);
                    }
                } catch (Exception e3) {
                    System.err.println("ERROR: Cleaning attempt failed: " + e3.getMessage());
                }

                // Create a descriptive error with the original exception
                VoltraError error = new VoltraError("Invalid JSON object response: '" + jsonString + "'", e);
                throw new RuntimeException(error);
            }
        }
    }
}
