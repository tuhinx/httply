package com.github.httply.retra;

import com.github.httply.core.HttpRequest;
import com.github.httply.core.HttpResponse;

import java.io.IOException;
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
     * @throws IOException if an I/O error occurs
     */
    Object fromResponse(HttpResponse response, Type type) throws IOException;

    /**
     * Creates {@link Converter} instances based on a type and target usage.
     */
    abstract class Factory {
        /**
         * Create a converter for serialization and deserialization.
         */
        public abstract Converter createConverter();
    }
}
