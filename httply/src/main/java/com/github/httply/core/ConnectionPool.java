package com.github.httply.core;

import java.net.HttpURLConnection;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manages reuse of HTTP connections to reduce latency.
 */
public final class ConnectionPool {
    private static final int DEFAULT_MAX_IDLE_CONNECTIONS = 5;
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 5 * 60 * 1000; // 5 minutes

    private final int maxIdleConnections;
    private final long keepAliveDurationMs;
    private final Deque<ConnectionEntry> connections = new ArrayDeque<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private boolean cleanupScheduled = false;

    /**
     * Creates a new connection pool with default settings.
     */
    public ConnectionPool() {
        this(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVE_DURATION_MS, TimeUnit.MILLISECONDS);
    }

    /**
     * Creates a new connection pool.
     *
     * @param maxIdleConnections maximum number of idle connections to keep in the pool
     * @param keepAliveDuration  time to keep idle connections alive
     * @param timeUnit           unit of time for keepAliveDuration
     */
    public ConnectionPool(int maxIdleConnections, long keepAliveDuration, TimeUnit timeUnit) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationMs = timeUnit.toMillis(keepAliveDuration);
        
        // Schedule cleanup task
        executor.scheduleWithFixedDelay(this::cleanup, keepAliveDurationMs / 2, 
                keepAliveDurationMs / 2, TimeUnit.MILLISECONDS);
    }

    /**
     * Returns a connection from the pool for the given URL, or null if none is available.
     *
     * @param url the URL to get a connection for
     * @return a connection, or null if none is available
     */
    public synchronized HttpURLConnection get(HttpUrl url) {
        String host = url.host();
        int port = url.port() != -1 ? url.port() : getDefaultPort(url.scheme());
        
        Iterator<ConnectionEntry> iterator = connections.iterator();
        while (iterator.hasNext()) {
            ConnectionEntry entry = iterator.next();
            if (entry.matches(host, port)) {
                iterator.remove();
                return entry.connection;
            }
        }
        
        return null;
    }

    /**
     * Adds a connection to the pool.
     *
     * @param connection the connection to add
     * @param url        the URL the connection is for
     */
    public synchronized void put(HttpURLConnection connection, HttpUrl url) {
        if (connection == null || url == null) {
            return;
        }
        
        // Don't pool connections that aren't keep-alive
        String connectionHeader = connection.getHeaderField("Connection");
        if ("close".equalsIgnoreCase(connectionHeader)) {
            return;
        }
        
        // Add the connection to the pool
        String host = url.host();
        int port = url.port() != -1 ? url.port() : getDefaultPort(url.scheme());
        ConnectionEntry entry = new ConnectionEntry(connection, host, port, System.currentTimeMillis());
        connections.addFirst(entry);
        
        // Clean up if we have too many connections
        while (connections.size() > maxIdleConnections) {
            connections.removeLast();
        }
    }

    /**
     * Closes all connections in the pool.
     */
    public synchronized void closeAll() {
        for (ConnectionEntry entry : connections) {
            entry.connection.disconnect();
        }
        connections.clear();
        executor.shutdown();
    }

    /**
     * Cleans up expired connections.
     */
    private synchronized int cleanup() {
        long now = System.currentTimeMillis();
        int removed = 0;
        
        Iterator<ConnectionEntry> iterator = connections.iterator();
        while (iterator.hasNext()) {
            ConnectionEntry entry = iterator.next();
            if (now - entry.createdAt > keepAliveDurationMs) {
                entry.connection.disconnect();
                iterator.remove();
                removed++;
            }
        }
        
        return removed;
    }

    private int getDefaultPort(String scheme) {
        return "https".equalsIgnoreCase(scheme) ? 443 : 80;
    }

    /**
     * Represents a connection in the pool.
     */
    private static final class ConnectionEntry {
        final HttpURLConnection connection;
        final String host;
        final int port;
        final long createdAt;

        ConnectionEntry(HttpURLConnection connection, String host, int port, long createdAt) {
            this.connection = connection;
            this.host = host;
            this.port = port;
            this.createdAt = createdAt;
        }

        boolean matches(String host, int port) {
            return this.host.equals(host) && this.port == port;
        }
    }
}
