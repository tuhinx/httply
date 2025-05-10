package com.github.httply.retra;

import android.util.Log;

import com.github.httply.core.HttpClient;
import com.github.httply.core.HttpHeader;
import com.github.httply.core.HttpRequest;
import com.github.httply.core.HttpResponse;
import com.github.httply.core.HttpUrl;
import com.github.httply.retra.annotations.Body;
import com.github.httply.retra.annotations.Call;
import com.github.httply.retra.annotations.Converter;
import com.github.httply.retra.annotations.DELETE;
import com.github.httply.retra.annotations.GET;
import com.github.httply.retra.annotations.HEAD;
import com.github.httply.retra.annotations.Header;
import com.github.httply.retra.annotations.Headers;
import com.github.httply.retra.annotations.HttpMethod;
import com.github.httply.retra.annotations.PATCH;
import com.github.httply.retra.annotations.POST;
import com.github.httply.retra.annotations.PUT;
import com.github.httply.retra.annotations.Path;
import com.github.httply.retra.annotations.Query;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a method in a service interface.
 *
 * @param <T> The return type of the method.
 */
public class ServiceMethod<T> {
    private static final String TAG = "ServiceMethod";
    private static final Pattern PATH_PARAM_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

    private final HttpClient client;
    private final Method method;
    private final String httpMethod;
    private final String relativeUrl;
    private final HttpUrl baseUrl;
    private final HttpHeader.Builder headers;
    private final List<ParameterHandler<?>> parameterHandlers;
    private final Converter converter;
    private final Type responseType;

    private ServiceMethod(Builder builder) {
        this.client = builder.client;
        this.method = builder.method;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.baseUrl = builder.baseUrl;
        this.headers = builder.headers;
        this.parameterHandlers = builder.parameterHandlers;
        this.converter = builder.converter;
        this.responseType = builder.responseType;
    }

    /**
     * Creates a Call object for the service method with the given arguments.
     */
    public Call<T> invoke(Object[] args) {
        return new OkHttpCall<>(this, args);
    }

    /**
     * Executes the service method with the given arguments and returns the HTTP
     * response.
     */
    public HttpResponse toResponse(Object[] args) throws IOException {
        // Build the request
        HttpRequest request = buildRequest(args);

        // Execute the request
        return client.execute(request);
    }

    /**
     * Parses the HTTP response into the expected return type.
     */
    @SuppressWarnings("unchecked")
    public T parseResponse(HttpResponse response) throws IOException {
        // Convert the response
        if (responseType == HttpResponse.class) {
            return (T) response;
        } else {
            return (T) converter.fromResponse(response, responseType);
        }
    }

    /**
     * Builds an HTTP request from the method arguments.
     */
    private HttpRequest buildRequest(Object[] args) {
        // Build the URL
        String url = buildUrl(args);

        // Build the request
        HttpRequest.Builder requestBuilder = new HttpRequest.Builder();

        // Set the URL
        requestBuilder.url(url);

        // Set the HTTP method
        com.github.httply.core.HttpMethod coreMethod = com.github.httply.core.HttpMethod.valueOf(httpMethod);

        // Set the headers
        HttpHeader.Builder headersBuilder = new HttpHeader.Builder();

        // Apply parameter handlers
        HttpRequest.RequestBody body = null;
        for (int i = 0; i < parameterHandlers.size(); i++) {
            ParameterHandler<?> handler = parameterHandlers.get(i);
            Object arg = args[i];
            if (arg != null) {
                @SuppressWarnings({ "unchecked", "rawtypes" })
                Object result = ((ParameterHandler) handler).apply(arg);
                if (result instanceof HttpRequest.RequestBody) {
                    body = (HttpRequest.RequestBody) result;
                }
            }
        }

        // Set the method and body
        requestBuilder.method(coreMethod, body);

        // Set the headers
        HttpHeader requestHeaders = headersBuilder.build();
        for (String name : requestHeaders.names()) {
            for (String value : requestHeaders.getAll(name)) {
                requestBuilder.addHeader(name, value);
            }
        }

        return requestBuilder.build();
    }

