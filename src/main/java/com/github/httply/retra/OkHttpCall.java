package com.github.httply.retra;

import com.github.httply.core.HttpResponse;
import com.github.httply.retra.annotations.Call;
import com.github.httply.retra.annotations.Callback;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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
    private final Executor callbackExecutor;

    OkHttpCall(ServiceMethod<T> serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
        this.callbackExecutor = Executors.newSingleThreadExecutor();
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
    public void enqueue(final Callback<T> callback) {
        if (callback == null)
            throw new NullPointerException("callback == null");

        if (executed.getAndSet(true)) {
            throw new IllegalStateException("Already executed.");
        }
        if (canceled.get()) {
            callback.onFailure(this, new IOException("Canceled"));
            return;
        }

        callbackExecutor.execute(() -> {
            try {
                HttpResponse rawResponse = serviceMethod.toResponse(args);
                T body = serviceMethod.parseResponse(rawResponse);
                callback.onResponse(OkHttpCall.this, RetraResponse.success(body, rawResponse));
            } catch (Throwable t) {
                callback.onFailure(OkHttpCall.this, t);
            }
        });
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
