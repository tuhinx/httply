package com.github.httply.core;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an HTTP URL with support for parsing and building URLs.
 * This class is immutable once created.
 */
public final class HttpUrl {
    private static final String CHARSET = "UTF-8";

    private final String scheme;
    private final String host;
    private final int port;
    private final String path;
    private final Map<String, List<String>> queryParams;
    private final String fragment;

    private HttpUrl(Builder builder) {
        this.scheme = builder.scheme;
        this.host = builder.host;
        this.port = builder.port;
        this.path = builder.path;

        Map<String, List<String>> params = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : builder.queryParams.entrySet()) {
            params.put(entry.getKey(), Collections.unmodifiableList(new ArrayList<>(entry.getValue())));
        }
        this.queryParams = Collections.unmodifiableMap(params);

        this.fragment = builder.fragment;
    }

    /**
     * Returns the scheme of this URL, e.g., "http" or "https".
     */
    public String scheme() {
        return scheme;
    }

    /**
     * Returns the host of this URL, e.g., "example.com".
     */
    public String host() {
        return host;
    }

    /**
     * Returns the port of this URL, or -1 if not specified.
     */
    public int port() {
        return port;
    }

    /**
     * Returns the path of this URL, e.g., "/path/to/resource".
     */
    public String path() {
        return path;
    }

    /**
     * Returns the query parameters of this URL.
     */
    public Map<String, List<String>> queryParams() {
        return queryParams;
    }

    /**
     * Returns the fragment of this URL, or null if not specified.
     */
    public String fragment() {
        return fragment;
    }

    /**
     * Returns the query string of this URL, e.g., "param1=value1&param2=value2".
     */
    public String query() {
        if (queryParams.isEmpty()) {
            return "";
        }

        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String name = entry.getKey();
            for (String value : entry.getValue()) {
                if (query.length() > 0) {
                    query.append('&');
                }
                query.append(encodeComponent(name)).append('=').append(encodeComponent(value));
            }
        }
        return query.toString();
    }

    /**
     * Returns the URL as a string.
     */
    @Override
    public String toString() {
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(host);

        if (port != -1) {
            url.append(':').append(port);
        }

        url.append(path);

        String query = query();
        if (!query.isEmpty()) {
            url.append('?').append(query);
        }

        if (fragment != null) {
            url.append('#').append(fragment);
        }

        return url.toString();
    }

    /**
     * Returns a new builder initialized with the values from this URL.
     */
    public Builder newBuilder() {
        Builder builder = new Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .fragment(fragment);

        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String name = entry.getKey();
            for (String value : entry.getValue()) {
                builder.addQueryParameter(name, value);
            }
        }

        return builder;
    }

    /**
     * Parses a URL string into an HttpUrl.
     *
     * @param url the URL string to parse
     * @return a new HttpUrl
     * @throws IllegalArgumentException if the URL is malformed
     */
    public static HttpUrl parse(String url) {
        // Trim the URL to remove any leading/trailing whitespace
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }

        url = url.trim();

        // Check for common URL issues
        if (url.contains(" ")) {
            // Replace spaces with %20
            url = url.replace(" ", "%20");
        }

        // Ensure URL has a protocol
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        try {
            URL javaUrl = new URL(url);

            Builder builder = new Builder()
                    .scheme(javaUrl.getProtocol())
                    .host(javaUrl.getHost())
                    .port(javaUrl.getPort())
                    .path(javaUrl.getPath().isEmpty() ? "/" : javaUrl.getPath())
                    .fragment(javaUrl.getRef());

            String query = javaUrl.getQuery();
            if (query != null) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf('=');
                    String name = idx > 0 ? decodeComponent(pair.substring(0, idx)) : decodeComponent(pair);
                    String value = idx > 0 && idx < pair.length() - 1 ? decodeComponent(pair.substring(idx + 1)) : "";
                    builder.addQueryParameter(name, value);
                }
            }

            return builder.build();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + url, e);
        }
    }

    private static String encodeComponent(String component) {
        try {
            return URLEncoder.encode(component, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported", e);
        }
    }

    private static String decodeComponent(String component) {
        try {
            return URLDecoder.decode(component, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported", e);
        }
    }

    /**
     * Builder for {@link HttpUrl}.
     */
    public static final class Builder {
        private String scheme;
        private String host;
        private int port = -1;
        private String path = "/";
        private final Map<String, List<String>> queryParams = new LinkedHashMap<>();
        private String fragment;

        public Builder() {
        }

        /**
         * Sets the scheme for this URL, e.g., "http" or "https".
         */
        public Builder scheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * Sets the host for this URL, e.g., "example.com".
         */
        public Builder host(String host) {
            this.host = host;
            return this;
        }

        /**
         * Sets the port for this URL.
         */
        public Builder port(int port) {
            this.port = port;
            return this;
        }

        /**
         * Sets the path for this URL, e.g., "/path/to/resource".
         */
        public Builder path(String path) {
            this.path = path.startsWith("/") ? path : "/" + path;
            return this;
        }

        /**
         * Adds a query parameter to this URL.
         */
        public Builder addQueryParameter(String name, String value) {
            List<String> values = queryParams.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        /**
         * Sets a query parameter, removing any existing values.
         */
        public Builder setQueryParameter(String name, String value) {
            List<String> values = new ArrayList<>();
            values.add(value);
            queryParams.put(name, values);
            return this;
        }

        /**
         * Removes a query parameter.
         */
        public Builder removeQueryParameter(String name) {
            queryParams.remove(name);
            return this;
        }

        /**
         * Sets the fragment for this URL.
         */
        public Builder fragment(String fragment) {
            this.fragment = fragment;
            return this;
        }

        /**
         * Builds a new {@link HttpUrl}.
         */
        public HttpUrl build() {
            if (scheme == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (host == null) {
                throw new IllegalStateException("host == null");
            }
            return new HttpUrl(this);
        }
    }
}
