package com.github.httply.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link HttpUrl}.
 */
public class HttpUrlTest {
    @Test
    public void testParse() {
        HttpUrl url = HttpUrl.parse("https://example.com/path?query=value#fragment");
        
        assertEquals("https", url.scheme());
        assertEquals("example.com", url.host());
        assertEquals("/path", url.path());
        assertEquals("fragment", url.fragment());
        assertEquals("value", url.queryParams().get("query").get(0));
    }

    @Test
    public void testBuilder() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("example.com")
                .path("/path")
                .addQueryParameter("query", "value")
                .fragment("fragment")
                .build();
        
        assertEquals("https", url.scheme());
        assertEquals("example.com", url.host());
        assertEquals("/path", url.path());
        assertEquals("fragment", url.fragment());
        assertEquals("value", url.queryParams().get("query").get(0));
    }

    @Test
    public void testToString() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("example.com")
                .path("/path")
                .addQueryParameter("query", "value")
                .fragment("fragment")
                .build();
        
        assertEquals("https://example.com/path?query=value#fragment", url.toString());
    }

    @Test
    public void testPort() {
        HttpUrl url = HttpUrl.parse("https://example.com:8443/path");
        
        assertEquals(8443, url.port());
    }

    @Test
    public void testMultipleQueryParameters() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("example.com")
                .path("/path")
                .addQueryParameter("query", "value1")
                .addQueryParameter("query", "value2")
                .build();
        
        assertEquals(2, url.queryParams().get("query").size());
        assertEquals("value1", url.queryParams().get("query").get(0));
        assertEquals("value2", url.queryParams().get("query").get(1));
        assertEquals("https://example.com/path?query=value1&query=value2", url.toString());
    }
}
