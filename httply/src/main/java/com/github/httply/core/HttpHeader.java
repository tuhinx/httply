package com.github.httply.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents HTTP headers in a request or response.
 * This class is immutable once created.
 */
public final class HttpHeader {
    private final Map<String, List<String>> headers;

    /**
     * Common HTTP header names.
     */
    public static final class Names {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_DISPOSITION = "Content-Disposition";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String HOST = "Host";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String PRAGMA = "Pragma";
        public static final String REFERER = "Referer";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String USER_AGENT = "User-Agent";

        private Names() {
            // No instances
        }
    }

    /**
     * Common HTTP header values.
     */
    public static final class Values {
        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String TEXT_PLAIN = "text/plain";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String CLOSE = "close";

        private Values() {
            // No instances
        }
    }

    private HttpHeader(Builder builder) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : builder.headers.entrySet()) {
            String key = entry.getKey();
            List<String> value = new ArrayList<>(entry.getValue());
            map.put(key, Collections.unmodifiableList(value));
        }
        this.headers = Collections.unmodifiableMap(map);
    }

    /**
     * Returns the first value for the specified header name, or null if none.
     *
     * @param name the header name
     * @return the first header value, or null if none
     */
    public String get(String name) {
        List<String> values = headers.get(name);
        return values != null && !values.isEmpty() ? values.get(0) : null;
    }

    /**
     * Returns all values for the specified header name, or an empty list if none.
     *
     * @param name the header name
     * @return a list of header values
     */
    public List<String> getAll(String name) {
        List<String> values = headers.get(name);
        return values != null ? values : Collections.emptyList();
    }

    /**
     * Returns a set of all header names.
     *
     * @return a set of header names
     */
    public Set<String> names() {
        return headers.keySet();
    }

    /**
     * Returns the underlying map of headers.
     *
     * @return an unmodifiable map of headers
     */
    public Map<String, List<String>> toMap() {
        return headers;
    }

    /**
     * Returns a new builder initialized with the values from this header.
     *
     * @return a new builder
     */
    public Builder newBuilder() {
        Builder builder = new Builder();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String name = entry.getKey();
            List<String> values = entry.getValue();
            for (String value : values) {
                builder.add(name, value);
            }
        }
        return builder;
    }

    /**
     * Builder for {@link HttpHeader}.
     */
    public static final class Builder {
        private final Map<String, List<String>> headers = new LinkedHashMap<>();

        public Builder() {
        }

        /**
         * Adds a header with the specified name and value.
         *
         * @param name  the header name
         * @param value the header value
         * @return this builder
         */
        public Builder add(String name, String value) {
            List<String> values = headers.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        /**
         * Sets a header with the specified name and value, removing any existing values.
         *
         * @param name  the header name
         * @param value the header value
         * @return this builder
         */
        public Builder set(String name, String value) {
            List<String> values = new ArrayList<>();
            values.add(value);
            headers.put(name, values);
            return this;
        }

        /**
         * Removes all headers with the specified name.
         *
         * @param name the header name
         * @return this builder
         */
        public Builder remove(String name) {
            headers.remove(name);
            return this;
        }

        /**
         * Builds a new {@link HttpHeader}.
         *
         * @return a new HttpHeader
         */
        public HttpHeader build() {
            return new HttpHeader(this);
        }
    }
}
