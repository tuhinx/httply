package com.github.httply.retra;

import com.github.httply.core.HttpResponse;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementation of {@link Call} that uses OkHttp for the HTTP requests.
 *
 * @param <T> Successful response body type.
 */
final class OkHttpCall<T> implements Call<T> {
    private final ServiceMethod<T> serviceMethod;
    private final Object[] args;
    private final AtomicBoolean canceled = new AtomicBoolean();
    private final AtomicBoolean executed = new AtomicBoolean();

    OkHttpCall(ServiceMethod<T> serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public T execute() throws IOException {
        if (executed.getAndSet(true)) {
            throw new IllegalStateException("Already executed.");
        }
        if (canceled.get()) {
            throw new IOException("Canceled");
        }
        
        HttpResponse response = serviceMethod.toResponse(args);
        return serviceMethod.parseResponse(response);
    }

    @Override
    public Call<T> clone() {
        return new OkHttpCall<>(serviceMethod, args);
    }

    @Override
    public void cancel() {
        canceled.set(true);
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }
}
