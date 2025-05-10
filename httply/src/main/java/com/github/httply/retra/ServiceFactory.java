package com.github.httply.retra;

import com.github.httply.core.HttpClient;
import com.github.httply.core.HttpUrl;
import com.github.httply.retra.annotations.Converter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates implementations of service interfaces.
 */
public final class ServiceFactory {
    private final HttpClient client;
    private final HttpUrl baseUrl;
    private final Converter converter;
    private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();

    private ServiceFactory(Builder builder) {
        this.client = builder.client;
        this.baseUrl = builder.baseUrl;
        this.converter = builder.converter;
    }

    /**
     * Creates an implementation of the given service interface.
     *
     * @param serviceClass the service interface
     * @param <T>          the service type
     * @return an implementation of the service interface
     */
    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> serviceClass) {
        validateServiceInterface(serviceClass);
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[] { serviceClass },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }

                        ServiceMethod serviceMethod = loadServiceMethod(method);
                        return serviceMethod.invoke(args);
                    }
                });
    }

    /**
     * Validates that the given class is a valid service interface.
     */
    private void validateServiceInterface(Class<?> serviceClass) {
        if (!serviceClass.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (serviceClass.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    /**
     * Loads or creates a ServiceMethod for the given method.
     */
    @SuppressWarnings("unchecked")
    private ServiceMethod<?> loadServiceMethod(Method method) {
        ServiceMethod<?> result = serviceMethodCache.get(method);
        if (result != null) {
            return result;
        }

        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                ServiceMethod.Builder<?> builder = new ServiceMethod.Builder<>(client, method, converter);
                builder.baseUrl(baseUrl);
                result = builder.build();
                serviceMethodCache.put(method, result);
            }
        }

        return result;
    }

    /**
     * Builder for {@link ServiceFactory}.
     */
    public static final class Builder {
        private HttpClient client;
        private HttpUrl baseUrl;
        private Converter converter;

        public Builder() {
        }

        /**
         * Sets the HTTP client for this factory.
         */
        public Builder client(HttpClient client) {
            this.client = client;
            return this;
        }

        /**
         * Sets the base URL for this factory.
         */
        public Builder baseUrl(HttpUrl baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Sets the base URL for this factory.
         */
        public Builder baseUrl(String baseUrl) {
            return baseUrl(HttpUrl.parse(baseUrl));
        }

        /**
         * Sets the converter for this factory.
         */
        public Builder converter(Converter converter) {
            this.converter = converter;
            return this;
        }

        /**
         * Builds a new {@link ServiceFactory}.
         */
        public ServiceFactory build() {
            if (client == null) {
                client = new HttpClient.Builder().build();
            }
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            if (converter == null) {
                converter = new JsonConverter();
            }
            return new ServiceFactory(this);
        }
    }
}