    /**
     * Builds the URL for the request.
     */
    private String buildUrl(Object[] args) {
        // Replace path parameters
        String path = relativeUrl;
        Matcher matcher = PATH_PARAM_PATTERN.matcher(path);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1);
            String value = "";
            for (int i = 0; i < parameterHandlers.size(); i++) {
                ParameterHandler<?> handler = parameterHandlers.get(i);
                if (handler instanceof ParameterHandler.PathHandler) {
                    ParameterHandler.PathHandler pathHandler = (ParameterHandler.PathHandler) handler;
                    if (pathHandler.name.equals(name)) {
                        value = String.valueOf(args[i]);
                        break;
                    }
                }
            }
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb);
        path = sb.toString();

        // Combine baseUrl with path
        String fullUrl;
        if (baseUrl != null) {
            String baseUrlStr = baseUrl.toString();

            // Log the URL components for debugging
            Log.d(TAG, "Base URL: " + baseUrlStr);
            Log.d(TAG, "Path: " + path);

            // Ensure baseUrl ends with / and path doesn't start with /
            if (baseUrlStr.endsWith("/")) {
                if (path.startsWith("/")) {
                    path = path.substring(1);
                }
                fullUrl = baseUrlStr + path;
            } else {
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
                fullUrl = baseUrlStr + path;
            }

            // Log the full URL for debugging
            Log.d(TAG, "Full URL: " + fullUrl);
        } else {
            fullUrl = path;
            Log.d(TAG, "No base URL, using path as full URL: " + fullUrl);
        }

        // Add query parameters
        HttpUrl.Builder urlBuilder = HttpUrl.parse(fullUrl).newBuilder();
        for (int i = 0; i < parameterHandlers.size(); i++) {
            ParameterHandler<?> handler = parameterHandlers.get(i);
            if (handler instanceof ParameterHandler.QueryHandler) {
                ParameterHandler.QueryHandler queryHandler = (ParameterHandler.QueryHandler) handler;
                Object arg = args[i];
                if (arg != null) {
                    urlBuilder.addQueryParameter(queryHandler.name, String.valueOf(arg));
                }
            }
        }

        return urlBuilder.build().toString();
    }

    /**
     * Builder for {@link ServiceMethod}.
     */
    public static final class Builder<T> {
        private final HttpClient client;
        private final Method method;
        private final Annotation[] methodAnnotations;
        private final Annotation[][] parameterAnnotationsArray;
        private final Type[] parameterTypes;
        private final Converter converter;

        private String httpMethod;
        private String relativeUrl;
        private HttpUrl baseUrl;
        private final HttpHeader.Builder headers = new HttpHeader.Builder();
        private final List<ParameterHandler<?>> parameterHandlers = new ArrayList<>();
        private Type responseType;

        public Builder(HttpClient client, Method method, Converter converter) {
            this.client = client;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.converter = converter;

            // Extract the response type from the Call<T> return type
            Type returnType = method.getGenericReturnType();
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalArgumentException(
                        "Return type must be parameterized as Call<Foo> or Call<? extends Foo>");
            }

            Type rawReturnType = ((ParameterizedType) returnType).getRawType();
            if (rawReturnType != Call.class) {
                throw new IllegalArgumentException(
                        "Return type must be Call<T>, found: " + returnType);
            }

            Type callResponseType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
            this.responseType = callResponseType;
        }

        /**
         * Sets the base URL for this service method.
         */
        public Builder baseUrl(HttpUrl baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Builds a new {@link ServiceMethod}.
         */
        @SuppressWarnings("unchecked")
        public ServiceMethod<T> build() {
            // Parse method annotations
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            if (httpMethod == null) {
                throw new IllegalArgumentException("HTTP method annotation is required (e.g., @GET, @POST).");
            }

            // Parse parameter annotations
            int parameterCount = parameterAnnotationsArray.length;
            for (int i = 0; i < parameterCount; i++) {
                Type parameterType = parameterTypes[i];
                Annotation[] parameterAnnotations = parameterAnnotationsArray[i];

                ParameterHandler<?> handler = null;
                for (Annotation annotation : parameterAnnotations) {
                    ParameterHandler<?> annotationHandler = parseParameterAnnotation(i, parameterType, annotation);
                    if (annotationHandler != null) {
                        if (handler != null) {
                            throw new IllegalArgumentException(
                                    "Multiple parameter annotations found at index " + i);
                        }
                        handler = annotationHandler;
                    }
                }

                if (handler == null) {
                    throw new IllegalArgumentException(
                            "No parameter annotation found at index " + i);
                }

                parameterHandlers.add(handler);
            }

            return (ServiceMethod<T>) new ServiceMethod<>(this);
        }

        /**
         * Parses a method annotation.
         */
        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                httpMethod = "GET";
                relativeUrl = ((GET) annotation).value();
            } else if (annotation instanceof POST) {
                httpMethod = "POST";
                relativeUrl = ((POST) annotation).value();
            } else if (annotation instanceof PUT) {
                httpMethod = "PUT";
                relativeUrl = ((PUT) annotation).value();
            } else if (annotation instanceof DELETE) {
                httpMethod = "DELETE";
                relativeUrl = ((DELETE) annotation).value();
            } else if (annotation instanceof HEAD) {
                httpMethod = "HEAD";
                relativeUrl = ((HEAD) annotation).value();
            } else if (annotation instanceof PATCH) {
                httpMethod = "PATCH";
                relativeUrl = ((PATCH) annotation).value();
            } else if (annotation instanceof Headers) {
                String[] headersToParse = ((Headers) annotation).value();
                if (headersToParse.length == 0) {
                    return;
                }
                for (String header : headersToParse) {
                    int colon = header.indexOf(':');
                    if (colon == -1 || colon == 0 || colon == header.length() - 1) {
                        throw new IllegalArgumentException(
                                "@Headers value must be in the form \"Name: Value\". Found: \"" + header + "\"");
                    }
                    String name = header.substring(0, colon).trim();
                    String value = header.substring(colon + 1).trim();
                    headers.add(name, value);
                }
            } else {
                // Look for a @HttpMethod annotation
                HttpMethod httpMethodAnnotation = annotation.annotationType().getAnnotation(HttpMethod.class);
                if (httpMethodAnnotation != null) {
                    httpMethod = httpMethodAnnotation.value();
                    try {
                        // Try to get the value() method from the annotation
                        String value = (String) annotation.annotationType().getMethod("value").invoke(annotation);
                        relativeUrl = value;
                    } catch (Exception e) {
                        throw new IllegalArgumentException(
                                "Failed to extract URL from " + annotation.annotationType().getSimpleName(), e);
                    }
                }
            }
        }

        /**
         * Parses a parameter annotation.
         */
        private ParameterHandler<?> parseParameterAnnotation(int index, Type parameterType, Annotation annotation) {
            if (annotation instanceof Path) {
                Path path = (Path) annotation;
                return new ParameterHandler.PathHandler(path.value(), path.encoded());
            } else if (annotation instanceof Query) {
                Query query = (Query) annotation;
                return new ParameterHandler.QueryHandler(query.value(), query.encoded());
            } else if (annotation instanceof Header) {
                Header header = (Header) annotation;
                return new ParameterHandler.HeaderHandler(header.value());
            } else if (annotation instanceof Body) {
                return new ParameterHandler.BodyHandler(converter, parameterType);
            }
            return null;
        }
    }

    /**
     * Handles method parameters.
     */
    abstract static class ParameterHandler<T> {
        abstract Object apply(T value);

        /**
         * Handles path parameters.
         */
        static final class PathHandler extends ParameterHandler<Object> {
            private final String name;
            private final boolean encoded;

            PathHandler(String name, boolean encoded) {
                this.name = name;
                this.encoded = encoded;
            }

            @Override
            Object apply(Object value) {
                return value.toString();
            }
        }

        /**
         * Handles query parameters.
         */
        static final class QueryHandler extends ParameterHandler<Object> {
            private final String name;
            private final boolean encoded;

            QueryHandler(String name, boolean encoded) {
                this.name = name;
                this.encoded = encoded;
            }

            @Override
            Object apply(Object value) {
                return value.toString();
            }
        }

        /**
         * Handles header parameters.
         */
        static final class HeaderHandler extends ParameterHandler<Object> {
            private final String name;

            HeaderHandler(String name) {
                this.name = name;
            }

            @Override
            Object apply(Object value) {
                return value.toString();
            }
        }

        /**
         * Handles request body.
         */
        static final class BodyHandler extends ParameterHandler<Object> {
            private final Converter converter;
            private final Type type;

            BodyHandler(Converter converter, Type type) {
                this.converter = converter;
                this.type = type;
            }

            @Override
            Object apply(Object value) {
                return converter.toRequestBody(value, type);
            }
        }
    }
}
