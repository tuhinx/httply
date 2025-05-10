package com.github.httply.core;

/**
 * Enum representing HTTP methods.
 */
public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    OPTIONS,
    PATCH,
    TRACE;

    /**
     * Returns whether this HTTP method has a request body.
     *
     * @return true if this method permits a request body, false otherwise
     */
    public boolean hasRequestBody() {
        return this == POST || this == PUT || this == PATCH;
    }

    /**
     * Returns whether this HTTP method requires a request body.
     *
     * @return true if this method requires a request body, false otherwise
     */
    public boolean requiresRequestBody() {
        return this == POST || this == PUT || this == PATCH;
    }

    /**
     * Returns whether this HTTP method permits a request body.
     *
     * @return true if this method permits a request body, false otherwise
     */
    public boolean permitsRequestBody() {
        return this != GET && this != HEAD;
    }
}
