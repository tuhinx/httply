package com.github.httply.retra;

import com.github.httply.core.HttpHeader;
import com.github.httply.core.HttpRequest;
import com.github.httply.core.HttpResponse;
import com.github.httply.retra.annotations.Converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Converts between Java objects and JSON.
 */
public class JsonConverter implements Converter {
    @Override
    public HttpRequest.RequestBody toRequestBody(Object value, Type type) {
        String json;
        if (value instanceof JSONObject || value instanceof JSONArray) {
            json = value.toString();
        } else if (value instanceof Map) {
            json = mapToJson((Map<?, ?>) value).toString();
        } else if (value instanceof List) {
            json = listToJson((List<?>) value).toString();
        } else if (value instanceof String) {
            json = (String) value;
        } else {
            json = objectToJson(value).toString();
        }
        return HttpRequest.RequestBody.create(json, HttpHeader.Values.APPLICATION_JSON);
    }

    @Override
    public Object fromResponse(HttpResponse response, Type type) {
        if (!response.isSuccessful()) {
            throw new RuntimeException("Request failed with code " + response.code());
        }

        if (response.body() == null) {
            return null;
        }

        String json = response.body().string();

        // Handle empty or null JSON
        if (json == null || json.trim().isEmpty()) {
            if (type == String.class) {
                return "";
            } else if (type == JSONObject.class) {
                return new JSONObject();
            } else if (type == JSONArray.class) {
                return new JSONArray();
            } else if (type instanceof Class && Map.class.isAssignableFrom((Class<?>) type)) {
                return new HashMap<>();
            } else if (type instanceof Class && List.class.isAssignableFrom((Class<?>) type)) {
                return new ArrayList<>();
            } else {
                try {
                    return ((Class<?>) type).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        if (type == String.class) {
            return json;
        } else if (type == JSONObject.class) {
            try {
                return new JSONObject(json);
            } catch (JSONException e) {
                // Return empty object instead of throwing exception
                return new JSONObject();
            }
        } else if (type == JSONArray.class) {
            try {
                return new JSONArray(json);
            } catch (JSONException e) {
                // Return empty array instead of throwing exception
                return new JSONArray();
            }
        } else if (type instanceof Class && Map.class.isAssignableFrom((Class<?>) type)) {
            try {
                return jsonToMap(new JSONObject(json));
            } catch (JSONException e) {
                // Return empty map instead of throwing exception
                return new HashMap<>();
            }
        } else if (type instanceof Class && List.class.isAssignableFrom((Class<?>) type)) {
            try {
                return jsonToList(new JSONArray(json));
            } catch (JSONException e) {
                // Return empty list instead of throwing exception
                return new ArrayList<>();
            }
        } else {
            try {
                return jsonToObject(new JSONObject(json), (Class<?>) type);
            } catch (Exception e) {
                try {
                    // Try to create a new instance instead of throwing exception
                    return ((Class<?>) type).getDeclaredConstructor().newInstance();
                } catch (Exception ex) {
                    return null;
                }
            }
        }
    }

    /**
     * Converts a Map to a JSONObject.
     */
    private JSONObject mapToJson(Map<?, ?> map) {
        JSONObject json = new JSONObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = String.valueOf(entry.getKey());
            Object value = entry.getValue();
            try {
                if (value == null) {
                    json.put(key, JSONObject.NULL);
                } else if (value instanceof Map) {
                    json.put(key, mapToJson((Map<?, ?>) value));
                } else if (value instanceof List) {
                    json.put(key, listToJson((List<?>) value));
                } else if (value instanceof JSONObject) {
                    json.put(key, value);
                } else if (value instanceof JSONArray) {
                    json.put(key, value);
                } else {
                    json.put(key, value);
                }
            } catch (JSONException e) {
                throw new RuntimeException("Failed to convert map to JSON", e);
            }
        }
        return json;
    }

    /**
     * Converts a List to a JSONArray.
     */
    private JSONArray listToJson(List<?> list) {
        JSONArray json = new JSONArray();
        for (Object value : list) {
            if (value == null) {
                json.put(JSONObject.NULL);
            } else if (value instanceof Map) {
                json.put(mapToJson((Map<?, ?>) value));
            } else if (value instanceof List) {
                json.put(listToJson((List<?>) value));
            } else if (value instanceof JSONObject) {
                json.put(value);
            } else if (value instanceof JSONArray) {
                json.put(value);
            } else {
                json.put(value);
            }
        }
        return json;
    }

    /**
     * Converts an object to a JSONObject.
     */
    private JSONObject objectToJson(Object object) {
        // This is a simplified implementation
        // In a real implementation, you would use reflection to get the object's fields
        return new JSONObject();
    }

    /**
     * Converts a JSONObject to a Map.
     */
    private Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);
            if (value == JSONObject.NULL) {
                map.put(key, null);
            } else if (value instanceof JSONObject) {
                map.put(key, jsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.put(key, jsonToList((JSONArray) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * Converts a JSONArray to a List.
     */
    private List<Object> jsonToList(JSONArray json) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            Object value = json.get(i);
            if (value == JSONObject.NULL) {
                list.add(null);
            } else if (value instanceof JSONObject) {
                list.add(jsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                list.add(jsonToList((JSONArray) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * Converts a JSONObject to an object of the specified class.
     */
    private Object jsonToObject(JSONObject json, Class<?> clazz) {
        // This is a simplified implementation
        // In a real implementation, you would use reflection to set the object's fields
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}
