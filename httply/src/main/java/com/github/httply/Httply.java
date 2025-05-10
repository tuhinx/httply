package com.github.httply;

import android.content.Context;

import com.github.httply.core.HttpClient;
import com.github.httply.retra.annotations.Converter;
import com.github.httply.retra.JsonConverter;
import com.github.httply.retra.ServiceFactory;
import com.github.httply.voltra.RequestQueue;

import java.util.concurrent.TimeUnit;

/**
 * Main entry point for the Httply library.
 * <p>
 * This class provides factory methods for creating both Retra-style and
 * Voltra-style
 * HTTP clients.
 */
public final class Httply {
    private Httply() {
        // No instances
    }

    /**
     * Creates a new Retra-style service factory builder.
     *
     * @return a new builder
     */
    public static ServiceFactory.Builder newServiceFactory() {
        return new ServiceFactory.Builder()
                .client(new HttpClient.Builder().build())
                .converter(new JsonConverter());
    }

    /**
     * Creates a new Retra-style service factory builder with the given base URL.
     *
     * @param baseUrl the base URL for the service
     * @return a new builder
     */
    public static ServiceFactory.Builder newServiceFactory(String baseUrl) {
        return newServiceFactory()
                .baseUrl(baseUrl);
    }

    /**
     * Creates a new Retra-style service factory builder with the given base URL
     * and converter.
     *
     * @param baseUrl   the base URL for the service
     * @param converter the converter to use
     * @return a new builder
     */
    public static ServiceFactory.Builder newServiceFactory(String baseUrl, Converter converter) {
        return newServiceFactory()
                .baseUrl(baseUrl)
                .converter(converter);
    }

    /**
     * Creates a new Voltra-style request queue.
     *
     * @param context the context
     * @return a new request queue
     */
    public static RequestQueue newRequestQueue(Context context) {
        HttpClient client = new HttpClient.Builder().build();
        RequestQueue queue = new RequestQueue(client);
        queue.start();
        return queue;
    }

    /**
     * Creates a new Voltra-style request queue with the given thread pool size.
     *
     * @param context        the context
     * @param threadPoolSize the number of threads to use
     * @return a new request queue
     */
    public static RequestQueue newRequestQueue(Context context, int threadPoolSize) {
        HttpClient client = new HttpClient.Builder().build();
        RequestQueue queue = new RequestQueue(client, threadPoolSize);
        queue.start();
        return queue;
    }

    /**
     * Creates a new Voltra-style request queue with the given HTTP client.
     *
     * @param context    the context
     * @param httpClient the HTTP client to use
     * @return a new request queue
     */
    public static RequestQueue newRequestQueue(Context context, HttpClient httpClient) {
        RequestQueue queue = new RequestQueue(httpClient);
        queue.start();
        return queue;
    }

    /**
     * Creates a new HTTP client builder.
     *
     * @return a new builder
     */
    public static HttpClient.Builder newHttpClient() {
        return new HttpClient.Builder();
    }

    /**
     * Creates a new HTTP client with the given connect and read timeouts.
     *
     * @param connectTimeout the connect timeout
     * @param readTimeout    the read timeout
     * @param unit           the time unit
     * @return a new HTTP client
     */
    public static HttpClient newHttpClient(long connectTimeout, long readTimeout, TimeUnit unit) {
        return new HttpClient.Builder()
                .connectTimeout(connectTimeout, unit)
                .readTimeout(readTimeout, unit)
                .build();
    }

}
