package com.github.httply.retra;

import com.github.httply.retra.annotations.Converter;

/**
 * A {@link Converter.Factory} for JSON.
 */
public final class JsonConverterFactory extends Converter.Factory {
    /**
     * Create an instance.
     */
    public static JsonConverterFactory create() {
        return new JsonConverterFactory();
    }

    private JsonConverterFactory() {
    }

    @Override
    public Converter createConverter() {
        return new JsonConverter();
    }
}
