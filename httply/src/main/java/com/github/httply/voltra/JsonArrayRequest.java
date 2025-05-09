package com.github.httply.voltra;

import com.github.httply.core.HttpHeader;
import com.github.httply.core.HttpMethod;
import com.github.httply.core.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A request that returns a response as a JSON array.
 */
public class JsonArrayRequest extends Request<JSONArray> {
    /**
     * Creates a new GET request.
     *
     * @param url           the URL to fetch
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonArrayRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
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
    public JsonArrayRequest(HttpMethod method, String url, Listener<JSONArray> listener, ErrorListener errorListener) {
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
    public JsonArrayRequest(HttpMethod method, String url, JSONArray requestBody,
            Listener<JSONArray> listener, ErrorListener errorListener) {
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
    public JsonArrayRequest(int method, String url, JSONArray requestBody,
            Listener<JSONArray> listener, ErrorListener errorListener) {
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
     * String urlArray = "https://api.example.com/users";
     * JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
     *         Request.Method.GET,
     *         urlArray,
     *         null,
     *         response -> {
     *             // Handle the JSON array response
     *             try {
     *                 for (int i = 0; i < response.length(); i++) {
     *                     JSONObject user = response.getJSONObject(i);
     *                     String name = user.getString("name");
     *                     int age = user.getInt("age");
     *                     Log.d("User", "Name: " + name + ", Age: " + age);
     *                 }
     *             } catch (JSONException e) {
     *                 e.printStackTrace();
     *             }
     *         },
     *         error -> {
     *             // Handle error
     *             error.printStackTrace();
     *         });
     * requestQueue.add(jsonArrayRequest);
     * </pre>
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param jsonRequest   the JSON body to send (can be null for GET requests)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    /**
     * Creates a new request with the given method constant and JSON body.
     * This constructor matches the example usage pattern with anonymous inner
     * classes.
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param jsonRequest   the JSON body to send (can be null for GET requests)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */
    public JsonArrayRequest(int method, String url, JSONArray jsonRequest,
            Response.Listener<JSONArray> listener,
            Response.ErrorListener errorListener) {
        super(Method.toHttpMethod(method), url,
                jsonRequest != null ? jsonRequest.toString() : null,
                jsonRequest != null ? HttpHeader.Values.APPLICATION_JSON : null,
                response -> listener.onResponse(response),
                error -> errorListener.onErrorResponse(error));
    }

    /**
     * Creates a new request with the given method constant and JSON body.
     * This constructor matches the example usage pattern with anonymous inner
     * classes.
     *
     * Example usage:
     *
     * <pre>
     * String url = "https://api.example.com/foods";
     *
     * JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
     *         Request.Method.GET,
     *         url,
     *         null,
     *         new Response.Listener<JSONArray>() {
     *             &#64;Override
     *             public void onResponse(JSONArray response) {
     *                 try {
     *                     for (int i = 0; i < response.length(); i++) {
     *                         JSONObject item = response.getJSONObject(i);
     *                         String name = item.getString("name");
     *                         double price = item.getDouble("price");
     *
     *                         Toast.makeText(context,
     *                                 (i + 1) + ". " + name + " - $" + price,
     *                                 Toast.LENGTH_SHORT).show();
     *                     }
     *                 } catch (JSONException e) {
     *                     e.printStackTrace();
     *                 }
     *             }
     *         },
     *         new Response.ErrorListener() {
     *             @Override
     *             public void onErrorResponse(VoltraError error) {
     *                 error.printStackTrace();
     *                 Toast.makeText(context, "Error loading array", Toast.LENGTH_SHORT).show();
     *             }
     *         });
     * requestQueue.add(jsonArrayRequest);
     * </pre>
     *
     * @param method        the HTTP method constant from {@link Request.Method}
     * @param url           the URL to fetch
     * @param jsonRequest   the JSON body to send (can be null for GET requests)
     * @param listener      the listener to receive the response
     * @param errorListener the listener to receive errors
     */

    @Override
    public JSONArray parseResponse(HttpResponse response) {
        if (response.body() == null) {
            System.err.println("ERROR: Response body is null");
            return new JSONArray(); // Return empty array instead of null
        }

        // Get the response body as bytes first to avoid string() method issues
        byte[] responseBytes = response.body().bytes();
        String jsonString = new String(responseBytes);
        System.out.println("DEBUG: Raw JSON response: " + jsonString);

        // Check if the string is empty or whitespace
        if (jsonString == null || jsonString.trim().isEmpty()) {
            System.err.println("ERROR: Empty JSON response");
            return new JSONArray(); // Return empty array
        }

        // Check for common formatting issues
        jsonString = jsonString.trim();

        try {
            // Try to parse as JSON array directly
            JSONArray jsonArray = new JSONArray(jsonString);
            System.out.println("DEBUG: Successfully parsed as JSON array with " + jsonArray.length() + " items");
            return jsonArray;
        } catch (JSONException e) {
            System.err.println("ERROR: Failed to parse as JSON array: " + e.getMessage());
            System.err.println("ERROR: Problematic JSON string: '" + jsonString + "'");

            // Try to handle common error cases
            try {
                // Check if it's a JSON object that contains an array
                JSONObject jsonObject = new JSONObject(jsonString);
                System.out.println("DEBUG: Successfully parsed as JSON object");

                // Look for common array field names
                for (String key : new String[] { "items", "data", "results", "array" }) {
                    if (jsonObject.has(key) && jsonObject.get(key) instanceof JSONArray) {
                        JSONArray result = jsonObject.getJSONArray(key);
                        System.out.println(
                                "DEBUG: Found array in field '" + key + "' with " + result.length() + " items");
                        return result;
                    }
                }

                System.err.println("ERROR: JSON object does not contain any recognized array field");
                // If we get here, it's a JSON object but not in a format we can convert to an
                // array
                // Instead of throwing an exception, convert the object to a single-item array
                JSONArray result = new JSONArray();
                result.put(jsonObject);
                System.out.println("DEBUG: Converted JSON object to single-item array");
                return result;
            } catch (JSONException e2) {
                System.err.println("ERROR: Also failed to parse as JSON object: " + e2.getMessage());

                // Last resort: try to clean the string and parse again
                try {
                    // Sometimes the response might have extra characters or encoding issues
                    String cleaned = jsonString.replaceAll("[^\\x20-\\x7E]", ""); // Remove non-printable chars
                    if (!cleaned.equals(jsonString)) {
                        System.out.println("DEBUG: Cleaned JSON string: '" + cleaned + "'");
                        return new JSONArray(cleaned);
                    }
                } catch (Exception e3) {
                    System.err.println("ERROR: Cleaning attempt failed: " + e3.getMessage());
                }

                // Create a descriptive error with the original exception
                VoltraError error = new VoltraError("Invalid JSON array response: '" + jsonString + "'", e);
                throw new RuntimeException(error);
            }
        }
    }
}
