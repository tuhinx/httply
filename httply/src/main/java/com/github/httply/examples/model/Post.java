package com.github.httply.examples.model;

/**
 * Model class representing a post from a JSON API.
 */
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    // Getters
    public int getUserId() { return userId; }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    
    // Setters for JSON deserialization
    public void setUserId(int userId) { this.userId = userId; }
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }
    
    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
