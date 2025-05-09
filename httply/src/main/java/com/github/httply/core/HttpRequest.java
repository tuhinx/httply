package com.github.httply.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Represents an HTTP request.
 * This class is immutable once created.
 */
public final class HttpRequest {
    private final HttpMethod method;
    private final HttpUrl url;
    private final HttpHeader headers;
    private final RequestBody body;
    private final int timeoutMs;

    private HttpRequest(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.timeoutMs = builder.timeoutMs;
    }

    /**
     * Returns the HTTP method of this request.
     */
    public HttpMethod method() {
        return method;
    }

    /**
     * Returns the URL of this request.
     */
    public HttpUrl url() {
        return url;
    }

    /**
     * Returns the headers of this request.
     */
    public HttpHeader headers() {
        return headers;
    }

    /**
     * Returns the body of this request, or null if none.
     */
    public RequestBody body() {
        return body;
    }

    /**
     * Returns the timeout in milliseconds for this request.
     */
    public int timeoutMs() {
        return timeoutMs;
    }

    /**
     * Returns a new builder initialized with the values from this request.
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Builder for {@link HttpRequest}.
     */
    public static final class Builder {
        private HttpMethod method;
        private HttpUrl url;
        private final HttpHeader.Builder headers = new HttpHeader.Builder();
        private RequestBody body;
        private int timeoutMs = 30000; // Default timeout: 30 seconds

        public Builder() {
        }

        private Builder(HttpRequest request) {
            this.method = request.method;
            this.url = request.url;
            this.headers.remove(""); // Clear all headers
            for (String name : request.headers.names()) {
                for (String value : request.headers.getAll(name)) {
                    this.headers.add(name, value);
                }
            }
            this.body = request.body;
            this.timeoutMs = request.timeoutMs;
        }

        /**
         * Sets the HTTP method for this request.
         */
        public Builder method(HttpMethod method, RequestBody body) {
            if (method == null) {
                throw new NullPointerException("method == null");
            }
            if (method.requiresRequestBody() && body == null) {
                throw new IllegalArgumentException("method " + method + " requires a request body");
            }
            if (!method.permitsRequestBody() && body != null) {
                throw new IllegalArgumentException("method " + method + " does not permit a request body");
            }
            this.method = method;
            this.body = body;
            return this;
        }

        /**
         * Sets a GET method for this request.
         */
        public Builder get() {
            return method(HttpMethod.GET, null);
        }

        /**
         * Sets a POST method for this request with the specified body.
         */
        public Builder post(RequestBody body) {
            return method(HttpMethod.POST, body);
        }

        /**
         * Sets a PUT method for this request with the specified body.
         */
        public Builder put(RequestBody body) {
            return method(HttpMethod.PUT, body);
        }

        /**
         * Sets a DELETE method for this request.
         */
        public Builder delete() {
            return method(HttpMethod.DELETE, null);
        }

        /**
         * Sets a DELETE method for this request with the specified body.
         */
        public Builder delete(RequestBody body) {
            return method(HttpMethod.DELETE, body);
        }

        /**
         * Sets a HEAD method for this request.
         */
        public Builder head() {
            return method(HttpMethod.HEAD, null);
        }

        /**
         * Sets a PATCH method for this request with the specified body.
         */
        public Builder patch(RequestBody body) {
            return method(HttpMethod.PATCH, body);
        }

        /**
         * Sets the URL for this request.
         */
        public Builder url(HttpUrl url) {
            if (url == null) {
                throw new NullPointerException("url == null");
            }
            this.url = url;
            return this;
        }

        /**
         * Sets the URL for this request.
         */
        public Builder url(String url) {
            if (url == null) {
                throw new NullPointerException("url == null");
            }
            return url(HttpUrl.parse(url));
        }

        /**
         * Adds a header to this request.
         */
        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        /**
         * Sets a header for this request, removing any existing values.
         */
        public Builder setHeader(String name, String value) {
            headers.set(name, value);
            return this;
        }

        /**
         * Removes a header from this request.
         */
        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        /**
         * Sets the timeout in milliseconds for this request.
         */
        public Builder timeout(int timeoutMs) {
            if (timeoutMs < 0) {
                throw new IllegalArgumentException("timeout < 0");
            }
            this.timeoutMs = timeoutMs;
            return this;
        }

        /**
         * Builds a new {@link HttpRequest}.
         */
        public HttpRequest build() {
            if (method == null) {
                throw new IllegalStateException("method == null");
            }
            if (url == null) {
                throw new IllegalStateException("url == null");
            }
            return new HttpRequest(this);
        }
    }

    /**
     * Represents the body of an HTTP request.
     */
    public abstract static class RequestBody {
        /**
         * Returns the content type of this body.
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
         * Creates a request body from the specified string and content type.
         */
        public static RequestBody create(final String content, final String contentType) {
            return create(content, contentType, StandardCharsets.UTF_8);
        }

        /**
         * Creates a request body from the specified string, content type, and charset.
         */
        public static RequestBody create(final String content, final String contentType, final Charset charset) {
            if (content == null) {
                throw new NullPointerException("content == null");
            }
            final byte[] bytes = content.getBytes(charset);
            return new RequestBody() {
                @Override
                public String contentType() {
                    return contentType;
                }

                @Override
                public long contentLength() {
                    return bytes.length;
                }

                @Override
                public InputStream content() {
                    return new ByteArrayInputStream(bytes);
                }
            };
        }

        /**
         * Creates a request body from the specified bytes and content type.
         */
        public static RequestBody create(final byte[] content, final String contentType) {
            if (content == null) {
                throw new NullPointerException("content == null");
            }
            return new RequestBody() {
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
            };
        }
    }
}
