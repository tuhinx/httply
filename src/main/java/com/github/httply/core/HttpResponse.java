package com.github.httply.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Represents an HTTP response.
 * This class is immutable once created.
 */
public final class HttpResponse {
    private final int code;
    private final String message;
    private final HttpHeader headers;
    private final ResponseBody body;
    private final HttpRequest request;

    private HttpResponse(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.request = builder.request;
    }

    /**
     * Returns the HTTP status code of this response.
     */
    public int code() {
        return code;
    }

    /**
     * Returns the HTTP status message of this response.
     */
    public String message() {
        return message;
    }

    /**
     * Returns the headers of this response.
     */
    public HttpHeader headers() {
        return headers;
    }

    /**
     * Returns the body of this response, or null if none.
     */
    public ResponseBody body() {
        return body;
    }

    /**
     * Returns the request that initiated this response.
     */
    public HttpRequest request() {
        return request;
    }

    /**
     * Returns whether this response was successful (status code in the range
     * [200..300)).
     */
    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    /**
     * Returns a new builder initialized with the values from this response.
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Builder for {@link HttpResponse}.
     */
    public static final class Builder {
        private int code;
        private String message;
        private final HttpHeader.Builder headers = new HttpHeader.Builder();
        private ResponseBody body;
        private HttpRequest request;

        public Builder() {
        }

        private Builder(HttpResponse response) {
            this.code = response.code;
            this.message = response.message;
            this.headers.remove(""); // Clear all headers
            for (String name : response.headers.names()) {
                for (String value : response.headers.getAll(name)) {
                    this.headers.add(name, value);
                }
            }
            this.body = response.body;
            this.request = response.request;
        }

        /**
         * Sets the HTTP status code for this response.
         */
        public Builder code(int code) {
            this.code = code;
            return this;
        }

        /**
         * Sets the HTTP status message for this response.
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Adds a header to this response.
         */
        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        /**
         * Sets a header for this response, removing any existing values.
         */
        public Builder setHeader(String name, String value) {
            headers.set(name, value);
            return this;
        }

        /**
         * Removes a header from this response.
         */
        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        /**
         * Sets the body for this response.
         */
        public Builder body(ResponseBody body) {
            this.body = body;
            return this;
        }

        /**
         * Sets the request that initiated this response.
         */
        public Builder request(HttpRequest request) {
            this.request = request;
            return this;
        }

        /**
         * Builds a new {@link HttpResponse}.
         */
        public HttpResponse build() {
            if (request == null) {
                throw new IllegalStateException("request == null");
            }
            return new HttpResponse(this);
        }
    }

    /**
     * Represents the body of an HTTP response.
     */
    public abstract static class ResponseBody {
        /**
         * Returns the content type of this body, or null if unknown.
         */
        public abstract String contentType();

        /**
         * Returns the content length of this body, or -1 if unknown.
         */
        public abstract long contentLength();

        /**
         * Returns the content of this body as an InputStream.
         */
        public abstract InputStream content();

        /**
         * Returns the content of this body as a byte array.
         */
        public abstract byte[] bytes();

        /**
         * Returns the content of this body as a string.
         */
        public abstract String string();

        /**
         * Creates a response body from the specified bytes and content type.
         */
        public static ResponseBody create(final byte[] content, final String contentType) {
            if (content == null) {
                throw new NullPointerException("content == null");
            }
            return new ResponseBody() {
                // Cache the string representation to avoid multiple conversions
                private String stringCache;

                @Override
                public String contentType() {
                    return contentType;
                }

                @Override
                public long contentLength() {
                    return content.length;
                }

                @Override
                public InputStream content() {
                    return new ByteArrayInputStream(content);
                }

                @Override
                public byte[] bytes() {
                    return content;
                }

                @Override
                public String string() {
                    if (stringCache == null) {
                        stringCache = new String(content);
                    }
                    return stringCache;
                }
            };
        }
    }
}
