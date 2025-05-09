package com.github.httply.retra;

import com.github.httply.core.HttpRequest;
import com.github.httply.core.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Converter.Factory} for JSON using reflection-based deserialization.
 */
public final class GsonConverterFactory extends Converter.Factory {
    /**
     * Create an instance.
     */
    public static GsonConverterFactory create() {
        return new GsonConverterFactory();
    }

    private GsonConverterFactory() {
    }

    @Override
    public Converter createConverter() {
        return new GsonConverter();
    }

    /**
     * A {@link Converter} that uses reflection to convert JSON to Java objects.
     */
    static class GsonConverter implements Converter {
        @Override
        public HttpRequest.RequestBody toRequestBody(Object value, Type type) {
            try {
                String json;
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    json = value.toString();
                } else {
                    json = objectToJson(value).toString();
                }
                return HttpRequest.RequestBody.create(json, "application/json");
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert " + value + " to JSON", e);
            }
        }

        @Override
        public Object fromResponse(HttpResponse response, Type type) throws IOException {
            if (response == null || !response.isSuccessful()) {
                return null;
            }

            String responseBody = response.body().string();
            if (responseBody == null || responseBody.isEmpty()) {
                return null;
            }

            try {
                if (type == String.class) {
                    return responseBody;
                } else if (type == JSONObject.class) {
                    return new JSONObject(responseBody);
                } else if (type == JSONArray.class) {
                    return new JSONArray(responseBody);
                } else if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    Type rawType = parameterizedType.getRawType();
                    if (rawType == List.class) {
                        Type elementType = parameterizedType.getActualTypeArguments()[0];
                        return parseList(responseBody, elementType);
                    }
                } else {
                    return parseObject(responseBody, type);
                }
            } catch (Exception e) {
                throw new IOException("Failed to parse response as " + type, e);
            }

            throw new IOException("Unsupported response type: " + type);
        }

        private List<?> parseList(String json, Type elementType) throws JSONException, ReflectiveOperationException {
            JSONArray jsonArray = new JSONArray(json);
            List<Object> result = new ArrayList<>();
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object item = parseObject(jsonObject.toString(), elementType);
                result.add(item);
            }
            
            return result;
        }

        private Object parseObject(String json, Type type) throws JSONException, ReflectiveOperationException {
            JSONObject jsonObject = new JSONObject(json);
            Class<?> clazz = (Class<?>) type;
            Object instance = clazz.newInstance();
            
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                String fieldName = field.getName();
                if (jsonObject.has(fieldName)) {
                    field.setAccessible(true);
                    Object value = jsonObject.get(fieldName);
                    
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        field.set(instance, jsonObject.getInt(fieldName));
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        field.set(instance, jsonObject.getLong(fieldName));
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        field.set(instance, jsonObject.getDouble(fieldName));
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        field.set(instance, jsonObject.getBoolean(fieldName));
                    } else if (field.getType() == String.class) {
                        field.set(instance, jsonObject.getString(fieldName));
                    }
                }
            }
            
            return instance;
        }

        private JSONObject objectToJson(Object obj) throws JSONException, IllegalAccessException {
            JSONObject json = new JSONObject();
            Class<?> clazz = obj.getClass();
            
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                
                if (value != null) {
                    json.put(fieldName, value);
                }
            }
            
            return json;
        }
    }
}
