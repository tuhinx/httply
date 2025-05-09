package com.github.httply.retra;

import com.github.httply.core.HttpRequest;
import com.github.httply.core.HttpResponse;

import java.lang.reflect.Type;

/**
 * Converts between Java objects and HTTP request/response bodies.
 */
public interface Converter {
    /**
     * Converts an object to a request body.
     *
     * @param value the object to convert
     * @param type  the type of the object
     * @return the request body
     */
    HttpRequest.RequestBody toRequestBody(Object value, Type type);

    /**
     * Converts a response to an object.
     *
     * @param response the response to convert
     * @param type     the type to convert to
     * @return the converted object
     */
    Object fromResponse(HttpResponse response, Type type);
}
