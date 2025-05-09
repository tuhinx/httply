package com.github.httply.voltra;

import com.github.httply.core.HttpClient;
import com.github.httply.core.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A queue for handling Voltra-style requests.
 */
public class RequestQueue {
    /**
     * Default number of network threads.
     */
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

    /**
     * The queue of requests that are waiting to be processed.
     */
    private final BlockingQueue<Request<?>> requestQueue = new LinkedBlockingQueue<>();

    /**
     * The set of requests currently being processed.
     */
    private final Set<Request<?>> currentRequests = new HashSet<>();

    /**
     * The HTTP client used to make requests.
     */
    private final HttpClient httpClient;

    /**
     * The executor service used to process requests.
     */
    private final ExecutorService executorService;

    /**
     * A map of request tags to lists of requests.
     */
    private final Map<Object, List<Request<?>>> requestsByTag = new HashMap<>();

    /**
     * Whether the queue is currently running.
     */
    private boolean isRunning = false;

    /**
     * Creates a new request queue with the given HTTP client.
     *
     * @param httpClient the HTTP client to use
     */
    public RequestQueue(HttpClient httpClient) {
        this(httpClient, DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /**
     * Creates a new request queue with the given HTTP client and thread pool size.
     *
     * @param httpClient     the HTTP client to use
     * @param threadPoolSize the number of threads to use
     */
    public RequestQueue(HttpClient httpClient, int threadPoolSize) {
        this.httpClient = httpClient;

        // Create a thread pool with the specified number of threads
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Httply-Voltra-" + counter.getAndIncrement());
            }
        };

        this.executorService = new ThreadPoolExecutor(
                threadPoolSize, threadPoolSize,
                0L, TimeUnit.MILLISECONDS,
                workQueue, threadFactory);
    }

    /**
     * Starts the request queue.
     */
    public void start() {
        if (isRunning) {
            return;
        }

        isRunning = true;
    }

    /**
     * Stops the request queue.
     */
    public void stop() {
        isRunning = false;
        executorService.shutdown();
    }

    /**
     * Adds a request to the queue.
     *
     * @param request the request to add
     * @param <T>     the type of response
     * @return the request
     */
    public <T> Request<T> add(Request<T> request) {
        // Add the request to the queue
        synchronized (currentRequests) {
            currentRequests.add(request);
        }

        // Add the request to the tag map
        if (request.getTag() != null) {
            synchronized (requestsByTag) {
                List<Request<?>> requests = requestsByTag.get(request.getTag());
                if (requests == null) {
                    requests = new ArrayList<>();
                    requestsByTag.put(request.getTag(), requests);
                }
                requests.add(request);
            }
        }

        // Process the request
        executorService.execute(new RequestProcessor<>(request));

        return request;
    }

    /**
     * Cancels all requests with the given tag.
     *
     * @param tag the tag
     */
    public void cancelAll(Object tag) {
        if (tag == null) {
            return;
        }

        synchronized (requestsByTag) {
            List<Request<?>> requests = requestsByTag.remove(tag);
            if (requests != null) {
                for (Request<?> request : requests) {
                    synchronized (currentRequests) {
                        currentRequests.remove(request);
                    }
                }
            }
        }
    }

    /**
     * Processes a request.
     *
     * @param <T> the type of response
     */
    private class RequestProcessor<T> implements Runnable {
        private final Request<T> request;

        RequestProcessor(Request<T> request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                // Check if the request has been canceled
                synchronized (currentRequests) {
                    if (!currentRequests.contains(request)) {
                        return;
                    }
                }

                // Execute the request
                HttpResponse response = httpClient.execute(request.toHttpRequest());

                // Check if the request has been canceled
                synchronized (currentRequests) {
                    if (!currentRequests.contains(request)) {
                        return;
                    }
                }

                // Parse the response
                if (response.isSuccessful()) {
                    T result = request.parseResponse(response);
                    request.deliverResponse(result);
                } else {
                    request.deliverError(new VoltraError(response));
                }
            } catch (IOException e) {
                // Check if we should retry the request
                if (!request.hasExceededMaxRetries()) {
                    request.incrementRetryCount();
                    executorService.execute(this);
                } else {
                    request.deliverError(new VoltraError(e));
                }
            } catch (Exception e) {
                request.deliverError(new VoltraError(e));
            } finally {
                // Remove the request from the current requests
                synchronized (currentRequests) {
                    currentRequests.remove(request);
                }

                // Remove the request from the tag map
                if (request.getTag() != null) {
                    synchronized (requestsByTag) {
                        List<Request<?>> requests = requestsByTag.get(request.getTag());
                        if (requests != null) {
                            requests.remove(request);
                            if (requests.isEmpty()) {
                                requestsByTag.remove(request.getTag());
                            }
                        }
                    }
                }
            }
        }
    }
}
