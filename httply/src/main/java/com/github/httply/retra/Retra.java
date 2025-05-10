package com.github.httply.retra;

import com.github.httply.core.HttpClient;
import com.github.httply.core.HttpUrl;
import com.github.httply.retra.annotations.Converter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Retra adapts a Java interface to HTTP calls by using annotations on the
 * declared methods to
 * define how requests are made. Create instances using {@linkplain Builder the
 * builder} and pass
 * your interface to {@link #create} to generate an implementation.
 */
public final class Retra {
    private final HttpUrl baseUrl;
    private final HttpClient client;
    private final List<Converter.Factory> converterFactories;
    private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();

    private Retra(HttpUrl baseUrl, HttpClient client, List<Converter.Factory> converterFactories) {
        this.baseUrl = baseUrl;
        this.client = client;
        this.converterFactories = converterFactories;
    }

    /**
     * Create an implementation of the API endpoints defined by the {@code service}
     * interface.
     */
    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(
                service.getClassLoader(),
                new Class<?>[] { service },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }

                        ServiceMethod<?> serviceMethod = loadServiceMethod(method);
                        return serviceMethod.invoke(args);
                    }
                });
    }

    /**
     * Validates that the given class is a valid service interface.
     */
    private void validateServiceInterface(Class<?> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    /**
     * Loads or creates a ServiceMethod for the given method.
     */
    private ServiceMethod<?> loadServiceMethod(Method method) {
        ServiceMethod<?> result = serviceMethodCache.get(method);
        if (result != null) {
            return result;
        }

        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                Converter converter = null;
                for (Converter.Factory factory : converterFactories) {
                    converter = factory.createConverter();
                    if (converter != null)
                        break;
                }

                if (converter == null) {
                    converter = new JsonConverter(); // Default converter
                }

                ServiceMethod.Builder<?> builder = new ServiceMethod.Builder<>(client, method, converter);
                builder.baseUrl(baseUrl);
                result = builder.build();
                serviceMethodCache.put(method, result);
            }
        }

        return result;
    }

    /**
     * Build a new {@link Retra}.
     */
    public static final class Builder {
        private HttpUrl baseUrl;
        private HttpClient client;
        private final List<Converter.Factory> converterFactories = new ArrayList<>();

        public Builder() {
        }

        /**
         * Set the API base URL.
         */
        public Builder baseUrl(String baseUrl) {
            return baseUrl(HttpUrl.parse(baseUrl));
        }

        /**
         * Set the API base URL.
         */
        public Builder baseUrl(HttpUrl baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Add a converter factory for serialization and deserialization of objects.
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            converterFactories.add(factory);
            return this;
        }

        /**
         * Set the HTTP client used for requests.
         */
        public Builder client(HttpClient client) {
            this.client = client;
            return this;
        }

        /**
         * Create the {@link Retra} instance using the configured values.
         */
        public Retra build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }

            if (client == null) {
                client = new HttpClient.Builder().build();
            }

            return new Retra(baseUrl, client, converterFactories);
        }
    }
}
