package com.github.httply.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link HttpHeader}.
 */
public class HttpHeaderTest {
    @Test
    public void testBuilder() {
        HttpHeader headers = new HttpHeader.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .build();
        
        assertEquals("application/json", headers.get("Content-Type"));
        assertEquals("application/json", headers.get("Accept"));
    }

    @Test
    public void testMultipleValues() {
        HttpHeader headers = new HttpHeader.Builder()
                .add("Accept", "application/json")
                .add("Accept", "text/plain")
                .build();
        
        assertEquals(2, headers.getAll("Accept").size());
        assertEquals("application/json", headers.getAll("Accept").get(0));
        assertEquals("text/plain", headers.getAll("Accept").get(1));
    }

    @Test
    public void testSet() {
        HttpHeader headers = new HttpHeader.Builder()
                .add("Accept", "application/json")
                .add("Accept", "text/plain")
                .set("Accept", "application/xml")
                .build();
        
        assertEquals(1, headers.getAll("Accept").size());
        assertEquals("application/xml", headers.get("Accept"));
    }

    @Test
    public void testRemove() {
        HttpHeader headers = new HttpHeader.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .remove("Accept")
                .build();
        
        assertEquals("application/json", headers.get("Content-Type"));
        assertNull(headers.get("Accept"));
    }

    @Test
    public void testNames() {
        HttpHeader headers = new HttpHeader.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .build();
        
        assertEquals(2, headers.names().size());
        assertTrue(headers.names().contains("Content-Type"));
        assertTrue(headers.names().contains("Accept"));
    }

    @Test
    public void testNewBuilder() {
        HttpHeader headers = new HttpHeader.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .build();
        
        HttpHeader newHeaders = headers.newBuilder()
                .add("Authorization", "Bearer token")
                .build();
        
        assertEquals("application/json", newHeaders.get("Content-Type"));
        assertEquals("application/json", newHeaders.get("Accept"));
        assertEquals("Bearer token", newHeaders.get("Authorization"));
    }
}
